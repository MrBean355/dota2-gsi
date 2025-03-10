/*
 * Copyright 2023 Michael Johnston
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mrbean355.dota2.json.factory

import com.github.mrbean355.dota2.item.ItemImpl
import com.github.mrbean355.dota2.item.Items
import com.github.mrbean355.dota2.json.ClientMode
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

private const val JsonKey = "items"

internal object ItemsFactory {

    fun createForPlayer(root: JsonObject, json: Json): Items? {
        return root[JsonKey]?.jsonObject?.mapItems(json)
    }

    fun createForSpectator(root: JsonObject, json: Json): Map<String, Items>? {
        return root[JsonKey]?.jsonObject?.values?.flatMap { teams ->
            teams.jsonObject.mapNotNull { (playerId, playerItems) ->
                val created = playerItems.jsonObject.mapItems(json)
                if (created != null) {
                    playerId to created
                } else {
                    null
                }
            }
        }?.toMap()
    }

    fun getClientMode(root: JsonObject): ClientMode {
        return root[JsonKey]?.jsonObject?.let { items ->
            when {
                "slot0" in items -> ClientMode.Playing
                items.isNotEmpty() -> ClientMode.Spectating
                else -> ClientMode.Unknown
            }
        } ?: ClientMode.Unknown
    }

    private fun JsonObject.mapItems(json: Json): Items? {
        if (isEmpty()) {
            return null
        }

        val inventory = entries
            .filter { it.key.startsWith("slot") }
            .map { json.decodeFromJsonElement<ItemImpl>(it.value) }

        val stash = entries
            .filter { it.key.startsWith("stash") }
            .map { json.decodeFromJsonElement<ItemImpl>(it.value) }

        val teleport = entries
            .first { it.key == "teleport0" }
            .let { json.decodeFromJsonElement<ItemImpl>(it.value) }

        val neutral = entries
            .first { it.key == "neutral0" }
            .let { json.decodeFromJsonElement<ItemImpl>(it.value) }

        val neutralEnchantment = entries
            .first { it.key == "neutral1" }
            .let { json.decodeFromJsonElement<ItemImpl>(it.value) }

        val preservedNeutrals = entries
            .filter { it.key.startsWith("preserved_neutral") }
            .map { json.decodeFromJsonElement<ItemImpl>(it.value) }

        return Items(inventory, stash, teleport, neutral, neutralEnchantment, preservedNeutrals)
    }
}