pluginManagement {
    plugins {
        kotlin("jvm") version "1.8.21"
        kotlin("plugin.serialization") version "1.8.21"
        id("org.jetbrains.dokka") version "1.8.10"
        id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.13.1"
        id("org.sonarqube") version "4.0.0.2929"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            version("ktor", "2.3.0")
            library("ktor-server-netty", "io.ktor", "ktor-server-netty").versionRef("ktor")
            library("ktor-server-contentNegotiation", "io.ktor", "ktor-server-content-negotiation").versionRef("ktor")
            library("ktor-serialization-kotlinx-json", "io.ktor", "ktor-serialization-kotlinx-json").versionRef("ktor")

            library("kotlinx-serialization-json", "org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
        }
        create("testLibs") {
            library("junit-bom", "org.junit:junit-bom:5.9.2")
            library("junit-jupiter", "org.junit.jupiter", "junit-jupiter").withoutVersion()
            library("mockK", "io.mockk:mockk:1.13.5")
        }
    }
}

include("lib")
include("demo")