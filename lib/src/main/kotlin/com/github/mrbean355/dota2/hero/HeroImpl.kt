/*
 * Copyright 2022 Michael Johnston
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

package com.github.mrbean355.dota2.hero

import com.github.mrbean355.dota2.hero.talent.TalentTreeImpl
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class HeroImpl(
    @SerialName("xpos") override val xPos: Int,
    @SerialName("ypos") override val yPos: Int,
    @SerialName("id") override val id: Int,
    @SerialName("name") override val name: String,
    @SerialName("level") override val level: Int,
    @SerialName("xp") override val xp: Int,
    @SerialName("alive") override val isAlive: Boolean,
    @SerialName("respawn_seconds") override val respawnTimer: Int,
    @SerialName("buyback_cost") override val buybackCost: Int,
    @SerialName("buyback_cooldown") override val buybackCooldown: Int,
    @SerialName("health") override val health: Int,
    @SerialName("max_health") override val maxHealth: Int,
    @SerialName("health_percent") override val healthPercent: Int,
    @SerialName("mana") override val mana: Int,
    @SerialName("max_mana") override val maxMana: Int,
    @SerialName("mana_percent") override val manaPercent: Int,
    @SerialName("silenced") override val isSilenced: Boolean,
    @SerialName("stunned") override val isStunned: Boolean,
    @SerialName("disarmed") override val isDisarmed: Boolean,
    @SerialName("magicimmune") override val isMagicImmune: Boolean,
    @SerialName("hexed") override val isHexed: Boolean,
    @SerialName("muted") override val isMuted: Boolean,
    @SerialName("break") override val isBroken: Boolean,
    @SerialName("aghanims_scepter") override val hasAghanimsScepter: Boolean,
    @SerialName("aghanims_shard") override val hasAghanimsShard: Boolean,
    @SerialName("smoked") override val isSmoked: Boolean,
    @SerialName("has_debuff") override val hasDebuff: Boolean,
    @SerialName("talentTree") override val talentTree: TalentTreeImpl,
    @SerialName("attributes_level") override val attributesLevel: Int,
) : Hero
