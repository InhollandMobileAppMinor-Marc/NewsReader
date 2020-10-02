package nl.bouwman.marc.news.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import nl.bouwman.marc.news.api.models.UserCredentials
import nl.bouwman.marc.news.domain.models.Article
import nl.bouwman.marc.news.domain.models.ArticleBatch
import nl.bouwman.marc.news.domain.models.Result
import nl.bouwman.marc.news.domain.services.NewsReaderApi
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class NewsReaderApiImpl : NewsReaderApi {
    private val api by lazy {
        Retrofit.Builder()
            .baseUrl("https://inhollandbackend.azurewebsites.net/api/")
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
            .build()
            .create(NewsReaderApiService::class.java)
    }

    private suspend inline fun <T> api(crossinline block: suspend () -> T): T? {
        return withContext(Dispatchers.IO) {
            try {
                block()
            } catch (error: UnknownHostException) {
                // Network is offline
                null
            }
        }
    }

    override suspend fun getArticles(id: Int?, token: String?): ArticleBatch? {
        val response = api {
            if (id == null) api.getArticles(token, count = 20)
            else api.getArticlesById(id, token, count = 20)
        }

        return if (response?.isSuccessful == true) response.body() else null
    }

    override suspend fun getLikedArticles(token: String): ArticleBatch? {
        val response = api { api.getLikedArticles(token) }

        return if (response?.isSuccessful == true) response.body() else null
    }

    override suspend fun registerAccount(username: String, password: String): Result<String, String?> {
        val user = UserCredentials(username, password)

        val response = api { api.registerNewUser(user) }

        return if (response?.isSuccessful == true) {
            val body = response.body()
            when {
                body == null -> Result.Error(null)
                !body.success -> Result.Error(body.message)
                else -> Result.OK(body.message)
            }
        } else Result.Error(null)
    }

    override suspend fun login(username: String, password: String): String? {
        val user = UserCredentials(username, password)

        val response = api { api.loginUser(user) }

        return if (response?.isSuccessful == true) response.body()?.authToken else null
    }

    override suspend fun isTokenValid(token: String): Boolean
            = api { api.getArticles(token, count = 1) }?.isSuccessful == true

    override suspend fun likeArticle(article: Article, token: String): Article {
        val response = api { api.likeArticle(article.id, token) }

        return if (response?.isSuccessful == true) article.copy(isLiked = true) else article
    }

    override suspend fun dislikeArticle(article: Article, token: String): Article {
        val response = api { api.dislikeArticle(article.id, token) }

        return if (response?.isSuccessful == true) article.copy(isLiked = false) else article
    }
}