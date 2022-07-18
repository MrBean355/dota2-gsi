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

package com.github.mrbean355.dota2.hero

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SpectatedHeroImpl(
    @SerialName("xpos") override val xPos: Int,
    @SerialName("ypos") override val yPos: Int,
    @SerialName("id") override val id: Int,
    @SerialName("name") override val name: String,
    @SerialName("level") override val level: Int,
    @SerialName("xp") override val xp: Int,
    @SerialName("alive") override val alive: Boolean,
    @SerialName("respawn_seconds") override val respawnSeconds: Int,
    @SerialName("buyback_cost") override val buybackCost: Int,
    @SerialName("buyback_cooldown") override val buybackCooldown: Int,
    @SerialName("health") override val health: Int,
    @SerialName("max_health") override val maxHealth: Int,
    @SerialName("health_percent") override val healthPercent: Int,
    @SerialName("mana") override val mana: Int,
    @SerialName("max_mana") override val maxMana: Int,
    @SerialName("mana_percent") override val manaPercent: Int,
    @SerialName("silenced") override val silenced: Boolean,
    @SerialName("stunned") override val stunned: Boolean,
    @SerialName("disarmed") override val disarmed: Boolean,
    @SerialName("magicimmune") override val magicImmune: Boolean,
    @SerialName("hexed") override val hexed: Boolean,
    @SerialName("muted") override val muted: Boolean,
    @SerialName("break") override val `break`: Boolean,
    @SerialName("aghanims_scepter") override val aghanimsScepter: Boolean,
    @SerialName("aghanims_shard") override val aghanimsShard: Boolean,
    @SerialName("smoked") override val smoked: Boolean,
    @SerialName("has_debuff") override val hasDebuff: Boolean,
    @SerialName("talent_1") override val talent1: Boolean,
    @SerialName("talent_2") override val talent2: Boolean,
    @SerialName("talent_3") override val talent3: Boolean,
    @SerialName("talent_4") override val talent4: Boolean,
    @SerialName("talent_5") override val talent5: Boolean,
    @SerialName("talent_6") override val talent6: Boolean,
    @SerialName("talent_7") override val talent7: Boolean,
    @SerialName("talent_8") override val talent8: Boolean,
    @SerialName("selected_unit") override val selectedUnit: Boolean,
) : SpectatedHero
