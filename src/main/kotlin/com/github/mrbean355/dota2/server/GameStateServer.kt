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

    fun start(wait: Boolean) {
        server.start(wait)
    }

    fun stop(gracePeriodMillis: Long, timeoutMillis: Long) {
        server.stop(gracePeriodMillis, timeoutMillis)
    }
}

fun interface GameStateListener {
    operator fun invoke(gameState: GameState)
}