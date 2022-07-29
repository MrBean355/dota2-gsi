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

package com.github.mrbean355.dota2.player

import com.github.mrbean355.dota2.map.Team
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SpectatedPlayerImpl(
    @SerialName("steamid") override val steamId: String,
    @SerialName("name") override val name: String,
    @SerialName("activity") override val activity: String,
    @SerialName("kills") override val kills: Int,
    @SerialName("deaths") override val deaths: Int,
    @SerialName("assists") override val assists: Int,
    @SerialName("last_hits") override val lastHits: Int,
    @SerialName("denies") override val denies: Int,
    @SerialName("kill_streak") override val killStreak: Int,
    @SerialName("commands_issued") override val commandsIssued: Int,
    @SerialName("kill_list") override val killList: Map<String, Int>? = null,
    @SerialName("team_name") override val team: Team,
    @SerialName("gold") override val gold: Int,
    @SerialName("gold_reliable") override val goldReliable: Int,
    @SerialName("gold_unreliable") override val goldUnreliable: Int,
    @SerialName("gold_from_hero_kills") override val goldFromHeroKills: Int,
    @SerialName("gold_from_creep_kills") override val goldFromCreepKills: Int,
    @SerialName("gold_from_income") override val goldFromIncome: Int,
    @SerialName("gold_from_shared") override val goldFromShared: Int,
    @SerialName("gpm") override val gpm: Int,
    @SerialName("xpm") override val xpm: Int,
    @SerialName("id") override val id: String,
    @SerialName("net_worth") override val netWorth: Int,
    @SerialName("hero_damage") override val heroDamage: Int,
    @SerialName("wards_purchased") override val wardsPurchased: Int,
    @SerialName("wards_placed") override val wardsPlaced: Int,
    @SerialName("wards_destroyed") override val wardsDestroyed: Int,
    @SerialName("runes_activated") override val runesActivated: Int,
    @SerialName("camps_stacked") override val campsStacked: Int,
    @SerialName("support_gold_spent") override val supportGoldSpent: Int,
    @SerialName("consumable_gold_spent") override val consumableGoldSpent: Int,
    @SerialName("item_gold_spent") override val itemGoldSpent: Int,
    @SerialName("gold_lost_to_death") override val goldLostToDeath: Int,
    @SerialName("gold_spent_on_buybacks") override val goldSpentOnBuybacks: Int,
) : SpectatedPlayer
