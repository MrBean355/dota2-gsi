import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    `maven-publish`
}

group = "com.github.mrbean355"
ext {
    set("artifactId", "dota2-gsi")
}
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-netty:1.4.1")
    implementation("io.ktor:ktor-gson:1.4.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

java {
    withSourcesJar()
}

publishing {
    repositories {
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
            credentials {
                username = System.getenv("SONATYPE_NEXUS_USERNAME") ?: ""
                password = System.getenv("SONATYPE_NEXUS_PASSWORD") ?: ""
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            artifactId = extra["artifactId"].toString()
            from(components["java"])
        }
    }
}