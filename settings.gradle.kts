pluginManagement {
    plugins {
        kotlin("jvm") version "2.3.10"
        kotlin("plugin.serialization") version "2.3.10"
        id("org.jetbrains.dokka") version "2.1.0"
        id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.18.1"
        id("org.sonarqube") version "7.2.3.7755"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            version("ktor", "3.4.1")
            library("ktor-server-netty", "io.ktor", "ktor-server-netty").versionRef("ktor")
            library("ktor-server-contentNegotiation", "io.ktor", "ktor-server-content-negotiation").versionRef("ktor")
            library("ktor-serialization-kotlinx-json", "io.ktor", "ktor-serialization-kotlinx-json").versionRef("ktor")

            library("kotlinx-serialization-json", "org.jetbrains.kotlinx:kotlinx-serialization-json:1.10.0")
        }
        create("testLibs") {
            library("junit-bom", "org.junit:junit-bom:6.0.3")
            library("junit-jupiter", "org.junit.jupiter", "junit-jupiter").withoutVersion()
            library("junit-launcher", "org.junit.platform", "junit-platform-launcher").withoutVersion()
            library("mockK", "io.mockk:mockk:1.14.9")
        }
    }
}

include("lib")
include("demo")