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

package com.github.mrbean355.dota2.server

import com.github.mrbean355.dota2.GameState
import com.github.mrbean355.dota2.json.AbilitiesDeserializer
import com.github.mrbean355.dota2.json.HeroesDeserializer
import com.github.mrbean355.dota2.json.ItemsDeserializer
import com.github.mrbean355.dota2.json.KillListDeserializer
import com.github.mrbean355.dota2.json.PlayersDeserializer
import com.github.mrbean355.dota2.util.registerTypeAdapter
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

/**
 * Server which listens for game state updates from Dota 2.
 *
 * The Dota client must be configured to send updates to the server.
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
 */
class GameStateServer(
    port: Int,
    private val listener: GameStateListener
) {
    private val server: ApplicationEngine = embeddedServer(Netty, port) {
        install(ContentNegotiation) {
            gson {
                registerTypeAdapter(AbilitiesDeserializer())
                registerTypeAdapter(HeroesDeserializer())
                registerTypeAdapter(KillListDeserializer())
                registerTypeAdapter(ItemsDeserializer())
                registerTypeAdapter(PlayersDeserializer())
            }
        }
        routing {
            post {
                try {
                    listener(call.receive())
                } catch (t: Throwable) {
                    log.error("Error receiving game state", t)
                }
                call.respond(OK)
            }
        }
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

fun interface GameStateListener {
    operator fun invoke(gameState: GameState)
}