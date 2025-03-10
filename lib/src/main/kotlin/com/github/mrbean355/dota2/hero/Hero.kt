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

package com.github.mrbean355.dota2.hero

import com.github.mrbean355.dota2.hero.talent.TalentTree

/**
 * A hero that is being controlled by a player in the match.
 */
interface Hero {
    val facet: Int
    val xPos: Int
    val yPos: Int
    val id: Int
    val name: String
    val level: Int
    val xp: Int
    val isAlive: Boolean
    val respawnTimer: Int
    val buybackCost: Int
    val buybackCooldown: Int
    val health: Int
    val maxHealth: Int
    val healthPercent: Int
    val mana: Int
    val maxMana: Int
    val manaPercent: Int
    val isSilenced: Boolean
    val isStunned: Boolean
    val isDisarmed: Boolean
    val isMagicImmune: Boolean
    val isHexed: Boolean
    val isMuted: Boolean
    val isBroken: Boolean
    val hasAghanimsScepter: Boolean
    val hasAghanimsShard: Boolean
    val isSmoked: Boolean
    val hasDebuff: Boolean
    val talentTree: TalentTree
    val attributesLevel: Int
}
