import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    `maven-publish`
    signing
}

group = "com.github.mrbean355"
ext {
    set("artifactId", "dota2-gsi")
}
version = "1.1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    api("io.ktor:ktor-server-netty:1.6.4")
    api("io.ktor:ktor-gson:1.6.4")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    repositories {
        maven {
            url = uri(mavenCentralUrl())
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
            pom {
                name.set("Dota 2 GSI")
                description.set("JVM library for receiving game state updates from Dota 2.")
                url.set("https://github.com/MrBean355/dota2-gsi")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        distribution.set("repo")
                    }
                }
                scm {
                    url.set("https://github.com/MrBean355/dota2-gsi")
                    connection.set("scm:git:git://github.com/MrBean355/dota2-gsi.git")
                    developerConnection.set("scm:git:ssh://github.com/MrBean355/dota2-gsi.git")
                }
                developers {
                    developer {
                        id.set("MrBean355")
                        name.set("Michael Johnston")
                    }
                }
            }
        }
    }
}

signing {
    sign(publishing.publications["maven"])
    isRequired = isReleaseBuild()
}

fun isReleaseBuild(): Boolean = !version.toString().endsWith("SNAPSHOT")

fun mavenCentralUrl(): String {
    return if (isReleaseBuild()) {
        "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
    } else {
        "https://oss.sonatype.org/content/repositories/snapshots/"
    }
}