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

package com.github.mrbean355.dota2.item

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ItemImpl(
    @SerialName("name") override val name: String,
    @SerialName("purchaser") override val purchaser: Int? = null,
    @SerialName("contains_rune") override val containsRune: String? = null,
    @SerialName("can_cast") override val canCast: Boolean? = null,
    @SerialName("cooldown") override val cooldown: Int? = null,
    @SerialName("passive") override val passive: Boolean? = null,
    @SerialName("charges") override val charges: Int? = null,
) : Item
