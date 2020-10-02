package nl.bouwman.marc.news.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.bouwman.marc.news.domain.models.Article
import nl.bouwman.marc.news.domain.services.NewsReaderApi
import nl.bouwman.marc.news.domain.services.AccountManager

class ArticleOverviewViewModel(
    private val api: NewsReaderApi,
    private val accountManager: AccountManager
) : ViewModel() {
    private var nextId: Int? = null

    val isLoggedIn
        get() = accountManager.isLoggedIn

    val isOnline
        get() = api.isOnline

    private val mutableArticles = MutableLiveData(emptySet<Article>())

    val articles: LiveData<Set<Article>>
        get() = mutableArticles

    private val mutableIsLoading = MutableLiveData(false)

    val isLoading: LiveData<Boolean>
        get() = mutableIsLoading

    var wasLoggedInOnLastSync: Boolean? = null
        private set

    init {
        loadArticles()
    }

    fun loadArticles() = loadArticles(false)

    fun reloadArticles() = loadArticles(true)

    private fun loadArticles(reload: Boolean) {
        // Don't load new articles if we're already doing so
        if(isLoading.value == true) return

        // Don't load new articles if we're at the end of the list
        if(nextId == null && !articles.value.isNullOrEmpty() && !reload) return

        mutableIsLoading.postValue(true)
        val id = if(reload) null else nextId

        viewModelScope.launch {
            val token = accountManager.fetchToken()
            wasLoggedInOnLastSync = isLoggedIn.value
            val articleBatch = api.getArticles(id, token)

            if (articleBatch != null) {
                val articles = if(reload) emptySet() else articles.value ?: emptySet()
                mutableArticles.postValue(articles + articleBatch.articles)
                nextId = articleBatch.nextId
            } else {
                // TODO: show error message
                mutableArticles.postValue(emptySet())
            }

            mutableIsLoading.postValue(false)
        }
    }
}