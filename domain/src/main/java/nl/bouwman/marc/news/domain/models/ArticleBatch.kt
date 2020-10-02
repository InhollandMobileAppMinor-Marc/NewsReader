package nl.bouwman.marc.news.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleBatch(
    @SerialName("Results")
    val articles: Set<Article> = emptySet(),
    @SerialName("NextId")
    val nextId: Int? = null
)