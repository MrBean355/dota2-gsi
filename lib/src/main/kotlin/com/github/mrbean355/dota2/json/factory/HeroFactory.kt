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

import com.github.mrbean355.dota2.hero.Hero
import com.github.mrbean355.dota2.hero.SpectatedHero
import com.github.mrbean355.dota2.json.ClientMode
import com.github.mrbean355.dota2.json.transform.HeroImplTransformer
import com.github.mrbean355.dota2.json.transform.SpectatedHeroImplTransformer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject

private const val JsonKey = "hero"

/**
 * If the hero object is missing this property, it means it isn't spawned into the game yet.
 * In such cases, a null Hero object is returned, instead of an incomplete object.
 */
private const val ValidityIndicator = "xpos"

internal object HeroFactory {

    fun createForPlayer(root: JsonObject, json: Json): Hero? {
        return root[JsonKey]?.jsonObject?.takeIf { ValidityIndicator in it }?.let {
            json.decodeFromJsonElement(HeroImplTransformer, it)
        }
    }

    fun createForSpectator(root: JsonObject, json: Json): Map<String, SpectatedHero>? {
        return root[JsonKey]?.jsonObject?.values?.flatMap { teams ->
            teams.jsonObject
                .filterValues { ValidityIndicator in it.jsonObject }
                .map { (playerId, playerHero) ->
                    playerId to json.decodeFromJsonElement(SpectatedHeroImplTransformer, playerHero)
                }
        }?.toMap()
    }

    fun getClientMode(root: JsonObject): ClientMode {
        return root[JsonKey]?.jsonObject?.let { hero ->
            when {
                "id" in hero -> ClientMode.Playing
                hero.isNotEmpty() -> ClientMode.Spectating
                else -> ClientMode.Unknown
            }
        } ?: ClientMode.Unknown
    }
}