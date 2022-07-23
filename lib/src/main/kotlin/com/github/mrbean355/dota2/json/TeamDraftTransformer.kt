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

package com.github.mrbean355.dota2.json

import com.github.mrbean355.dota2.draft.DraftHero
import com.github.mrbean355.dota2.draft.TeamDraft
import com.github.mrbean355.dota2.draft.TeamDraftImpl
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonTransformingSerializer
import kotlinx.serialization.json.addJsonObject
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.jsonObject

/**
 * Merges multiple pick and ban fields:
 * ```
 * {
 *   "pick0_id": 19,
 *   "pick0_class": "tiny",
 *   "pick1_id": 0,
 *   "pick1_class": "",
 *   "ban0_id": 135,
 *   "ban0_class": "dawnbreaker",
 *   "ban1_id": 101,
 *   "ban1_class": "skywrath_mage"
 * }
 * ```
 * Into arrays:
 * ```
 * {
 *   "picks": [
 *     { "id": 19, "name": "tiny" },
 *     { "id": 0, "name": "" }
 *   ],
 *   "bans": [
 *     { "id": 135, "name": "dawnbreaker" },
 *     { "id": 101, "name": "skywrath_mage" }
 *   ]
 * }
 * ```
 */
internal object TeamDraftTransformer : JsonTransformingSerializer<TeamDraftImpl>(TeamDraftImpl.serializer()) {

    private const val PickPrefix = "pick"
    private const val BanPrefix = "ban"

    override fun transformDeserialize(element: JsonElement): JsonElement {
        val root = element.jsonObject
        val picks = root.extractArray(PickPrefix)
        val bans = root.extractArray(BanPrefix)

        val transformed = root
            .filterNot { it.key.startsWith(PickPrefix) || it.key.startsWith(BanPrefix) }
            .plus(TeamDraft::picks.name to picks)
            .plus(TeamDraft::bans.name to bans)

        return JsonObject(transformed)
    }

    private fun JsonObject.extractArray(kind: String): JsonArray {
        val regex = """$kind(\d+)_id""".toRegex()
        val clazz = "$kind%s_class"

        return buildJsonArray {
            forEach { (key, value) ->
                regex.matchEntire(key)?.let { match ->
                    addJsonObject {
                        put(DraftHero::id.name, value)
                        put(DraftHero::name.name, getValue(clazz.format(match.groupValues[1])))
                    }
                }
            }
        }
    }
}