pluginManagement {
    plugins {
        kotlin("jvm") version "1.7.10"
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
            library("ktor-serialization-gson", "io.ktor", "ktor-serialization-gson").versionRef("ktor")
        }
    }
}

include("lib")
include("tools")
include("demo")