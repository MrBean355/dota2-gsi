/*
 * Copyright 2022 Michael Johnston
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

package com.github.mrbean355.dota2.server

import com.github.mrbean355.dota2.gamestate.GameState
import com.github.mrbean355.dota2.gamestate.IdleGameState
import com.github.mrbean355.dota2.gamestate.PlayingGameState
import com.github.mrbean355.dota2.gamestate.SpectatingGameState

/**
 * Server which listens for game state updates from Dota 2.
 *
 * Make sure the Dota client is set up to send game state updates.
 * Find more information here: https://github.com/MrBean355/dota2-gsi
 *
 * Create an instance in Kotlin with the factory function:
 * ```
 * val server = GameStateServer(portNumber)
 * ```
 * Or in Java:
 * ```
 * GameStateServer server = GameStateServer.create(portNumber);
 * ```
 * Peruse the below functions to see what is available.
 */
interface GameStateServer {

    /**
     * Set a listener to get called when a "generic" game state update happens.
     * This will be called when the client is playing or spectating a match.
     *
     * @param listener Listener to set.
     * @return this object.
     */
    fun setGenericListener(listener: Listener<GameState>): GameStateServer

    /**
     * Set a listener to get called when a "playing" game state update happens.
     * This will only be called when the client is playing a match (not when spectating).
     *
     * @param listener Listener to set.
     * @return this object.
     */
    fun setPlayingListener(listener: Listener<PlayingGameState>): GameStateServer

    /**
     * Set a listener to get called when a "spectating" game state update happens.
     * This will only be called when the client is spectating a match (not when playing).
     *
     * @param listener Listener to set.
     * @return this object.
     */
    fun setSpectatingListener(listener: Listener<SpectatingGameState>): GameStateServer

    /**
     * Set a listener to get called when an "idle" game state update happens.
     * This will only be called when the client is neither playing nor spectating a match.
     * This usually happens when the client is on the main menu.
     *
     * @param listener Listener to set.
     * @return this object.
     */
    fun setIdleListener(listener: Listener<IdleGameState>): GameStateServer

    /**
     * Set a handler to get called when an exception is thrown during JSON deserialization.
     * Errors can happen when the data returned from Dota 2 is in an unexpected format.
     * Please consider reporting such errors, along with the JSON received in the [handler]:
     * https://github.com/MrBean355/dota2-gsi/issues
     *
     * @param handler Handler to set.
     * @return this object.
     */
    fun setErrorHandler(handler: ErrorHandler): GameStateServer

    /**
     * Optionally authenticate data coming from the Dota client using the `auth` section in
     * the Game State Integration config file. If data is received without the expected
     * authentication, it will be ignored and the error handler will be notified.
     *
     * There can be any number of keys and values inside the `auth` block. The keys and values
     * can be any valid strings (wrapped in double quotes).
     *
     * For example, in the config file:
     * ```
     * "auth"
     * {
     *   "token1" "2ec6f84406fb"
     *   "token2" "18156144b841"
     * }
     * ```
     * When creating the server:
     * ```
     * GameStateServer(port).requireAuthentication(
     *     mapOf(
     *         "token1" to "2ec6f84406fb",
     *         "token2" to "18156144b841"
     *     )
     * )
     * ```
     */
    fun requireAuthentication(auth: Map<String, String>): GameStateServer

    /**
     * Starts this server, blocking the current thread until the server is stopped.
     * **This function will NOT return until [stop] is called.**
     *
     * @return this object.
     */
    fun start(): GameStateServer

    /**
     * Starts this server without blocking the current thread.
     * **This function will return immediately.**
     *
     * @return this object.
     */
    fun startAsync(): GameStateServer

    /**
     * Stops this server.
     *
     * @param gracePeriodMillis the maximum amount of time for activity to cool down.
     * @param timeoutMillis the maximum amount of time to wait until server stops gracefully.
     */
    fun stop(gracePeriodMillis: Long, timeoutMillis: Long)

    fun interface Listener<T : GameState> {

        /**
         * @param gameState Deserialized game state, see [GameState] subclasses.
         */
        operator fun invoke(gameState: T)

    }

    fun interface ErrorHandler {

        /**
         * @param throwable The throwable that was thrown.
         * @param json The JSON blob from Dota that caused the error.
         */
        operator fun invoke(throwable: Throwable, json: String)

    }

    companion object {

        /**
         * Create a new instance of a [GameStateServer].
         *
         * @param port GSI port number, as configured in the .cfg file.
         */
        @JvmStatic
        @JvmName("create")
        operator fun invoke(port: Int): GameStateServer {
            return GameStateServerImpl(port)
        }
    }
}
