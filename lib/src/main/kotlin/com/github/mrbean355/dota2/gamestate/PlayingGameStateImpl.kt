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

package com.github.mrbean355.dota2.gamestate

import com.github.mrbean355.dota2.ability.Ability
import com.github.mrbean355.dota2.building.Building
import com.github.mrbean355.dota2.hero.Hero
import com.github.mrbean355.dota2.item.Items
import com.github.mrbean355.dota2.map.PlayingMap
import com.github.mrbean355.dota2.player.Player
import com.github.mrbean355.dota2.provider.Provider

internal data class PlayingGameStateImpl(
    override val provider: Provider?,
    override val map: PlayingMap?,
    override val player: Player?,
    override val hero: Hero?,
    override val abilities: List<Ability>?,
    override val items: Items?,
    override val buildings: List<Building>?,
    override val wearables: List<Int>?,
) : PlayingGameState
