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
        return Abilities(root.entrySet().map {
            context.deserialize(it.value, Ability::class.java)
        })
    }
}