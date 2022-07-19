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

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AbilityImpl(
    @SerialName("name") override val name: String,
    @SerialName("level") override val level: Int,
    @SerialName("can_cast") override val canCast: Boolean,
    @SerialName("passive") override val passive: Boolean,
    @SerialName("ability_active") override val abilityActive: Boolean,
    @SerialName("cooldown") override val cooldown: Int,
    @SerialName("ultimate") override val ultimate: Boolean,
    @SerialName("charges") override val charges: Int? = null,
    @SerialName("max_charges") override val maxCharges: Int? = null,
    @SerialName("charge_cooldown") override val chargeCooldown: Int? = null,
) : Ability
