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

import com.github.mrbean355.dota2.GameState
import com.github.mrbean355.dota2.server.GameStateServer

/**
 * Start up a server that listens on the given port for data from Dota 2.
 * When Dota sends us a new game state, we can check various properties.
 */
fun main() {
    GameStateServer(12345) { state ->
        onNewGameState(state)
    }.start(wait = true)
}

// Store the previous game state, so we can compare values when a new state comes in:
private var previousState: GameState? = null

private fun onNewGameState(gameState: GameState) {
    // Current game time (number of seconds since first creeps spawned):
    val clockTime = gameState.map?.clockTime ?: 0
    // Bounty runes spawn every 180 seconds (3 minutes):
    val bountyRuneTimer = 3 * 60
    // If the current time is a multiple of 3 minutes, the bounties have just spawned:
    if (clockTime % bountyRuneTimer == 0L) {
        println("Bounty runes just spawned!")
    }

    // If a previous state exists, we can compare some values to see how they are
    // different in the new state:
    previousState?.let { previous ->
        val previousKills = previous.players?.values.orEmpty().first().kills
        val currentKills = gameState.players?.values.orEmpty().first().kills
        // If the current kills are higher than in the previous state:
        if (currentKills > previousKills) {
            println("You got a kill, congrats!")
        }
    }

    // Previous state becomes the current state, for the next time the game state updates:
    previousState = gameState
}