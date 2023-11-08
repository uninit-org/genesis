import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm()
    jvmToolchain(17)
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":genesis:app"))
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "xyz.genesisapp.genesis.Application"

        nativeDistributions {
            targetFormats(
                TargetFormat.Deb,
                TargetFormat.Rpm,
                TargetFormat.Dmg,
                TargetFormat.Exe
            )
            packageName = "Genesis"
            packageVersion = "1.0.0"

            val iconsRoot = project.file("../genesis/app/src/commonMain/resources/icons")
            macOS {
                bundleID = "xyz.genesisapp.genesis"
                iconFile.set(iconsRoot.resolve("genesis.icns"))
            }
            windows {
                iconFile.set(iconsRoot.resolve("genesis.ico"))
            }
            linux {
                iconFile.set(iconsRoot.resolve("genesis.png"))
            }
        }
    }
}
