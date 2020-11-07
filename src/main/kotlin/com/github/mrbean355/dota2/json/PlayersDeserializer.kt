package com.github.mrbean355.dota2.json

import com.github.mrbean355.dota2.Player
import com.github.mrbean355.dota2.Players
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

internal class PlayersDeserializer : JsonDeserializer<Players> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Players {
        val root = json.asJsonObject
        return Players(if (root.has("steamid")) {
            mapOf("player0" to context.deserialize(root, Player::class.java))
        } else {
            val players = mutableMapOf<String, Player>()
            root.entrySet().forEach { team ->
                team.value.asJsonObject.entrySet().forEach { player ->
                    players += player.key to context.deserialize(player.value, Player::class.java)
                }
            }
            players
        })
    }
}