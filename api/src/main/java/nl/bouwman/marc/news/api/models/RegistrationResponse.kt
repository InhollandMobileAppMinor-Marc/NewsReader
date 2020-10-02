package nl.bouwman.marc.news.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.text.DateFormat
import java.util.*

@Serializable
data class RegistrationResponse(
    @SerialName("Success")
    val success: Boolean,
    @SerialName("Message")
    val message: String
) : java.io.Serializable
