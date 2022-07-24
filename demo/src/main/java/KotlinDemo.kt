/*
 * Copyright 2022 Michael Johnston
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mrbean355.dota2.demo

import com.github.mrbean355.dota2.gamestate.PlayingGameState
import com.github.mrbean355.dota2.server.GameStateServer
import java.io.File

/**
 * Start up a server that listens on the given port for data from Dota 2.
 * When Dota sends us a new game state, we can check various properties.
 * See JavaDemo.java for the Java version.
 */
fun main() {
    // Create a server and add various listeners for game state updates.
    // All listeners are optional; you only need to add the ones you want.
    // Remember to call start() or startAsync() so that the server actually runs!
    // Check the documentation of GameStateServer for more info.
    GameStateServer(12345)
        .setPlayingListener { state ->
            println("Playing match ${state.map?.matchId}.")
            onNewGameState(state)
        }
        .setSpectatingListener { state ->
            println("Spectating match ${state.map?.matchId}.")
        }
        .setIdleListener {
            println("Not in a match.")
        }
        .setGenericListener { state ->
            println("State update: ${state::class.simpleName}.")
        }
        .setErrorHandler { t, json ->
            println("Error processing data: $t.")
            // Store the problematic data in a file for a bug report:
            File("error.json").writeText(json)
        }
        .start()
}

// Store the previous game state, so we can compare values when a new state comes in:
private var previousState: PlayingGameState? = null

private fun onNewGameState(newState: PlayingGameState) {
    // Current game time (number of seconds since first creeps spawned):
    val clockTime = newState.map?.clockTime ?: 0

    // If the current time is a multiple of 3 minutes, the bounty runes have just spawned:
    if (clockTime % 180 == 0) {
        println("Bounty runes just spawned!")
    }

    // If we have stored the previous state, we can compare some values to see how they are
    // different in the new state:
    previousState?.let { previousState ->
        val kills = newState.player?.kills ?: 0
        val previousKills = previousState.player?.kills ?: 0

        // The player's current kills are higher than the previous kills:
        if (kills > previousKills) {
            println("You got a kill, congrats!")
        }

        val deaths = newState.player?.deaths ?: 0
        val previousDeaths = previousState.player?.deaths ?: 0

        // The player's current deaths are higher than the previous deaths:
        if (deaths > previousDeaths) {
            println("You died, too bad :(")
        }
    }

    // Previous state becomes the current state, for the next time the game state updates:
    previousState = newState
}