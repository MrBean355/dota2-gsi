pluginManagement {
    plugins {
        kotlin("jvm") version "1.8.22"
        kotlin("plugin.serialization") version "1.8.22"
        id("org.jetbrains.dokka") version "1.9.10"
        id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.13.2"
        id("org.sonarqube") version "4.4.1.3373"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            version("ktor", "2.3.5")
            library("ktor-server-netty", "io.ktor", "ktor-server-netty").versionRef("ktor")
            library("ktor-server-contentNegotiation", "io.ktor", "ktor-server-content-negotiation").versionRef("ktor")
            library("ktor-serialization-kotlinx-json", "io.ktor", "ktor-serialization-kotlinx-json").versionRef("ktor")

            library("kotlinx-serialization-json", "org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
        }
        create("testLibs") {
            library("junit-bom", "org.junit:junit-bom:5.10.0")
            library("junit-jupiter", "org.junit.jupiter", "junit-jupiter").withoutVersion()
            library("mockK", "io.mockk:mockk:1.13.8")
        }
    }
}

include("lib")
include("demo")