plugins {
    kotlin("jvm")
    application
}

dependencies {
    // The dependency would usually be added like this:
    // implementation("com.github.mrbean355:dota2-gsi:$latestVersion")
    implementation(project(":lib"))

    // Logger implementation so we can see logs coming through:
    implementation("ch.qos.logback:logback-classic:1.5.16")
}