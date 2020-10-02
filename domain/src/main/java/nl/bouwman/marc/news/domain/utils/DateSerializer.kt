package nl.bouwman.marc.news.domain.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateSerializer : KSerializer<Date?> {
    private val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Date? = try {
        parser.parse(decoder.decodeString())
    } catch(err: ParseException) {
        null
    }

    override fun serialize(encoder: Encoder, value: Date?) {
        return if (value == null) encoder.encodeNull() else encoder.encodeString(parser.format(value))
    }
}