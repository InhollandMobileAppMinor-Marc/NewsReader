package nl.bouwman.marc.news.domain.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ArticleBatch(
    @SerialName("Results")
    val articles: Set<Article> = emptySet(),
    @SerialName("NextId")
    val nextId: Int? = null
)