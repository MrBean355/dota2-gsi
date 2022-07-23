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

package com.github.mrbean355.dota2.map

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SpectatingMapImpl(
    @SerialName("name") override val name: String,
    @SerialName("matchid") override val matchId: String,
    @SerialName("game_time") override val gameTime: Int,
    @SerialName("clock_time") override val clockTime: Int,
    @SerialName("daytime") override val isDaytime: Boolean,
    @SerialName("nightstalker_night") override val isNightStalkerNight: Boolean,
    @SerialName("game_state") override val matchState: MatchState,
    @SerialName("paused") override val isPaused: Boolean,
    @SerialName("win_team") override val winningTeam: String,
    @SerialName("customgamename") override val customGameName: String,
    @SerialName("radiant_ward_purchase_cooldown") override val radiantWardPurchaseCooldown: Int,
    @SerialName("dire_ward_purchase_cooldown") override val direWardPurchaseCooldown: Int,
    @SerialName("roshan_state") override val roshanState: String,
    @SerialName("roshan_state_end_seconds") override val roshanStateEndTimer: Int,
    @SerialName("radiant_win_chance") override val radiantWinChance: Int,
) : SpectatingMap
