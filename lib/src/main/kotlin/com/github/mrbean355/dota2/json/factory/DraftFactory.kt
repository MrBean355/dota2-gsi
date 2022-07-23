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

import com.github.mrbean355.dota2.draft.Draft
import com.github.mrbean355.dota2.draft.DraftImpl
import com.github.mrbean355.dota2.json.ClientMode
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

private const val JsonKey = "draft"

internal object DraftFactory {

    fun createForSpectator(root: JsonObject): Draft? {
        val draft = root[JsonKey]?.jsonObject ?: return null
        return if (draft.isNotEmpty()) {
            Json.decodeFromJsonElement<DraftImpl>(draft)
        } else {
            return null
        }
    }

    fun getClientMode(root: JsonObject): ClientMode {
        return root[JsonKey]?.jsonObject?.let { draft ->
            if (draft.isNotEmpty()) {
                ClientMode.Spectating
            } else {
                ClientMode.Unknown
            }
        } ?: ClientMode.Unknown
    }
}