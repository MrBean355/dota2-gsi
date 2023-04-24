/*
 * Copyright 2023 Michael Johnston
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mrbean355.dota2.demo;

import com.github.mrbean355.dota2.gamestate.PlayingGameState;
import com.github.mrbean355.dota2.server.GameStateServer;

import java.io.FileWriter;
import java.io.IOException;

public class JavaDemo {

    public static void main(String[] args) {
        new JavaDemo().run();
    }

    /**
     * Start up a server that listens on the given port for data from Dota 2.
     * When Dota sends us a new game state, we can check various properties.
     * See KotlinDemo.kt for the Kotlin version.
     */
    private void run() {
        // Create a server and add various listeners for game state updates.
        // All listeners are optional; you only need to add the ones you want.
        // Remember to call start() or startAsync() so that the server actually runs!
        // Check the documentation of GameStateServer for more info.
        GameStateServer.create(12345)
                .setPlayingListener(state -> {
                    System.out.println("Playing match " + state.getMap().getMatchId() + ".");
                    onNewGameState(state);
                })
                .setSpectatingListener(state -> System.out.println("Spectating match " + state.getMap().getMatchId() + "."))
                .setIdleListener(state -> System.out.println("Not in a match."))
                .setGenericListener(state -> System.out.println("State update: " + state.getClass().getSimpleName() + "."))
                .setErrorHandler((t, json) -> {
                    System.out.println("Error processing data: " + t + ".");
                    // Store the problematic data in a file for a bug report:
                    writeErrorLog(json);
                })
                .start();
    }

    // Store the previous game state, so we can compare values when a new state comes in:
    private PlayingGameState previousState = null;

    private void onNewGameState(PlayingGameState newState) {
        if (newState.getMap() != null) {
            // Current game time (number of seconds since first creeps spawned):
            int clockTime = newState.getMap().getClockTime();

            // If the current time is a multiple of 3 minutes, the bounty runes have just spawned:
            if (clockTime % 180 == 0) {
                System.out.println("Bounty runes just spawned!");
            }
        }

        // If we have stored the previous state, we can compare some values to see how they are
        // different in the new state:
        if (previousState != null) {
            if (newState.getPlayer() != null && previousState.getPlayer() != null) {

                // The player's current kills are higher than the previous kills:
                if (newState.getPlayer().getKills() > previousState.getPlayer().getKills()) {
                    System.out.println("You got a kill, congrats!");
                }

                // The player's current deaths are higher than the previous deaths:
                if (newState.getPlayer().getDeaths() > previousState.getPlayer().getDeaths()) {
                    System.out.println("You died, too bad :(");
                }
            }
        }

        // Previous state becomes the current state, for the next time the game state updates:
        previousState = newState;
    }

    private void writeErrorLog(String json) {
        try {
            FileWriter writer = new FileWriter("error.json");
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing data: " + e + ".");
        }
    }
}
