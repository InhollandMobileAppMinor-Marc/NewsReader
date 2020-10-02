package nl.bouwman.marc.news.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserCredentials(
    @SerialName("UserName")
    val username: String,
    @SerialName("Password")
    val password: String
) : java.io.Serializable
