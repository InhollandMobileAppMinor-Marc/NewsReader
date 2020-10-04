-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.SerializationKt

-keep,includedescriptorclasses class nl.bouwman.marc.news.domain.models.**$$serializer { *; }
-keepclassmembers class nl.bouwman.marc.news.domain.models.* {
    *** Companion;
}
-keepclasseswithmembers class nl.bouwman.marc.news.domain.models.* {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep,includedescriptorclasses class nl.bouwman.marc.news.domain.utils.**$$serializer { *; }
-keepclassmembers class nl.bouwman.marc.news.domain.utils.* {
    *** Companion;
}
-keepclasseswithmembers class nl.bouwman.marc.news.domain.utils.* {
    kotlinx.serialization.KSerializer serializer(...);
}