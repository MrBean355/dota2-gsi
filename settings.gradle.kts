pluginManagement {
    plugins {
        kotlin("jvm") version "1.7.10"
        kotlin("plugin.serialization") version "1.7.10"
        id("org.jetbrains.dokka") version "1.7.10"
        id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.11.0"
        id("org.sonarqube") version "3.4.0.2513"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            version("ktor", "2.0.3")
            library("ktor-server-netty", "io.ktor", "ktor-server-netty").versionRef("ktor")
            library("ktor-server-contentNegotiation", "io.ktor", "ktor-server-content-negotiation").versionRef("ktor")
            library("ktor-serialization-kotlinx-json", "io.ktor", "ktor-serialization-kotlinx-json").versionRef("ktor")

            library("kotlinx-serialization-json", "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
        }
        create("testLibs") {
            library("junit-bom", "org.junit:junit-bom:5.8.2")
            library("junit-jupiter", "org.junit.jupiter", "junit-jupiter").withoutVersion()
            library("mockK", "io.mockk:mockk:1.12.4")
        }
    }
}

include("lib")
include("demo")