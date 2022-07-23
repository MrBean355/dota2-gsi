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

package com.github.mrbean355.dota2.ability

/**
 * An ability slot that belongs to a hero.
 */
sealed interface Ability {
    val name: String
    val level: Int
    val canCast: Boolean
    val isPassive: Boolean
    val isAbilityActive: Boolean
    val cooldown: Int
    val isUltimate: Boolean
    val charges: Int?
    val maxCharges: Int?
    val chargeCooldown: Int?
}
