pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    val kotlinVersion = "2.4.21"
    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion
        id("org.jetbrains.kotlin.plugin.compose") version kotlinVersion
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }
}

rootProject.name = "petrel-catbird"

// Composite build: substitutes blue.catbird:petrel-kotlin with the local checkout.
includeBuild("../../Petrel/kotlin")
