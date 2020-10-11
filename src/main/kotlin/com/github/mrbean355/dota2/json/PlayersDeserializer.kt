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
            listOf(context.deserialize(root, Player::class.java))
        } else {
            root.entrySet().map { it.value }.flatMap { team ->
                team.asJsonObject.entrySet().map {
                    context.deserialize(it.value, Player::class.java)
                }
            }
        })
    }
}