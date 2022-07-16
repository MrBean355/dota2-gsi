pluginManagement {
    plugins {
        kotlin("jvm") version "1.7.10"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
    }
}

include("lib")
include("tools")
include("demo")