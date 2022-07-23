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

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

private const val JsonKey = "wearables"

internal object WearablesFactory {

    fun createForPlayer(root: JsonObject): List<Int>? {
        return root[JsonKey]?.jsonObject?.let { obj ->
            obj.values.map {
                it.jsonPrimitive.int
            }
        }
    }

    fun createForSpectator(root: JsonObject): Map<String, List<Int>>? {
        return root[JsonKey]?.jsonObject?.values?.flatMap { teams ->
            teams.jsonObject.map { (playerId, playerAbilities) ->
                playerId to playerAbilities.jsonObject.values.map {
                    it.jsonPrimitive.int
                }
            }
        }?.toMap()
    }
}