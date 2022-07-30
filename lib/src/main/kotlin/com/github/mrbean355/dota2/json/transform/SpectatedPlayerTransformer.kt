/*
 * Copyright 2022 Michael Johnston
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

package com.github.mrbean355.dota2.json.transform

import com.github.mrbean355.dota2.player.SpectatedPlayer
import com.github.mrbean355.dota2.player.SpectatedPlayerImpl
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.JsonTransformingSerializer
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.jsonObject

internal object SpectatedPlayerTransformer : JsonTransformingSerializer<List<SpectatedPlayerImpl>>(ListSerializer(SpectatedPlayerImpl.serializer())) {

    override fun transformDeserialize(element: JsonElement): JsonElement {
        return buildJsonArray {
            element.jsonObject.forEach { (playerId, data) ->
                add(
                    JsonObject(data.jsonObject.plus(SpectatedPlayer::id.name to JsonPrimitive(playerId)))
                )
            }
        }
    }
}