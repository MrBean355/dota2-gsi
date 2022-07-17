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

sealed interface Hero {
    val xPos: Long
    val yPos: Long
    val id: Long
    val name: String
    val level: Long
    val xp: Long
    val alive: Boolean
    val respawnSeconds: Long
    val buybackCost: Long
    val buybackCooldown: Long
    val health: Long
    val maxHealth: Long
    val healthPercent: Long
    val mana: Long
    val maxMana: Long
    val manaPercent: Long
    val silenced: Boolean
    val stunned: Boolean
    val disarmed: Boolean
    val magicImmune: Boolean
    val hexed: Boolean
    val muted: Boolean
    val `break`: Boolean
    val aghanimsScepter: Boolean
    val aghanimsShard: Boolean
    val smoked: Boolean
    val hasDebuff: Boolean
    val talent1: Boolean
    val talent2: Boolean
    val talent3: Boolean
    val talent4: Boolean
    val talent5: Boolean
    val talent6: Boolean
    val talent7: Boolean
    val talent8: Boolean
}
