# Dota 2 Game State Integration

This library is a Java/Kotlin wrapper for receiving game state updates from Dota 2.

Game State Integration (GSI) is a feature built into Dota 2 that sends JSON data on the current game state. This data
can be examined to perform the necessary actions. For example, play a notification sound shortly before the bounty runes
spawn every 3 minutes.

This is the mechanism that is used to create those fancy overlays seen at the tournaments.

## Try It Out

To get started, add a dependency on the library. Next, create a game state integration file in your Dota 2 folder.
Finally, write some code to start receiving game state updates!

### Add The Dependency

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.mrbean355/dota2-gsi/badge.png)](https://search.maven.org/artifact/com.github.mrbean355/dota2-gsi)

The library is published to Maven Central. Update your `build.gradle` file as follows. Other build systems (e.g. Maven)
hopefully have their own way of doing this.

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'com.github.mrbean355:dota2-gsi:1.0.0'
}
```

### Set Up Dota 2

In order for Dota 2 to send data on the current game state, a simple text file must be created. Navigate to this folder
within your Dota 2 installation folder, creating folders that don't exist already:

```
dota 2 beta/game/dota/cfg/gamestate_integration
```

Next create a new text file inside this folder named `gamestate_integration_*.cfg`, for example:

```
gamestate_integration_test.cfg
```

Open the file in a text editor and add this content:

```
"My GSI test"
{
    "uri"           "http://localhost:44444"
    "timeout"       "5.0"
    "buffer"        "0.1"
    "throttle"      "0.1"
    "heartbeat"     "30.0"
    "data"
    {
        "provider"      "1"
        "map"           "1"
        "player"        "1"
        "hero"          "1"
        "abilities"     "1"
        "items"         "1"
    }
}
```

Note that you can change the port number (`44444`) if necessary.

## Sample Code

Have a look at the simple [demo project](demo/src/main/kotlin/Main.kt) for some sample code.

## Snapshots

You can try out the latest snapshots (built from the `develop` branch). Note that these are development versions, and
will likely be unstable. Update your `build.gradle` file to point to the snapshots repository:

```groovy
repositories {
    maven {
        url 'https://oss.sonatype.org/content/repositories/snapshots/'
    }
}

dependencies {
    implementation "com.github.mrbean355:dota2-gsi:1.1.0-SNAPSHOT"
}
```

The list of available snapshot versions can be found
on [Maven Central](https://oss.sonatype.org/content/repositories/snapshots/com/github/mrbean355/dota2-gsi/).

## Contributing

Contributions are welcome! This includes pull requests as well as suggestions of things you'd like to see in the
library. Feel free to [create a GitHub issue](https://github.com/MrBean355/dota2-gsi/issues) to provide your feedback.