plugins {
    kotlin("jvm")
}

dependencies {
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.serialization.gson)
}