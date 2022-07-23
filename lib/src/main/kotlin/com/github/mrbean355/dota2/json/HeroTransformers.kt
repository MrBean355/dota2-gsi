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

import com.github.mrbean355.dota2.hero.Hero
import com.github.mrbean355.dota2.hero.HeroImpl
import com.github.mrbean355.dota2.hero.SpectatedHeroImpl
import com.github.mrbean355.dota2.hero.talent.TalentTree
import com.github.mrbean355.dota2.hero.talent.TalentTreeChoice
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.JsonTransformingSerializer
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

internal object HeroImplTransformer : JsonTransformingSerializer<HeroImpl>(HeroImpl.serializer()) {

    override fun transformDeserialize(element: JsonElement) = transform(element)

}

internal object SpectatedHeroImplTransformer : JsonTransformingSerializer<SpectatedHeroImpl>(SpectatedHeroImpl.serializer()) {

    override fun transformDeserialize(element: JsonElement) = transform(element)

}

private const val Prefix = "talent_"
private const val StartIndex = 1
private const val EndIndex = 8

/**
 * Combines the individual "talent_*" properties into a structured object.
 */
private fun transform(element: JsonElement): JsonElement {
    val root = element.jsonObject
    val talents = buildList {
        for (i in StartIndex..EndIndex) {
            add(root.getValue("$Prefix$i").jsonPrimitive.boolean)
        }
    }

    val levels = talents.windowed(size = 2, step = 2).map {
        buildJsonObject {
            put(TalentTreeChoice::hasLeft.name, JsonPrimitive(it[1]))
            put(TalentTreeChoice::hasRight.name, JsonPrimitive(it[0]))
        }
    }

    val talentTree = buildJsonObject {
        put(TalentTree::level10.name, levels[0])
        put(TalentTree::level15.name, levels[1])
        put(TalentTree::level20.name, levels[2])
        put(TalentTree::level25.name, levels[3])
    }

    val transformed = element.jsonObject
        .filterNot { it.key.startsWith(Prefix) }
        .plus(Hero::talentTree.name to talentTree)

    return JsonObject(transformed)
}