plugins {
    kotlin("jvm") version "1.5.31"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    // The dependency would usually be added like this:
    // implementation("com.github.mrbean355:dota2-gsi:$latestVersion")

    implementation(project(":lib"))
}