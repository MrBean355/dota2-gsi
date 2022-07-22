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

/**
 * Not for direct use; see [GameStateServer].
 */
internal class GameStateServerImpl(
    port: Int
) : GameStateServer {

    private var genericListener: GameStateServer.Listener<GameState> = GameStateServer.Listener { }
    private var playingListener: GameStateServer.Listener<PlayingGameState> = GameStateServer.Listener { }
    private var spectatingListener: GameStateServer.Listener<SpectatingGameState> = GameStateServer.Listener { }
    private var idleListener: GameStateServer.Listener<IdleGameState> = GameStateServer.Listener { }
    private var errorHandler: GameStateServer.ErrorHandler = GameStateServer.ErrorHandler { _, _ -> }

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

    override fun start(wait: Boolean): GameStateServer {
        server.start(wait)
        return this
    }

    override fun stop(gracePeriodMillis: Long, timeoutMillis: Long) {
        server.stop(gracePeriodMillis, timeoutMillis)
    }

    private suspend fun PipelineContext<Unit, ApplicationCall>.handleGameStateRequest() {
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
        call.respond(HttpStatusCode.OK)
    }
}