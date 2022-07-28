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

import com.github.mrbean355.dota2.json.ClientMode
import com.github.mrbean355.dota2.json.transform.SpectatedPlayerTransformer
import com.github.mrbean355.dota2.player.Player
import com.github.mrbean355.dota2.player.PlayerImpl
import com.github.mrbean355.dota2.player.SpectatedPlayer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

private const val JsonKey = "player"

internal object PlayerFactory {

    fun createForPlayer(root: JsonObject): Player? {
        return root[JsonKey]?.jsonObject?.let {
            Json.decodeFromJsonElement<PlayerImpl>(it)
        }
    }

    fun createForSpectator(root: JsonObject): Map<String, List<SpectatedPlayer>>? {
        return root[JsonKey]?.jsonObject?.mapValues { (_, players) ->
            Json.decodeFromJsonElement(SpectatedPlayerTransformer, players)
        }
    }

    fun getClientMode(root: JsonObject): ClientMode {
        return root[JsonKey]?.jsonObject?.let { player ->
            when {
                "steamid" in player -> ClientMode.Playing
                player.isNotEmpty() -> ClientMode.Spectating
                else -> ClientMode.Unknown
            }
        } ?: ClientMode.Unknown
    }
}