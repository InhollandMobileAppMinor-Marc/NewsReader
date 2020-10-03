package nl.bouwman.marc.news.domain.services

import androidx.lifecycle.LiveData
import nl.bouwman.marc.news.domain.models.Article
import nl.bouwman.marc.news.domain.models.ArticleBatch
import nl.bouwman.marc.news.domain.models.Result

interface NewsReaderApi {
    val isOnline: LiveData<Boolean>

    suspend fun getArticles(id: Int? = null, token: String? = null): ArticleBatch?

    suspend fun getArticle(id: Int, token: String? = null): Article?

    suspend fun getLikedArticles(token: String): ArticleBatch?

    suspend fun registerAccount(username: String, password: String): Result<String, String?>

    suspend fun login(username: String, password: String): String?

    suspend fun isTokenValid(token: String): Boolean

    suspend fun likeArticle(article: Article, token: String): Article

    suspend fun dislikeArticle(article: Article, token: String): Article
}