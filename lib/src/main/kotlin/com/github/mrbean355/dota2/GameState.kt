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

data class GameState(

    @SerializedName("provider")
    val provider: Provider,

    @SerializedName("map")
    val map: DotaMap?,

    @SerializedName("player")
    val players: Players?,

    @SerializedName("hero")
    val heroes: Heroes?,

    @SerializedName("abilities")
    val abilities: Abilities?,

    @SerializedName("items")
    val items: Items?

)
