@file:Suppress("UNUSED_VARIABLE")

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdkVersion(30)

    packagingOptions {
        exclude("kotlin/**")
        exclude("**/*.kotlin_metadata")
        exclude("DebugProbesKt.bin")
        exclude("META-INF/*.kotlin_module")
        exclude("META-INF/*.version")
        exclude("build-data.properties")
    }

    defaultConfig {
        applicationId = "nl.bouwman.marc.news"

        minSdkVersion(23)
        targetSdkVersion(30)

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))

    implementation(project(":ui"))
    implementation(project(":domain"))
    implementation(project(":api"))

    implementation(kotlin("stdlib-jdk8"))

    implementation("org.koin:koin-android:2.0.1")
    implementation("org.koin:koin-androidx-viewmodel:2.0.1")

    implementation("androidx.core:core-ktx:1.3.1")
    implementation("androidx.appcompat:appcompat:1.2.0")

    testImplementation("junit:junit:4.13")

    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}
