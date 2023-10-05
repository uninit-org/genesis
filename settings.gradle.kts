rootProject.name = "Genesis"
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

}
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.4.0")
}

include(":appAndroid")
include(":appDesktop")
//include(":appIos") appIos is an xcode project, this is just here as a symbolic measure.
include(":genesis:app")
include(":genesis:discord")
// include(":genesis:nativeVoice") TODO: soon:tm:

