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

import com.github.mrbean355.dota2.hero.Hero
import com.github.mrbean355.dota2.hero.HeroImpl
import com.github.mrbean355.dota2.hero.SpectatedHero
import com.github.mrbean355.dota2.hero.SpectatedHeroImpl
import com.github.mrbean355.dota2.json.ClientMode
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

private const val JsonKey = "hero"

internal object HeroFactory {

    fun createForPlayer(root: JsonObject): Hero? {
        return root[JsonKey]?.jsonObject?.let {
            maybeCreateHero<HeroImpl>(it)
        }
    }

    fun createForSpectator(root: JsonObject): Map<String, SpectatedHero>? {
        return root[JsonKey]?.jsonObject?.values?.flatMap { teams ->
            teams.jsonObject.mapNotNull { (playerId, playerHero) ->
                val created = maybeCreateHero<SpectatedHeroImpl>(playerHero.jsonObject)
                if (created != null) {
                    playerId to created
                } else {
                    null
                }
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

    private inline fun <reified H : Hero> maybeCreateHero(source: JsonObject): H? {
        return if ("xpos" in source) {
            Json.decodeFromJsonElement<H>(source)
        } else {
            // Hero is not spawned into the game yet, so most of its properties will be missing anyway.
            null
        }
    }
}