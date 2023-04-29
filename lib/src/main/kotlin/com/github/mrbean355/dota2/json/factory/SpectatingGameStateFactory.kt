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

package com.github.mrbean355.dota2.json.factory

import com.github.mrbean355.dota2.gamestate.SpectatingGameState
import com.github.mrbean355.dota2.gamestate.SpectatingGameStateImpl
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

internal object SpectatingGameStateFactory {

    fun create(root: JsonObject, json: Json): SpectatingGameState {
        return SpectatingGameStateImpl(
            provider = ProviderFactory.create(root, json),
            map = MapFactory.createForSpectator(root, json),
            players = PlayerFactory.createForSpectator(root, json),
            heroes = HeroFactory.createForSpectator(root, json),
            abilities = AbilitiesFactory.createForSpectator(root, json),
            items = ItemsFactory.createForSpectator(root, json),
            buildings = BuildingsFactory.createForSpectator(root, json),
            wearables = WearablesFactory.createForSpectator(root),
            events = EventsFactory.create(root),
            draft = DraftFactory.createForSpectator(root, json),
        )
    }
}