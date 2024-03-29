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

import com.github.mrbean355.dota2.building.Building
import com.github.mrbean355.dota2.json.transform.BuildingsTransformer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject

private const val JsonKey = "buildings"

internal object BuildingsFactory {

    fun createForPlayer(root: JsonObject, json: Json): List<Building>? {
        val teamBuildings = createForSpectator(root, json) ?: return null
        val team = teamBuildings.keys.firstOrNull() ?: return emptyList()
        return teamBuildings[team]
    }

    fun createForSpectator(root: JsonObject, json: Json): Map<String, List<Building>>? {
        return root[JsonKey]?.jsonObject?.mapValues { (_, buildings) ->
            json.decodeFromJsonElement(BuildingsTransformer, buildings)
        }
    }
}