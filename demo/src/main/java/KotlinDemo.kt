/*
 * Copyright 2021 Michael Johnston
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

import com.github.mrbean355.dota2.PlayingGameState
import com.github.mrbean355.dota2.server.GameStateServer

/**
 * Start up a server that listens on the given port for data from Dota 2.
 * When Dota sends us a new game state, we can check various properties.
 * See JavaDemo.java for the Java version.
 */
fun main() {
    GameStateServer(12345)
        // Get notified when Dota sends a new game state.
        // This will only be called when the user is playing a Dota match, NOT spectating.
        .setPlayingListener {
            val gameTime = it.map?.clockTime
        }
        // Block the current thread so the program keeps running.
        .start(wait = true)
}

// Store the previous game state, so we can compare values when a new state comes in:
private var previousState: PlayingGameState? = null

private fun onNewGameState(newState: PlayingGameState) {
    // Current game time (number of seconds since first creeps spawned):
    val clockTime = newState.map?.clockTime ?: 0

    // If the current time is a multiple of 3 minutes, the bounty runes have just spawned:
    if (clockTime % 180 == 0L) {
        println("Bounty runes just spawned!")
    }

    // If we have stored the previous state, we can compare some values to see how they are
    // different in the new state:
    previousState?.let { previousState ->

        // The player's current kills are higher than the previous kills:
        if (newState.player.kills > previousState.player.kills) {
            println("You got a kill, congrats!")
        }

        // The player's current deaths are higher than the previous deaths:
        if (newState.player.deaths > previousState.player.deaths) {
            println("You died, too bad :(")
        }
    }

    // Previous state becomes the current state, for the next time the game state updates:
    previousState = newState
}