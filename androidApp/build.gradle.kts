plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")
}

kotlin {
    android()
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(project(":shared"))

                implementation("com.arkivanov.decompose:decompose:2.0.0-compose-experimental-beta-01")
                implementation("com.arkivanov.mvikotlin:mvikotlin:3.1.0")
                implementation("com.arkivanov.mvikotlin:mvikotlin-main:3.1.0")
                implementation("io.insert-koin:koin-android:3.3.3")
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "eu.kevin.composedemo"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        applicationId = "eu.kevin.composedemo"
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }
    kotlin {
        jvmToolchain(11)
    }
}
