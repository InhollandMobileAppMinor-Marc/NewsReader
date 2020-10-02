package nl.bouwman.marc.news.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.bouwman.marc.news.domain.models.Article
import nl.bouwman.marc.news.domain.services.NewsReaderApi
import nl.bouwman.marc.news.ui.account.AccountManager

class ArticleDetailsViewModel(
    private val api: NewsReaderApi,
    private val accountManager: AccountManager
) : ViewModel() {
    val article = MutableLiveData<Article>()

    private val mutableIsChangingLike = MutableLiveData(false)

    val isChangingLike: LiveData<Boolean>
        get() = mutableIsChangingLike

    val isLoggedIn: LiveData<Boolean>
        get() = accountManager.isLoggedIn

    fun changeLike() {
        mutableIsChangingLike.postValue(true)
        val currentArticle = article.value
        viewModelScope.launch {
            val token = accountManager.fetchToken()
            if(currentArticle != null && token != null) {
                val likeFunction = if(currentArticle.isLiked) api::dislikeArticle else api::likeArticle
                article.postValue(likeFunction(currentArticle, token))
            }

            mutableIsChangingLike.postValue(false)
        }
    }
}