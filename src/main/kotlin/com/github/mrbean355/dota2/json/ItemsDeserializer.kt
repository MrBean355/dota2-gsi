package com.github.mrbean355.dota2.json

import com.github.mrbean355.dota2.Item
import com.github.mrbean355.dota2.Items
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

internal class ItemsDeserializer : JsonDeserializer<Items> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Items {
        val root = json.asJsonObject

        val inventory = root.entrySet()
                .filter { it.key.startsWith("slot") }
                .map { context.deserialize<Item>(it.value, Item::class.java) }

        val stash = root.entrySet()
                .filter { it.key.startsWith("stash") }
                .map { context.deserialize<Item>(it.value, Item::class.java) }

        return Items(inventory, stash)
    }
}