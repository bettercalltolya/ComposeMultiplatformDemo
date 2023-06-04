plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("kotlin-parcelize")
    id("io.realm.kotlin") version "1.8.0"
}

kotlin {
    android()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    val decomposeVersion = "2.0.0-compose-experimental-beta-01"
    val essentyVersion = "1.1.0"
    val mviKotlinVersion = "3.1.0"
    val realmVersion = "1.7.1"
    val koinVersion = "3.3.3"

    cocoapods {
        version = "1.0.0"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true

            export("com.arkivanov.decompose:decompose:$decomposeVersion")
            export("com.arkivanov.essenty:lifecycle:$essentyVersion")
            export("com.arkivanov.mvikotlin:mvikotlin-main:$mviKotlinVersion")
        }
        extraSpecAttributes["resources"] = "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material3)
                api(compose.ui)
                api(compose.materialIconsExtended)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                api(compose.components.resources)

                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")

                // Navigation
                api("com.arkivanov.decompose:decompose:$decomposeVersion")
                api("com.arkivanov.decompose:extensions-compose-jetbrains:$decomposeVersion")

                // MVI
                api("com.arkivanov.mvikotlin:mvikotlin:$mviKotlinVersion")
                api("com.arkivanov.mvikotlin:mvikotlin-main:$mviKotlinVersion")
                api("com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:$mviKotlinVersion")

                // Database
                implementation("io.realm.kotlin:library-base:$realmVersion")

                // DI
                implementation("io.insert-koin:koin-core:$koinVersion")
            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.activity:activity-compose:1.7.2")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.10.1")
                api("io.insert-koin:koin-android:$koinVersion")
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                api("com.arkivanov.decompose:decompose:$decomposeVersion")
                api("com.arkivanov.essenty:lifecycle:$essentyVersion")
                api("com.arkivanov.mvikotlin:mvikotlin-main:$mviKotlinVersion")
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "eu.kevin.composedemo.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(11)
    }
}