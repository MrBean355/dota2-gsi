pluginManagement {
    plugins {
        kotlin("jvm") version "1.9.25"
        kotlin("plugin.serialization") version "1.9.25"
        id("org.jetbrains.dokka") version "2.0.0"
        id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.17.0"
        id("org.sonarqube") version "6.0.1.5171"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            version("ktor", "2.3.13")
            library("ktor-server-netty", "io.ktor", "ktor-server-netty").versionRef("ktor")
            library("ktor-server-contentNegotiation", "io.ktor", "ktor-server-content-negotiation").versionRef("ktor")
            library("ktor-serialization-kotlinx-json", "io.ktor", "ktor-serialization-kotlinx-json").versionRef("ktor")

            library("kotlinx-serialization-json", "org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
        }
        create("testLibs") {
            library("junit-bom", "org.junit:junit-bom:5.11.4")
            library("junit-jupiter", "org.junit.jupiter", "junit-jupiter").withoutVersion()
            library("mockK", "io.mockk:mockk:1.13.16")
        }
    }
}

include("lib")
include("demo")