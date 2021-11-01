plugins {
    kotlin("jvm") version "1.5.31"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-netty:1.6.4")
    implementation("io.ktor:ktor-gson:1.6.4")
}