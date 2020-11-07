package com.github.mrbean355.dota2.server

import com.github.mrbean355.dota2.GameState
import com.github.mrbean355.dota2.PlayingGameState
import com.github.mrbean355.dota2.SpectatedPlayer
import com.github.mrbean355.dota2.SpectatingGameState
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
import kotlin.concurrent.thread

class GameStateServer(port: Int) {
    private var playing: (PlayingGameState) -> Unit = {}
    private var spectating: (SpectatingGameState) -> Unit = {}
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
                    val gameState = call.receive<GameState>()
                    if (gameState.players == null) {
                        return@post
                    }
                    if (gameState.players.orEmpty().size == 1) {
                        processPlaying(gameState)
                    } else {
                        processSpectating(gameState)
                    }
                } catch (t: Throwable) {
                    log.error("Error receiving game state", t)
                }
                call.respond(OK)
            }
        }
    }

    fun whenPlaying(callback: (PlayingGameState) -> Unit): GameStateServer {
        this.playing = callback
        return this
    }

    fun whenSpectating(callback: (SpectatingGameState) -> Unit): GameStateServer {
        this.spectating = callback
        return this
    }

    fun start(wait: Boolean) {
        server.start(wait)
    }

    fun startAsync() {
        thread(isDaemon = true) {
            start(wait = true)
        }
    }

    fun stop(gracePeriodMillis: Long, timeoutMillis: Long) {
        server.stop(gracePeriodMillis, timeoutMillis)
    }

    private fun processPlaying(gameState: GameState) {
        gameState.map ?: return
        val player = gameState.players?.values?.firstOrNull() ?: return
        val hero = gameState.heroes?.values?.firstOrNull() ?: return
        val abilities = gameState.abilities?.values?.firstOrNull() ?: return
        val items = gameState.items?.values?.firstOrNull() ?: return

        playing(PlayingGameState(gameState.provider, gameState.map, player, hero, abilities, items))
    }

    private fun processSpectating(gameState: GameState) {
        gameState.map ?: return
        gameState.players ?: return
        gameState.heroes ?: return
        gameState.abilities ?: return
        gameState.items ?: return

        val players: Collection<SpectatedPlayer> = gameState.players.map {
            SpectatedPlayer(
                    it.value,
                    gameState.heroes.getValue(it.key),
                    gameState.abilities.getValue(it.key),
                    gameState.items.getValue(it.key)
            )
        }

        spectating(SpectatingGameState(gameState.provider, gameState.map, players))
    }
}