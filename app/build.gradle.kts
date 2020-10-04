@file:Suppress("UNUSED_VARIABLE")

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdkVersion(Versions.SDK.Android.compile)

    packagingOptions {
        exclude("kotlin/**")
        exclude("okhttp3/**")
        exclude("**/*.kotlin_metadata")
        exclude("DebugProbesKt.bin")
        exclude("META-INF/*.kotlin_module")
        exclude("META-INF/*.version")
        exclude("build-data.properties")
    }

    defaultConfig {
        applicationId = "nl.bouwman.marc.news"

        minSdkVersion(Versions.SDK.Android.min)
        targetSdkVersion(Versions.SDK.Android.target)

        versionCode = Versions.App.code
        versionName = Versions.App.name

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        val release by getting {
            isMinifyEnabled = true
            isZipAlignEnabled = true
            isShrinkResources = true
            isCrunchPngs = true

            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        val debug by getting {
            isMinifyEnabled = false
            isZipAlignEnabled = true
            isShrinkResources = false
            isCrunchPngs = true

            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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

    implementation(project(":ui"))
    implementation(project(":domain"))
    implementation(project(":api"))

    implementation(kotlin("stdlib-jdk8"))

    implementation("org.koin:koin-android:${Versions.Libs.koin}")
    implementation("org.koin:koin-androidx-viewmodel:${Versions.Libs.koin}")

    implementation("androidx.core:core-ktx:1.3.1")
    implementation("androidx.appcompat:appcompat:1.2.0")

    testImplementation("junit:junit:${Versions.Libs.junit}")

    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}
