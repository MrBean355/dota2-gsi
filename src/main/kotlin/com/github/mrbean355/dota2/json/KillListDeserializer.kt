package com.github.mrbean355.dota2.json

import com.github.mrbean355.dota2.KillList
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

internal class KillListDeserializer : JsonDeserializer<KillList> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): KillList {
        val root = json.asJsonObject
        return KillList(root.entrySet().map { it.value.asInt })
    }
}