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

import com.github.mrbean355.dota2.GameState
import com.github.mrbean355.dota2.PlayingGameState
import com.github.mrbean355.dota2.SpectatingGameState
import com.github.mrbean355.dota2.ability.Ability
import com.github.mrbean355.dota2.ability.AbilityImpl
import com.github.mrbean355.dota2.hero.Hero
import com.github.mrbean355.dota2.hero.HeroImpl
import com.github.mrbean355.dota2.hero.SpectatedHero
import com.github.mrbean355.dota2.hero.SpectatedHeroImpl
import com.github.mrbean355.dota2.item.ItemImpl
import com.github.mrbean355.dota2.item.Items
import com.github.mrbean355.dota2.map.PlayingMap
import com.github.mrbean355.dota2.map.PlayingMapImpl
import com.github.mrbean355.dota2.map.SpectatingMap
import com.github.mrbean355.dota2.map.SpectatingMapImpl
import com.github.mrbean355.dota2.player.Player
import com.github.mrbean355.dota2.player.PlayerImpl
import com.github.mrbean355.dota2.player.SpectatedPlayer
import com.github.mrbean355.dota2.player.SpectatedPlayerImpl
import com.github.mrbean355.dota2.provider.ProviderImpl
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

private const val ProviderKey = "provider"
private const val MapKey = "map"
private const val MapIdentifier = "ward_purchase_cooldown"
private const val PlayerKey = "player"
private const val PlayerIdentifier = "steamid"
private const val HeroKey = "hero"
private const val HeroIdentifier = "id"
private const val AbilitiesKey = "abilities"
private const val AbilitiesIdentifier = "ability0"
private const val ItemsKey = "items"
private const val ItemsIdentifier = "slot0"

internal fun parseGameState(text: String): GameState {
    val root = Json.parseToJsonElement(text).jsonObject
    var isPlaying = false

    val provider = root[ProviderKey]?.jsonObject?.let {
        Json.decodeFromJsonElement<ProviderImpl>(it)
    }

    val map = root[MapKey]?.jsonObject?.let { map ->
        isPlaying = map.containsKey(MapIdentifier)
        if (isPlaying) {
            Json.decodeFromJsonElement<PlayingMapImpl>(map)
        } else {
            Json.decodeFromJsonElement<SpectatingMapImpl>(map)
        }
    }

    val player = root[PlayerKey]?.jsonObject?.let { player ->
        isPlaying = player.containsKey(PlayerIdentifier)
        if (isPlaying) {
            Json.decodeFromJsonElement<PlayerImpl>(player)
        } else {
            player.values.flatMap { teams ->
                teams.jsonObject.map { player ->
                    player.key to Json.decodeFromJsonElement<SpectatedPlayerImpl>(player.value)
                }
            }.toMap()
        }
    }

    val hero = root[HeroKey]?.jsonObject?.let { hero ->
        isPlaying = hero.containsKey(HeroIdentifier)
        if (isPlaying) {
            if (hero.getValue("id").jsonPrimitive.int != -1) {
                Json.decodeFromJsonElement<HeroImpl>(hero)
            } else {
                null
            }
        } else {
            hero.values.flatMap { teams ->
                teams.jsonObject.map { playerHero ->
                    playerHero.key to if (playerHero.value.jsonObject.getValue("id").jsonPrimitive.int != -1) {
                        Json.decodeFromJsonElement<SpectatedHeroImpl>(playerHero.value)
                    } else {
                        null
                    }
                }
            }.toMap()
        }
    }

    val abilities = root[AbilitiesKey]?.jsonObject?.let { abilities ->
        isPlaying = abilities.containsKey(AbilitiesIdentifier)
        if (isPlaying) {
            abilities.values.map {
                Json.decodeFromJsonElement<AbilityImpl>(it)
            }
        } else {
            abilities.values.flatMap { teams ->
                teams.jsonObject.map { playerAbilities ->
                    playerAbilities.key to playerAbilities.value.jsonObject.values.map {
                        Json.decodeFromJsonElement<AbilityImpl>(it)
                    }
                }
            }.toMap()
        }
    }

    val items = root[ItemsKey]?.jsonObject?.let { items ->
        isPlaying = items.containsKey(ItemsIdentifier)
        if (isPlaying) {
            items.mapItems()
        } else {
            items.values.flatMap {
                it.jsonObject.map { playerItems ->
                    playerItems.key to playerItems.value.jsonObject.mapItems()
                }
            }.toMap()
        }
    }

    @Suppress("UNCHECKED_CAST")
    return if (isPlaying) {
        PlayingGameState(
            provider,
            map as PlayingMap?,
            player as Player?,
            hero as Hero?,
            abilities as List<Ability>?,
            items as Items?
        )
    } else {
        SpectatingGameState(
            provider,
            map as SpectatingMap?,
            player as Map<String, SpectatedPlayer>?,
            hero as Map<String, SpectatedHero>?,
            abilities as Map<String, List<Ability>>?,
            items as Map<String, Items>?
        )
    }
}

private const val SlotKeyPrefix = "slot"
private const val StashKeyPrefix = "stash"
private const val TeleportKey = "teleport0"
private const val NeutralKey = "neutral0"

private fun JsonObject.mapItems(): Items {
    val inventory = entries
        .filter { it.key.startsWith(SlotKeyPrefix) }
        .map { Json.decodeFromJsonElement<ItemImpl>(it.value) }

    val stash = entries
        .filter { it.key.startsWith(StashKeyPrefix) }
        .map { Json.decodeFromJsonElement<ItemImpl>(it.value) }

    val teleport = entries
        .first { it.key == TeleportKey }
        .let { Json.decodeFromJsonElement<ItemImpl>(it.value) }

    val neutral = entries
        .first { it.key == NeutralKey }
        .let { Json.decodeFromJsonElement<ItemImpl>(it.value) }

    return Items(inventory, stash, teleport, neutral)
}