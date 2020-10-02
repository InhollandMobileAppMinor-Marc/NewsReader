package nl.bouwman.marc.news.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.text.DateFormat
import java.util.*

@Serializable
data class LoginResponse(
    @SerialName("AuthToken")
    val authToken: String
) : java.io.Serializable
