plugins {
    kotlin("multiplatform")
    id("com.android.library")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    androidTarget()

    jvm("desktop")

    iosArm64().binaries.framework {
        baseName = "genesisDiscordApi"
        binaryOption("bundleId", "uninit.genesis.discord.api")
        isStatic = true
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.serialization.json)

            }

            resources.srcDirs("resources")
        }
        val commonTest by getting {
            dependencies {
//                dependsOn(commonMain)
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {

            }
        }
        val desktopMain by getting {
            dependencies {

            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "uninit.genesis.discord.api"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}