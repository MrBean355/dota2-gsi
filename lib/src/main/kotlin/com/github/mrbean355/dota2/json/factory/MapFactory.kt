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

import com.github.mrbean355.dota2.json.ClientMode
import com.github.mrbean355.dota2.map.PlayingMap
import com.github.mrbean355.dota2.map.PlayingMapImpl
import com.github.mrbean355.dota2.map.SpectatedMap
import com.github.mrbean355.dota2.map.SpectatedMapImpl
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

private const val JsonKey = "map"

internal object MapFactory {

    fun createForPlayer(root: JsonObject, json: Json): PlayingMap? {
        return root[JsonKey]?.jsonObject?.let {
            json.decodeFromJsonElement<PlayingMapImpl>(it)
        }
    }

    fun createForSpectator(root: JsonObject, json: Json): SpectatedMap? {
        return root[JsonKey]?.jsonObject?.let {
            json.decodeFromJsonElement<SpectatedMapImpl>(it)
        }
    }

    fun getClientMode(root: JsonObject): ClientMode {
        return root[JsonKey]?.jsonObject?.let { map ->
            when {
                "ward_purchase_cooldown" in map -> ClientMode.Playing
                "radiant_ward_purchase_cooldown" in map -> ClientMode.Spectating
                else -> ClientMode.Unknown
            }
        } ?: ClientMode.Unknown
    }
}