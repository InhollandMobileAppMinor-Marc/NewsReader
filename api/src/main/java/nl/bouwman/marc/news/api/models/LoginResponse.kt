package nl.bouwman.marc.news.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("AuthToken")
    val authToken: String
) : java.io.Serializable
