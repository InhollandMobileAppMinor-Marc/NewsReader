package nl.bouwman.marc.news.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.bouwman.marc.news.domain.utils.DateSerializer
import java.text.DateFormat
import java.util.*

@Serializable
data class Article(
    @SerialName("Id")
    val id: Int,
    @SerialName("Feed")
    val feed: Int,
    @SerialName("Title")
    val title: String,
    @SerialName("Summary")
    val summary: String,
    @Serializable(with = DateSerializer::class)
    @SerialName("PublishDate")
    val publishDate: Date?,
    @SerialName("Image")
    val image: String,
    @SerialName("Url")
    val url: String,
    @SerialName("Related")
    val related: Set<String> = emptySet(),
    @SerialName("Categories")
    val categories: Set<Feed> = emptySet(),
    @SerialName("IsLiked")
    val isLiked: Boolean = false
) : java.io.Serializable {
    val humanReadableTimeAndDate: String?
        get() =
            if(publishDate == null) null
            else DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(publishDate)
}
