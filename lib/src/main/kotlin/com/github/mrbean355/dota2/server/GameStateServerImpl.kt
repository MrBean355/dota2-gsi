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

package com.github.mrbean355.dota2.server

import com.github.mrbean355.dota2.gamestate.GameState
import com.github.mrbean355.dota2.gamestate.IdleGameState
import com.github.mrbean355.dota2.gamestate.PlayingGameState
import com.github.mrbean355.dota2.gamestate.SpectatingGameState
import com.github.mrbean355.dota2.json.parseGameState
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.request.receiveText
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.util.pipeline.PipelineContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.slf4j.LoggerFactory

/**
 * Not for direct use; see [GameStateServer].
 */
internal class GameStateServerImpl(
    port: Int
) : GameStateServer {

    private var genericListener: GameStateServer.Listener<GameState>? = null
    private var playingListener: GameStateServer.Listener<PlayingGameState>? = null
    private var spectatingListener: GameStateServer.Listener<SpectatingGameState>? = null
    private var idleListener: GameStateServer.Listener<IdleGameState>? = null
    private var errorHandler: GameStateServer.ErrorHandler = GameStateServer.ErrorHandler { t, _ ->
        LoggerFactory.getLogger(GameStateServerImpl::class.java)
            .error("Error parsing game state! Set a custom error handler with setErrorHandler().", t)
    }
    private var authentication: Map<String, String> = emptyMap()
    private var json = createJson()

    private val server: ApplicationEngine = embeddedServer(Netty, port) {
        routing {
            post("/") {
                handleGameStateRequest()
            }
        }
    }

    override fun setGenericListener(listener: GameStateServer.Listener<GameState>): GameStateServer {
        genericListener = listener
        return this
    }

    override fun setPlayingListener(listener: GameStateServer.Listener<PlayingGameState>): GameStateServer {
        playingListener = listener
        return this
    }

    override fun setSpectatingListener(listener: GameStateServer.Listener<SpectatingGameState>): GameStateServer {
        spectatingListener = listener
        return this
    }

    override fun setIdleListener(listener: GameStateServer.Listener<IdleGameState>): GameStateServer {
        idleListener = listener
        return this
    }

    override fun setErrorHandler(handler: GameStateServer.ErrorHandler): GameStateServer {
        errorHandler = handler
        return this
    }

    override fun enableStrictDeserialization(): GameStateServer {
        json = createJson(lenient = false)
        return this
    }

    override fun requireAuthentication(auth: Map<String, String>): GameStateServer {
        require(auth.isNotEmpty()) { "Map must not be empty" }
        authentication = auth
        return this
    }

    override fun start(): GameStateServer {
        server.start(wait = true)
        return this
    }

    override fun startAsync(): GameStateServer {
        server.start(wait = false)
        return this
    }

    override fun stop(gracePeriodMillis: Long, timeoutMillis: Long) {
        server.stop(gracePeriodMillis, timeoutMillis)
    }

    private fun createJson(lenient: Boolean = true): Json {
        return Json { ignoreUnknownKeys = lenient }
    }

    private suspend fun PipelineContext<Unit, ApplicationCall>.handleGameStateRequest() {
        val text = call.receiveText()
        try {
            val state = json.parseToJsonElement(text).jsonObject.let {
                authenticate(it)
                parseGameState(it, json)
            }
            genericListener?.invoke(state)
            when (state) {
                is PlayingGameState -> playingListener?.invoke(state)
                is SpectatingGameState -> spectatingListener?.invoke(state)
                is IdleGameState -> idleListener?.invoke(state)
            }
        } catch (t: Throwable) {
            errorHandler(t, text)
        }
        call.respond(HttpStatusCode.OK)
    }

    private fun authenticate(root: JsonObject) {
        val received = root["auth"] as JsonObject?
        if (received.isNullOrEmpty()) {
            check(authentication.isEmpty()) {
                "Unauthorized request, auth=$received"
            }
            return
        }

        check(received.size == authentication.size) {
            "Unauthorized request, auth=$received"
        }

        authentication.forEach { (key, value) ->
            check(received[key]?.jsonPrimitive?.content == value) {
                "Unauthorized request, auth=$received"
            }
        }
    }
}