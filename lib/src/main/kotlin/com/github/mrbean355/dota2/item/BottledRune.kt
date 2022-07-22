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

@file:Suppress("unused")

package com.github.mrbean355.dota2.item

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class BottledRune {

    @SerialName("arcane")
    Arcane,

    @SerialName("double_damage")
    DoubleDamage,

    @SerialName("empty")
    Empty,

    @SerialName("haste")
    Haste,

    @SerialName("illusion")
    Illusion,

    @SerialName("invis")
    Invisibility,

    @SerialName("regen")
    Regeneration,

    @SerialName("water")
    Water,

}