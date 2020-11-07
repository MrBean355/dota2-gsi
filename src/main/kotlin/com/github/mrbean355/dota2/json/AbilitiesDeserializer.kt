package com.github.mrbean355.dota2.json

import com.github.mrbean355.dota2.Abilities
import com.github.mrbean355.dota2.Ability
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

internal class AbilitiesDeserializer : JsonDeserializer<Abilities> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Abilities {
        val root = json.asJsonObject
        return Abilities(if (root.has("ability0")) {
            mapOf("player0" to root.entrySet().map {
                context.deserialize(it.value, Ability::class.java)
            })
        } else {
            val map = mutableMapOf<String, List<Ability>>()
            root.entrySet().forEach { team ->
                team.value.asJsonObject.entrySet().forEach { player ->
                    map += player.key to player.value.asJsonObject.entrySet().map { ability ->
                        context.deserialize(ability.value, Ability::class.java)
                    }
                }
            }
            map
        })
    }
}