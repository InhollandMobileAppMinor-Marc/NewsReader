package nl.bouwman.marc.news.api

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.delay
import nl.bouwman.marc.news.domain.models.Article
import nl.bouwman.marc.news.domain.models.ArticleBatch
import nl.bouwman.marc.news.domain.models.Feed
import nl.bouwman.marc.news.domain.models.Result
import nl.bouwman.marc.news.domain.services.NewsReaderApi
import java.util.*

class FakeNewsReaderApiImpl : NewsReaderApi {
    override val isOnline = MutableLiveData(true)

    private var myArticle = Article(
        1,
        1,
        "Hello World!",
        "Lorem ipsum",
        Date(),
        "https://marketplace.canva.com/MAB_ajIqclg/1/0/thumbnail_large/canva-hello-world-instagram-post-MAB_ajIqclg.jpg",
        "https://example.com/",
        categories = setOf(Feed(1, "Test"), Feed(2, "Programming"))
    )

    override suspend fun getArticles(id: Int?, token: String?): ArticleBatch? {
        delay(1000)
        return ArticleBatch(setOf(myArticle.copy()))
    }

    override suspend fun getArticle(id: Int, token: String?): Article? {
        delay(1000)
        return if(id == 1) myArticle.copy() else null
    }

    override suspend fun getLikedArticles(token: String): ArticleBatch? {
        delay(1000)
        return ArticleBatch(if(myArticle.isLiked) setOf(myArticle.copy()) else emptySet())
    }

    override suspend fun registerAccount(username: String, password: String): Result<String, String?> {
        delay(1000)
        return Result.OK("ABCD")
    }

    override suspend fun login(username: String, password: String): String? {
        delay(1000)
        return "ABCD"
    }

    override suspend fun isTokenValid(token: String): Boolean {
        delay(500)
        return token == "ABCD"
    }

    override suspend fun likeArticle(article: Article, token: String): Article {
        delay(1000)
        myArticle = article.copy(isLiked = true)
        return myArticle.copy()
    }

    override suspend fun dislikeArticle(article: Article, token: String): Article {
        delay(1000)
        myArticle = article.copy(isLiked = false)
        return myArticle.copy()
    }
}