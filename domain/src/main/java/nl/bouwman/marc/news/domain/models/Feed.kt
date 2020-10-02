package nl.bouwman.marc.news.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Feed(
    @SerialName("Id")
    val id: Int,
    @SerialName("Name")
    val name: String
) : java.io.Serializable