/*
 * Copyright 2022 Michael Johnston
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
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

    fun createForPlayer(root: JsonObject): Items? {
        return root[JsonKey]?.jsonObject?.mapItems()
    }

    fun createForSpectator(root: JsonObject): Map<String, Items>? {
        return root[JsonKey]?.jsonObject?.values?.flatMap {
            it.jsonObject.map { playerItems ->
                playerItems.key to playerItems.value.jsonObject.mapItems()
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

    private fun JsonObject.mapItems(): Items {
        val inventory = entries
            .filter { it.key.startsWith("slot") }
            .map { Json.decodeFromJsonElement<ItemImpl>(it.value) }

        val stash = entries
            .filter { it.key.startsWith("stash") }
            .map { Json.decodeFromJsonElement<ItemImpl>(it.value) }

        val teleport = entries
            .first { it.key == "teleport0" }
            .let { Json.decodeFromJsonElement<ItemImpl>(it.value) }

        val neutral = entries
            .first { it.key == "neutral0" }
            .let { Json.decodeFromJsonElement<ItemImpl>(it.value) }

        return Items(inventory, stash, teleport, neutral)
    }
}