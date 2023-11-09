@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
}

tasks.create("packageDesktop") {
    group = "build"
    listOf(
        "Deb",
        "Rpm",
        "Msi",
        "Exe",
        "Dmg"
    ).forEach { format ->
        dependsOn(":appDesktop:package${format}")
    }
}

buildscript {
    repositories {
        gradlePluginPortal()
    }

    dependencies {
//        classpath(libs.moko.resources.generator)
    }
}
allprojects {
    val iosSpec = object : Spec<Task> {
        override fun isSatisfiedBy(element: Task?): Boolean {
            return System.getenv("GITHUB_ACTIONS") == "true" || System.getenv("ENABLE_IOS") == "true"
        }

    }

//    tasks.withType(KotlinCompile::class).all {
//        kotlinOptions {
//            freeCompilerArgs += "-Xexpect-actual-classes"
//        }
//    }

    tasks.matching {
        name.contains("FrameworkIos")
    }.forEach {
        it.setOnlyIf(iosSpec)
    }
}
true // Needed to make the Suppress annotation work for the plugins block