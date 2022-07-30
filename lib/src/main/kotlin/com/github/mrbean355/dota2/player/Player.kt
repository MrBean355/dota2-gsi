/*
 * Copyright 2022 Michael Johnston
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

package com.github.mrbean355.dota2.player

import com.github.mrbean355.dota2.map.Team

/**
 * A player involved in the match.
 */
sealed interface Player {
    val steamId: String
    val name: String
    val activity: String
    val kills: Int
    val deaths: Int
    val assists: Int
    val lastHits: Int
    val denies: Int
    val killStreak: Int
    val commandsIssued: Int
    val killList: Map<String, Int>?
    val team: Team
    val gold: Int
    val goldReliable: Int
    val goldUnreliable: Int
    val goldFromHeroKills: Int
    val goldFromCreepKills: Int
    val goldFromIncome: Int
    val goldFromShared: Int
    val gpm: Int
    val xpm: Int
}
