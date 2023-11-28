plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    alias(libs.plugins.kotlinx.serialization)
    `maven-publish`
}

kotlin {
    androidTarget()

    jvm("desktop")

    iosArm64().binaries.framework {
        baseName = "genesisDiscordClient"
        binaryOption("bundleId", "uninit.genesisApi")
        isStatic = true
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.serialization.json)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.negotiation)
                implementation(libs.ktor.serialization.json)
                compileOnly(compose.runtime)

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
                implementation(libs.uninit.common)
                implementation(libs.uninit.common.compose)

            }

            resources.srcDirs("resources")
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "uninit.genesisApi"

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

version = "0.0.1"

publishing {
    var versionStr = project.version.toString()
    val ci = System.getenv("CI") != null && System.getenv("GITHUB_EVENT_NAME") != "release"
    var repo = "releases"
    if (ci) {
        val commitHash = System.getenv("GITHUB_SHA").slice(0..6)
        versionStr += "-#$commitHash"
        repo = "snapshots"
    }
    repositories {
        maven {
            name = "uninit"
            url = uri("https://repo.uninit.dev/$repo")
            credentials {
                username = "admin"
                password = System.getenv("REPOSILITE_PASSWORD")
            }
        }

    }
    publications {
        create<MavenPublication>("uninit.genesis-api") {
            groupId = "uninit"
            artifactId = "genesis-api"
            version = versionStr
        }
    }

}