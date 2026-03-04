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

package com.github.mrbean355.dota2.map

import com.github.mrbean355.dota2.map.watcher.WatcherImpl
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SpectatedMapImpl(
    @SerialName("name") override val name: String,
    @SerialName("matchid") override val matchId: String,
    @SerialName("game_time") override val gameTime: Int,
    @SerialName("clock_time") override val clockTime: Int,
    @SerialName("daytime") override val isDaytime: Boolean,
    @SerialName("nightstalker_night") override val isNightStalkerNight: Boolean,
    @SerialName("radiant_score") override val radiantScore: Int,
    @SerialName("dire_score") override val direScore: Int,
    @SerialName("game_state") override val matchState: MatchState,
    @SerialName("paused") override val isPaused: Boolean,
    @SerialName("win_team") override val winningTeam: Team,
    @SerialName("customgamename") override val customGameName: String,
    @SerialName("radiant_ward_purchase_cooldown") override val radiantWardPurchaseCooldown: Int,
    @SerialName("dire_ward_purchase_cooldown") override val direWardPurchaseCooldown: Int,
    @SerialName("roshan_state") override val roshanState: String,
    @SerialName("roshan_state_end_seconds") override val roshanStateEndTimer: Int,
    @SerialName("tormentor_state") override val tormentorState: String,
    @SerialName("tormentor_state_end_seconds") override val tormentorStateEndSeconds: Int,
    @SerialName("tormentor_state_location") override val tormentorStateLocation: String,
    @SerialName("radiant_glyph_cooldown") override val radiantGlyphCooldown: Double,
    @SerialName("dire_glyph_cooldown") override val direGlyphCooldown: Double,
    @SerialName("radiant_scan_cooldown") override val radiantScanCooldown: Double,
    @SerialName("radiant_scan_charges") override val radiantScanCharges: Int,
    @SerialName("dire_scan_cooldown") override val direScanCooldown: Double,
    @SerialName("dire_scan_charges") override val direScanCharges: Int,
    @SerialName("radiant_wisdom_shrine") override val radiantWisdomShrine: Boolean,
    @SerialName("dire_wisdom_shrine") override val direWisdomShrine: Boolean,
    @SerialName("radiant_lotus_pool_count") override val radiantLotusPoolCount: Int,
    @SerialName("dire_lotus_pool_count") override val direLotusPoolCount: Int,
    @SerialName("watchers") override val watchers: Map<String, WatcherImpl>,
    @SerialName("radiant_win_chance") override val radiantWinChance: Int? = null,
) : SpectatedMap
