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
internal data class PlayingMapImpl(
    @SerialName("name") override val name: String,
    @SerialName("matchid") override val matchId: String,
    @SerialName("game_time") override val gameTime: Long,
    @SerialName("clock_time") override val clockTime: Long,
    @SerialName("daytime") override val daytime: Boolean,
    @SerialName("nightstalker_night") override val nightStalkerNight: Boolean,
    @SerialName("game_state") override val matchState: MatchState,
    @SerialName("paused") override val paused: Boolean,
    @SerialName("win_team") override val winningTeam: String,
    @SerialName("customgamename") override val customGameName: String,
    @SerialName("ward_purchase_cooldown") override val wardPurchaseCooldown: Long,
) : PlayingMap
