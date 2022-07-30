# Dota 2 - Game State Integration - JVM

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.mrbean355/dota2-gsi/badge.png)](https://search.maven.org/artifact/com.github.mrbean355/dota2-gsi)
[![Documentation](https://img.shields.io/badge/KDoc-GitHub%20Pages-B125EA)](https://mrbean355.github.io/dota2-gsi)
[![Build project](https://github.com/MrBean355/dota2-gsi/actions/workflows/build-project.yml/badge.svg?branch=main)](https://github.com/MrBean355/dota2-gsi/actions/workflows/build-project.yml)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=MrBean355_dota2-gsi&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=MrBean355_dota2-gsi)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=MrBean355_dota2-gsi&metric=coverage)](https://sonarcloud.io/summary/new_code?id=MrBean355_dota2-gsi)

This library is a Java/Kotlin wrapper for receiving game state updates from Dota 2.

Game State Integration (GSI) is a feature built into Dota 2 that periodically sends JSON data on the current game state.
This data can be examined to perform the necessary actions. For example, play a notification sound shortly before the
bounty runes spawn every 3 minutes.

This is the mechanism that is used to create those fancy overlays seen at the tournaments. More information can be found
on [Valve's developer wiki](https://developer.valvesoftware.com/wiki/Counter-Strike:_Global_Offensive_Game_State_Integration)
. It is written for CS:GO, but the same applies to Dota 2.

## Getting Started

1. [**Set up the Dota 2 client**](https://github.com/MrBean355/dota2-gsi/wiki/Dota-2-Setup) to send game state
   information to the program.
2. **Add the dependency**. Add the dependency to the project and start coding!

### Adding the Dependency

The library is published to Maven Central; make sure it is added as a repository. Then simply add a dependency on the
library:

```groovy
repositories {
    mavenCentral()
}

dependencies {
    // Check this Maven Central page for the latest released version:
    // https://search.maven.org/artifact/com.github.mrbean355/dota2-gsi/
    implementation "com.github.mrbean355:dota2-gsi:x.x.x"
}
```

## Sample Code

*Note: the library is written in Kotlin, but can be used from Java code as well.*

0. **Important**: Dota 2 must be set up first! See [the Wiki](https://github.com/MrBean355/dota2-gsi/wiki/Dota-2-Setup)
   for more information.
1. Create a `GameStateServer` instance with the _same port_ that was used when setting up Dota.
2. Register at least one listener to get called when the game state changes.
3. Start up the server!

Here's a simple example of how easy it is:

```kotlin
fun main() {
    // Create a server which listens on the port configured during Dota setup:
    GameStateServer(44444)
        // Register one or more listeners: 
        .setPlayingListener { gameState ->
            // Do something with the received gameState object:
            val clockTime = gameState.map?.clockTime
            println("The clock time is $clockTime seconds.")
        }
        // Start the server, blocking the thread so the program doesn't immediately exit:
        .start() // Alternatively startAsync() will not block the thread.
}
```

Have a look at the simple [demo project](demo/src/main/java/com/github/mrbean355/dota2/demo) for some more examples,
including Java code.

The [KDoc/Javadoc](https://mrbean355.github.io/dota2-gsi) is also available online for browsing.

## Snapshots

Snapshots of the current in-development release are also published to Maven Central. They can be used in projects as
well, but are likely to be unstable. Include the snapshot repository and dependency:

```groovy
repositories {
    maven {
        url 'https://oss.sonatype.org/content/repositories/snapshots/'
    }
}

dependencies {
    // Check this Maven Central page for the latest snapshot version:
    // https://oss.sonatype.org/content/repositories/snapshots/com/github/mrbean355/dota2-gsi/
    implementation "com.github.mrbean355:dota2-gsi:x.x.x-SNAPSHOT"
}

// This is needed to tell Gradle to always check for new uploads of the snapshot.
// Without this, Gradle will only check once per day.
configurations.configureEach {
    resolutionStrategy {
        cacheChangingModulesFor(0, "seconds")
    }
}
```

## Contributing

Contributions are welcome! This includes bug reports, feature requests and general suggestions. Feel free to
[create a GitHub issue](https://github.com/MrBean355/dota2-gsi/issues) to provide feedback.

[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-white.svg)](https://sonarcloud.io/summary/new_code?id=MrBean355_dota2-gsi)
