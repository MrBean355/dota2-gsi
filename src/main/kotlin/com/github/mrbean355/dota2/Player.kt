/*
 * Copyright 2021 Michael Johnston
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

package com.github.mrbean355.dota2

import com.google.gson.annotations.SerializedName

data class Player(

    @SerializedName("steamid")
    val steamId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("activity")
    val activity: String,

    @SerializedName("kills")
    val kills: Int,

    @SerializedName("deaths")
    val deaths: Int,

    @SerializedName("assists")
    val assists: Int,

    @SerializedName("last_hits")
    val lastHits: Int,

    @SerializedName("denies")
    val denies: Int,

    @SerializedName("kill_streak")
    val killStreak: Int,

    @SerializedName("commands_issued")
    val commandsIssued: Int,

    @SerializedName("kill_list")
    val killList: KillList,

    @SerializedName("team_name")
    val teamName: String,

    @SerializedName("gold")
    val gold: Int,

    @SerializedName("gold_reliable")
    val goldReliable: Int,

    @SerializedName("gold_unreliable")
    val goldUnreliable: Int,

    @SerializedName("gold_from_hero_kills")
    val goldFromHeroKills: Int,

    @SerializedName("gold_from_creep_kills")
    val goldFromCreepKills: Int,

    @SerializedName("gold_from_income")
    val goldFromIncome: Int,

    @SerializedName("gold_from_shared")
    val goldFromShared: Int,

    @SerializedName("gpm")
    val gpm: Int,

    @SerializedName("xpm")
    val xpm: Int,

    // BEGIN: spectating

    @SerializedName("net_worth")
    val netWorth: Int?,

    @SerializedName("hero_damage")
    val heroDamage: Int?,

    @SerializedName("wards_purchased")
    val wardsPurchased: Int?,

    @SerializedName("wards_placed")
    val wardsPlaced: Int?,

    @SerializedName("wards_destroyed")
    val wardsDestroyed: Int?,

    @SerializedName("runes_activated")
    val runesActivated: Int?,

    @SerializedName("camps_stacked")
    val campsStacked: Int?,

    @SerializedName("support_gold_spent")
    val supportGoldSpent: Int?,

    @SerializedName("consumable_gold_spent")
    val consumableGoldSpent: Int?,

    @SerializedName("item_gold_spent")
    val itemGoldSpent: Int?,

    @SerializedName("gold_lost_to_death")
    val goldLostToDeath: Int?,

    @SerializedName("gold_spent_on_buybacks")
    val goldSpentOnBuybacks: Int?

)

class KillList internal constructor(c: Collection<Int>) : ArrayList<Int>(c)

class Players internal constructor(c: Map<String, Player>) : HashMap<String, Player>(c)
