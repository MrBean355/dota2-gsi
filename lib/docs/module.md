# Module dota2-gsi

This library is a Java/Kotlin wrapper for receiving game state updates from Dota 2.

Game State Integration (GSI) is a feature built into Dota 2 that periodically sends JSON data on the current game state.
This data can be examined to perform the necessary actions. For example, play a notification sound shortly before the
bounty runes spawn every 3 minutes.

More information can be found on the [project's Wiki](https://github.com/MrBean355/dota2-gsi/wiki).

## Dota 2 setup

Before writing some code, make sure you have
[set up the Dota client](https://github.com/MrBean355/dota2-gsi/wiki/Dota-2-Setup).

## Sample code

### Kotlin

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

### Java

```java
public class GsiDemo {

    public static void main(String[] args) {
        // Create a server which listens on the port configured during Dota setup:
        GameStateServer.create(44444)
                // Register one or more listeners: 
                .setPlayingListener(gameState -> {
                    // Do something with the received gameState object:
                    if (gameState.getMap() != null) {
                        int clockTime = gameState.getMap().getClockTime();
                        System.out.println("The clock time is " + clockTime + " seconds.");
                    }
                })
                // Start the server, blocking the thread so the program doesn't immediately exit:
                .start(); // Alternatively startAsync() will not block the thread.
    }
}
```

## Exploring

To see what classes and functions are available, you can get started here:

- [GameStateServer][com.github.mrbean355.dota2.server.GameStateServer]
- [PlayingGameState][com.github.mrbean355.dota2.gamestate.PlayingGameState]
- [SpectatingGameState][com.github.mrbean355.dota2.gamestate.SpectatingGameState]