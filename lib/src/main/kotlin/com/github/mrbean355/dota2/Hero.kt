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

data class Hero(

    @SerializedName("xpos")
    val xPos: Long,

    @SerializedName("ypos")
    val yPos: Long,

    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("level")
    val level: Long,

    @SerializedName("xp")
    val xp: Long,

    @SerializedName("alive")
    val alive: Boolean,

    @SerializedName("respawn_seconds")
    val respawnSeconds: Long,

    @SerializedName("buyback_cost")
    val buybackCost: Long,

    @SerializedName("buyback_cooldown")
    val buybackCooldown: Long,

    @SerializedName("health")
    val health: Long,

    @SerializedName("max_health")
    val maxHealth: Long,

    @SerializedName("health_percent")
    val healthPercent: Long,

    @SerializedName("mana")
    val mana: Long,

    @SerializedName("max_mana")
    val maxMana: Long,

    @SerializedName("mana_percent")
    val manaPercent: Long,

    @SerializedName("silenced")
    val silenced: Boolean,

    @SerializedName("stunned")
    val stunned: Boolean,

    @SerializedName("disarmed")
    val disarmed: Boolean,

    @SerializedName("magicimmune")
    val magicImmune: Boolean,

    @SerializedName("hexed")
    val hexed: Boolean,

    @SerializedName("muted")
    val muted: Boolean,

    @SerializedName("break")
    val `break`: Boolean,

    @SerializedName("aghanims_scepter")
    val aghanimsScepter: Boolean,

    @SerializedName("aghanims_shard")
    val aghanimsShard: Boolean,

    @SerializedName("smoked")
    val smoked: Boolean,

    @SerializedName("has_debuff")
    val hasDebuff: Boolean,

    @SerializedName("talents")
    val talents: List<Boolean>,

    // BEGIN: spectating

    @SerializedName("selected_unit")
    val selectedUnit: Boolean?

)

class Heroes internal constructor(c: Map<String, Hero>) : HashMap<String, Hero>(c)