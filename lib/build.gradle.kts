import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.net.URI

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("org.jetbrains.dokka")
    id("org.jetbrains.kotlinx.binary-compatibility-validator")
    id("jacoco")
    id("org.sonarqube")
    `maven-publish`
    signing
}

group = "com.github.mrbean355"
val artifactId by extra("dota2-gsi")
version = "3.0.0"

dependencies {
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.contentNegotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    api(libs.kotlinx.serialization.json)

    testImplementation(platform(testLibs.junit.bom))
    testImplementation(testLibs.junit.jupiter)
    testImplementation(testLibs.junit.launcher)
    testImplementation(testLibs.mockK)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
        freeCompilerArgs.add("-opt-in=com.github.mrbean355.dota2.annotation.ExperimentalGameState")
    }
}

tasks.withType<Jar> {
    archiveBaseName.set(artifactId)
    dependsOn("apiCheck")
}

dokka {
    moduleName.set(artifactId)
    dokkaSourceSets.named("main") {
        displayName.set("JVM")
        includes.from("docs/module.md")
        sourceLink {
            localDirectory.set(file("src/main/kotlin"))
            remoteUrl.set(URI("https://github.com/MrBean355/dota2-gsi/blob/main/${project.name}/src/main/kotlin"))
            remoteLineSuffix.set("#L")
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

jacoco {
    toolVersion = "0.8.14"
}

tasks.withType(JacocoReport::class.java) {
    reports.xml.required.set(true)
}

tasks.withType(org.sonarqube.gradle.SonarTask::class.java) {
    dependsOn("jacocoTestReport")
}

sonar {
    properties {
        property("sonar.projectKey", "MrBean355_dota2-gsi")
        property("sonar.organization", "mrbean355")
        property("sonar.host.url", "https://sonarcloud.io")
    }
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
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
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
        "https://ossrh-staging-api.central.sonatype.com/service/local/staging/deploy/maven2/"
    } else {
        "https://central.sonatype.com/repository/maven-snapshots/"
    }
}