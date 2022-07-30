# Module dota2-gsi

This is a JVM library that **aims to make working with Dota 2's Game State Integration simple**. Game State
Integration (GSI) is a feature built into Dota 2 that allows us to ask Dota to periodically send us information on the
current game state. You can find more information
on [Valve's Developer Wiki for CS:GO](https://developer.valvesoftware.com/wiki/Counter-Strike:_Global_Offensive_Game_State_Integration)
(the same applies to Dota 2).

In the simplest terms, the library starts up a local server which listens for game state information from the Dota 2
client. It then deserializes this JSON data into type-safe objects for developers to work with.

**Why use a library for this?** The JSON data that Dota sends can vary significantly in structure, which makes simple
parsing very tricky. Specifically, the structure is different when the Dota client is playing a match compared to when
it is spectating a match. This library aims to remove this worry from the developer, and simply exposes type-safe
objects to work with.

## Getting Set Up

1. [**Set up your Dota 2 client**](https://github.com/MrBean355/dota2-gsi/wiki/Dota-2-Setup) to send game state
   information to your program.
2. [**Add the dependency**](https://github.com/MrBean355/dota2-gsi/wiki/Library-Guide#add-the-dependency) to your
   project.

## Sample Code

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

## Additional Resources

- The project's [Wiki](https://github.com/MrBean355/dota2-gsi/wiki).
- The full [API reference](https://mrbean355.github.io/dota2-gsi).
- The
  [demo project](https://github.com/MrBean355/dota2-gsi/tree/main/demo/src/main/java/com/github/mrbean355/dota2/demo)
  has some more involved examples.

## Exploring

To see what classes and functions are available, you can get started here:

- [GameStateServer][com.github.mrbean355.dota2.server.GameStateServer]
- [PlayingGameState][com.github.mrbean355.dota2.gamestate.PlayingGameState]
- [SpectatingGameState][com.github.mrbean355.dota2.gamestate.SpectatingGameState]