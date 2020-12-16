package com.github.mrbean355.dota2.server

import com.github.mrbean355.dota2.GameState
import com.github.mrbean355.dota2.json.*
import com.github.mrbean355.dota2.util.registerTypeAdapter
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

class GameStateServer(port: Int, private val onNewGameState: (GameState) -> Unit) {
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
                    onNewGameState(call.receive())
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