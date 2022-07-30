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
import com.github.mrbean355.dota2.draft.Draft
import com.github.mrbean355.dota2.hero.SpectatedHero
import com.github.mrbean355.dota2.item.Items
import com.github.mrbean355.dota2.map.SpectatedMap
import com.github.mrbean355.dota2.player.SpectatedPlayer
import com.github.mrbean355.dota2.provider.Provider

internal data class SpectatingGameStateImpl(
    override val provider: Provider?,
    override val map: SpectatedMap?,
    override val players: Map<String, List<SpectatedPlayer>>?,
    override val heroes: Map<String, SpectatedHero>?,
    override val abilities: Map<String, List<Ability>>?,
    override val items: Map<String, Items>?,
    override val buildings: Map<String, List<Building>>?,
    override val wearables: Map<String, List<Int>>?,
    override val draft: Draft?,
) : SpectatingGameState
