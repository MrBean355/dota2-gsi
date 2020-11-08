# Dota 2 Game State Integration

This library is a Java/Kotlin wrapper for receiving game state updates from Dota 2.

Game State Integration (GSI) is a feature built into Dota 2 that sends JSON data on the current game state. This data
can be examined to perform the necessary actions. For example, play a notification sound shortly before the bounty runes
spawn every 5 minutes.

## Try It Out
To get started, add a dependency on the library. Next, create a game state integration file in your Dota 2 folder.
Finally, write some code to start receiving game state updates!

### Add The Dependency
There are only snapshot versions at the moment, while the project is in early development. They are published to Maven
Central. Update your `build.gradle` file as follows. Other build systems (e.g. Maven) hopefully have their own way of
doing this.
```groovy
repositories {
    // Add this to be able to use snapshots:
    maven {
        url 'https://oss.sonatype.org/content/repositories/snapshots/'
    }
}

dependencies {
    // Add a dependency on the library:
    implementation 'com.github.mrbean355:dota2-gsi:1.0.0-SNAPSHOT'
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
Note that you can change the port number if necessary.

### Sample Code
Create an instance of `GameStateServer` which listens on the port you specified above.
```kotlin
fun startServer() {
    // Start up a server to listen for Dota 2 game state updates:
    GameStateServer(port = 44444)
            .whenPlaying(::onPlaying)
            .whenSpectating(::onSpectating)
            .startAsync()
}

// When you're playing a match, we only get data about you (not other players).
fun onPlaying(gameState: PlayingGameState) {
    val gameTime = gameState.map.clockTime // Current time in seconds.
    val bountyRuneTimer = 5 * 60 // Runes spawn every 5 minutes.

    // Is the current time is a multiple of the rune timer?
    if (gameTime % bountyRuneTimer == 0) {
        println("Bounty runes just spawned! Go pick them up.")
    }
}

// When spectating a game, we get data about each player.
fun onSpectating(gameState: SpectatingGameState) {
    val playerNames = gameState.players.joinToString { it.playerInfo.name }
    println("Spectating players: $playerNames")
}
```