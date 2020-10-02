@file:Suppress("UNUSED_VARIABLE")

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("plugin.serialization")
}

android {
    compileSdkVersion(Versions.SDK.Android.compile)

    defaultConfig {
        minSdkVersion(Versions.SDK.Android.min)
        targetSdkVersion(Versions.SDK.Android.target)

        versionCode = Versions.App.code
        versionName = Versions.App.name

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        val release by getting {
            isMinifyEnabled = false
            isZipAlignEnabled = true
            isShrinkResources = false
            isCrunchPngs = true

            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        val debug by getting {
            isMinifyEnabled = false
            isZipAlignEnabled = true
            isShrinkResources = false
            isCrunchPngs = true
        }
    }

    compileOptions {
        sourceCompatibility = Versions.SDK.jvm
        targetCompatibility = Versions.SDK.jvm
    }

    kotlinOptions {
        jvmTarget = Versions.SDK.jvm.toString()
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))

    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:${Versions.Libs.KotlinX.serialization}")

    testImplementation("junit:junit:${Versions.Libs.junit}")

    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}
