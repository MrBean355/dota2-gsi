package com.github.mrbean355.dota2.json

import com.github.mrbean355.dota2.Hero
import com.github.mrbean355.dota2.Heroes
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

internal class HeroesDeserializer : JsonDeserializer<Heroes> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Heroes {
        val root = json.asJsonObject
        return Heroes(if (root.has("xpos")) {
            mapOf("player0" to context.deserialize(root, Hero::class.java))
        } else {
            val heroes = mutableMapOf<String, Hero>()
            root.entrySet().forEach { team ->
                team.value.asJsonObject.entrySet().forEach { player ->
                    heroes += player.key to context.deserialize(player.value, Hero::class.java)
                }
            }
            heroes
        })
    }
}