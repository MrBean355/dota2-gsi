package com.github.mrbean355.dota2.json

import com.github.mrbean355.dota2.HeroItems
import com.github.mrbean355.dota2.Item
import com.github.mrbean355.dota2.Items
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

internal class ItemsDeserializer : JsonDeserializer<Items> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Items {
        val root = json.asJsonObject
        return Items(if (root.has("slot0")) {
            mapOf("player0" to mapItems(root, context))
        } else {
            val items = mutableMapOf<String, HeroItems>()
            root.entrySet().forEach { team ->
                team.value.asJsonObject.entrySet().forEach { player ->
                    items += player.key to mapItems(player.value.asJsonObject, context)
                }
            }
            items
        })
    }

    private fun mapItems(root: JsonObject, context: JsonDeserializationContext): HeroItems {
        val inventory = root.entrySet()
            .filter { it.key.startsWith("slot") }
            .map { context.deserialize<Item>(it.value, Item::class.java) }

        val stash = root.entrySet()
            .filter { it.key.startsWith("stash") }
            .map { context.deserialize<Item>(it.value, Item::class.java) }

        return HeroItems(inventory, stash)
    }
}