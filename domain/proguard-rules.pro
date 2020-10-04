# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

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