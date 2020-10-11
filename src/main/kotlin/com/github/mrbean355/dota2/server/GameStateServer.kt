package com.github.mrbean355.dota2.server

import com.github.mrbean355.dota2.GameState
import com.github.mrbean355.dota2.json.AbilitiesDeserializer
import com.github.mrbean355.dota2.json.ItemsDeserializer
import com.github.mrbean355.dota2.json.KillListDeserializer
import com.github.mrbean355.dota2.json.PlayersDeserializer
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
                registerTypeAdapter(PlayersDeserializer())
                registerTypeAdapter(KillListDeserializer())
                registerTypeAdapter(AbilitiesDeserializer())
                registerTypeAdapter(ItemsDeserializer())
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