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

package com.github.mrbean355.dota2.server

import com.github.mrbean355.dota2.GameState
import com.github.mrbean355.dota2.IdleGameState
import com.github.mrbean355.dota2.PlayingGameState
import com.github.mrbean355.dota2.SpectatingGameState
import com.github.mrbean355.dota2.json.parseGameState
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.call
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.request.receiveText
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

/**
 * Server which listens for game state updates from Dota 2.
 *
 * The Dota client must be configured to send updates to the server.
 *
 * Add this launch option to Dota 2 to enable GSI:
 * ```
 * -gamestateintegration
 * ```
 * Create a text file like `gamestate_integration_test.cfg` in the folder:
 * ```
 * dota 2 beta/game/dota/cfg/gamestate_integration/
 * ```
 * With contents like:
 * ```
 * "My GSI test"
 * {
 *     "uri"           "http://localhost:44444"
 *     "timeout"       "5.0"
 *     "buffer"        "0.1"
 *     "throttle"      "0.1"
 *     "heartbeat"     "30.0"
 *     "data"
 *     {
 *         "provider"      "1"
 *         "map"           "1"
 *         "player"        "1"
 *         "hero"          "1"
 *         "abilities"     "1"
 *         "items"         "1"
 *     }
 * }
 * ```
 * Note that the port (`44444`) can be changed to any valid port.
 *
 * @param port GSI port number, as configured in the .cfg file.
 */
class GameStateServer(
    port: Int
) {
    private var genericListener: GameStateListener<GameState> = GameStateListener { }
    private var playingListener: GameStateListener<PlayingGameState> = GameStateListener { }
    private var spectatingListener: GameStateListener<SpectatingGameState> = GameStateListener { }
    private var idleListener: GameStateListener<IdleGameState> = GameStateListener { }
    private var errorHandler: ErrorHandler = ErrorHandler { _, _ -> }

    private val server: ApplicationEngine = embeddedServer(Netty, port) {
        routing {
            post {
                val json = call.receiveText()
                try {
                    val state = parseGameState(json)
                    genericListener(state)
                    when (state) {
                        is PlayingGameState -> playingListener(state)
                        is SpectatingGameState -> spectatingListener(state)
                        is IdleGameState -> idleListener(state)
                    }
                } catch (t: Throwable) {
                    errorHandler(t, json)
                }
                call.respond(OK)
            }
        }
    }

    /**
     * Set a listener to get called when a "generic" game state update happens.
     * This will be called when the client is playing or spectating a match.
     *
     * @param listener Listener to set.
     * @return this object.
     */
    fun setGenericListener(listener: GameStateListener<GameState>): GameStateServer {
        genericListener = listener
        return this
    }

    /**
     * Set a listener to get called when a "playing" game state update happens.
     * This will only be called when the client is playing a match (not when spectating).
     *
     * @param listener Listener to set.
     * @return this object.
     */
    fun setPlayingListener(listener: GameStateListener<PlayingGameState>): GameStateServer {
        playingListener = listener
        return this
    }

    /**
     * Set a listener to get called when a "spectating" game state update happens.
     * This will only be called when the client is spectating a match (not when playing).
     *
     * @param listener Listener to set.
     * @return this object.
     */
    fun setSpectatingListener(listener: GameStateListener<SpectatingGameState>): GameStateServer {
        spectatingListener = listener
        return this
    }

    /**
     * Set a listener to get called when an "idle" game state update happens.
     * This will only be called when the client is neither playing nor spectating a match.
     * This usually happens when the client is on the main menu.
     *
     * @param listener Listener to set.
     * @return this object.
     */
    fun setIdleListener(listener: GameStateListener<IdleGameState>): GameStateServer {
        idleListener = listener
        return this
    }

    /**
     * Set a handler to get called when an exception is thrown during JSON deserialization.
     * Errors can happen when the data returned from Dota 2 is in an unexpected format.
     * Please consider reporting such errors, along with the JSON received in the [ErrorHandler]:
     * https://github.com/MrBean355/dota2-gsi/issues
     *
     * @param handler Handler to set.
     * @return this object.
     */
    fun setErrorHandler(handler: ErrorHandler): GameStateServer {
        errorHandler = handler
        return this
    }

    /**
     * Starts this server.
     *
     * @param wait if true, this function does not return until the server is stopped.
     * @return this object.
     */
    fun start(wait: Boolean): GameStateServer {
        server.start(wait)
        return this
    }

    /**
     * Stops this server.
     *
     * @param gracePeriodMillis the maximum amount of time for activity to cool down.
     * @param timeoutMillis the maximum amount of time to wait until server stops gracefully.
     */
    fun stop(gracePeriodMillis: Long, timeoutMillis: Long) {
        server.stop(gracePeriodMillis, timeoutMillis)
    }
}

fun interface GameStateListener<T : GameState> {

    operator fun invoke(gameState: T)

}

fun interface ErrorHandler {

    operator fun invoke(throwable: Throwable, json: String)

}