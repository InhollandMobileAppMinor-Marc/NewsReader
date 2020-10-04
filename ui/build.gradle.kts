@file:Suppress("UNUSED_VARIABLE")

plugins {
    id("com.android.library")
    kotlin("android")
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

    buildFeatures {
        viewBinding = true
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

    // Projects/modules
    implementation(project(":domain"))

    // Kotlin
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Libs.KotlinX.coroutines}")

    // Network
    implementation("io.coil-kt:coil:1.0.0-rc3")

    // Design
    implementation("com.google.android.material:material:1.2.1")

    // DI
    implementation("org.koin:koin-android:${Versions.Libs.koin}")
    implementation("org.koin:koin-androidx-viewmodel:${Versions.Libs.koin}")

    // Security
    implementation("androidx.security:security-crypto:1.0.0-rc03")

    // Views
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.1")

    // Other
    implementation("androidx.activity:activity-ktx:1.1.0")
    implementation("androidx.core:core-ktx:1.3.1")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.browser:browser:1.2.0")
    implementation("androidx.annotation:annotation:1.1.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")

    // Tests
    testImplementation("junit:junit:${Versions.Libs.junit}")

    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}
