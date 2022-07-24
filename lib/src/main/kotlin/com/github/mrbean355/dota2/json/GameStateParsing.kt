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

package com.github.mrbean355.dota2.json

import com.github.mrbean355.dota2.gamestate.GameState
import com.github.mrbean355.dota2.json.factory.AbilitiesFactory
import com.github.mrbean355.dota2.json.factory.DraftFactory
import com.github.mrbean355.dota2.json.factory.HeroFactory
import com.github.mrbean355.dota2.json.factory.IdleGameStateFactory
import com.github.mrbean355.dota2.json.factory.ItemsFactory
import com.github.mrbean355.dota2.json.factory.MapFactory
import com.github.mrbean355.dota2.json.factory.PlayerFactory
import com.github.mrbean355.dota2.json.factory.PlayingGameStateFactory
import com.github.mrbean355.dota2.json.factory.SpectatingGameStateFactory
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject

internal fun parseGameState(text: String): GameState {
    val root = Json.parseToJsonElement(text).jsonObject

    return when (findClientMode(root)) {
        ClientMode.Playing -> PlayingGameStateFactory.create(root)
        ClientMode.Spectating -> SpectatingGameStateFactory.create(root)
        ClientMode.Unknown -> IdleGameStateFactory.create(root)
    }
}

internal enum class ClientMode {
    Playing,
    Spectating,
    Unknown
}

/** Examines the JSON blob to try to determine what mode the Dota client is in. */
private fun findClientMode(root: JsonObject): ClientMode {
    MapFactory.getClientMode(root).let {
        if (it != ClientMode.Unknown) return it
    }

    PlayerFactory.getClientMode(root).let {
        if (it != ClientMode.Unknown) return it
    }

    HeroFactory.getClientMode(root).let {
        if (it != ClientMode.Unknown) return it
    }

    AbilitiesFactory.getClientMode(root).let {
        if (it != ClientMode.Unknown) return it
    }

    ItemsFactory.getClientMode(root).let {
        if (it != ClientMode.Unknown) return it
    }

    DraftFactory.getClientMode(root).let {
        if (it != ClientMode.Unknown) return it
    }

    return ClientMode.Unknown
}