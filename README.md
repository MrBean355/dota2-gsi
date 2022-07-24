# Dota 2 - Game State Integration - JVM

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.mrbean355/dota2-gsi/badge.png)](https://search.maven.org/artifact/com.github.mrbean355/dota2-gsi)
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

1. **Set up Dota 2**. Set up the Dota 2 client to send game state information to the program.
2. **Add the dependency**. Add the dependency to the project and start coding!

### Set Up Dota 2

#### Enable GSI

Add the `-gamestateintegration` Dota 2 [launch option](https://help.steampowered.com/en/faqs/view/7D01-D2DD-D75E-2955)
to enable GSI.

#### Configure GSI

Navigate to this folder within the Dota 2 installation folder, creating folders that don't exist already:

```
dota 2 beta/game/dota/cfg/gamestate_integration
```

Create a new text file inside this folder. The file name must start with `gamestate_integration_`, and have the
extension `.cfg`. For example:

```
gamestate_integration_test.cfg
```

Open the file in a text editor and add this content:

```
"My GSI test"
{
    "uri"           "http://localhost:44444"
    "timeout"       "5.0"
    "buffer"        "0.5"
    "throttle"      "0.5"
    "heartbeat"     "30.0"
    "data"
    {
        "provider"      "1"
        "map"           "1"
        "player"        "1"
        "hero"          "1"
        "abilities"     "1"
        "items"         "1"
        "buildings"     "1"
        "draft"         "1"
        "wearables"     "1"
    }
}
```

**Note: the `data` section can be configured to return the desired data.** For example, the `buildings` line can be
removed if building data isn't required. This helps to improve performance as there is less data to process.

**Note: the `44444` in the URI can be changed to any valid port.**

Save & close the file. The setup is now complete!

### Add The Dependency

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

The library is built with JDK 8, using these dependencies:

- Ktor
- KotlinX Serialization (JSON)
- Kotlin Standard Library

Check the [settings file](settings.gradle.kts) to see what versions are being used. They will be transitively pulled
into the project, along with their own dependencies.

## Sample Code

*Note: the library is written in Kotlin, but can be used from Java code as well.*

1. Create a `GameStateServer` instance with the same port from the `.cfg` config file above.
2. Register at least one listener to get called when the game state changes.
3. Start up the server!

Here's a simple example of how easy it is:

```kotlin
fun main() {
    // Create a server:
    GameStateServer(44444)
        // Register one or more listeners: 
        .setPlayingListener { gameState ->
            // Do something with the received gameState object:
            val gameTime = gameState.map?.clockTime
        }
        // Start the server, blocking the thread so the program doesn't immediately exit:
        .start() // Alternatively startAsync() will not block the thread.
}
```

Have a look at the simple [demo project](demo/src/main/java/com/github/mrbean355/dota2/demo) for some more examples,
including Java code.

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
```

## Contributing

Contributions are welcome! This includes bug reports, feature requests and general suggestions. Feel free to
[create a GitHub issue](https://github.com/MrBean355/dota2-gsi/issues) to provide feedback.

[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-white.svg)](https://sonarcloud.io/summary/new_code?id=MrBean355_dota2-gsi)
