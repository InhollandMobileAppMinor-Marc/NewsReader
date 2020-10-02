package nl.bouwman.marc.news.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.bouwman.marc.news.domain.models.Article
import nl.bouwman.marc.news.domain.services.AccountManager
import nl.bouwman.marc.news.domain.services.NewsReaderApi

class AccountOverviewViewModel(
    private val api: NewsReaderApi,
    private val accountManager: AccountManager
) : ViewModel() {
    private val mutableArticles = MutableLiveData(emptySet<Article>())

    val articles: LiveData<Set<Article>>
        get() = mutableArticles

    private val mutableIsLoading = MutableLiveData(false)

    val isLoading: LiveData<Boolean>
        get() = mutableIsLoading

    val isLoggedIn
        get() = accountManager.isLoggedIn

    init {
        loadArticles()
    }

    fun loadArticles() {
        // Don't load new articles if we're already doing so
        if(isLoading.value == true) return

        mutableIsLoading.postValue(true)
        mutableArticles.postValue(emptySet())

        viewModelScope.launch {
            val token = accountManager.fetchToken()

            if (token != null) {
                val articleBatch = api.getLikedArticles(token)

                if (articleBatch != null)
                    mutableArticles.postValue(articleBatch.articles)
            }

            mutableIsLoading.postValue(false)
        }
    }

    fun logout() {
        viewModelScope.launch {
            accountManager.logout()
        }
    }
}