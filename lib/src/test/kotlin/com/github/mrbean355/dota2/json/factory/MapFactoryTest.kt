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

import com.github.mrbean355.dota2.json.ClientMode
import com.github.mrbean355.dota2.map.MatchState
import com.github.mrbean355.dota2.map.PlayingMapImpl
import com.github.mrbean355.dota2.map.SpectatedMapImpl
import com.github.mrbean355.dota2.map.Team
import com.github.mrbean355.dota2.testutil.jsonObject
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class MapFactoryTest {

    @Test
    internal fun createForPlayer_KeyMissing_ReturnsNull() {
        val map = MapFactory.createForPlayer("empty.json".jsonObject, Json)

        assertNull(map)
    }

    @Test
    internal fun testCreateForPlayer_DeserializesObject() {
        val map = MapFactory.createForPlayer("map_playing.json".jsonObject, Json)!!

        with(map as PlayingMapImpl) {
            assertEquals("start", name)
            assertEquals("6671869265", matchId)
            assertEquals(891, gameTime)
            assertEquals(713, clockTime)
            assertTrue(isDaytime)
            assertFalse(isNightStalkerNight)
            assertEquals(12, radiantScore)
            assertEquals(24, direScore)
            assertSame(MatchState.GameInProgress, matchState)
            assertFalse(isPaused)
            assertSame(Team.None, winningTeam)
            assertEquals("", customGameName)
            assertEquals(37, wardPurchaseCooldown)
        }
    }

    @Test
    internal fun createForSpectator_KeyMissing_ReturnsNull() {
        val map = MapFactory.createForSpectator("empty.json".jsonObject, Json)

        assertNull(map)
    }

    @Test
    internal fun testCreateForSpectator_DeserializesObject() {
        val map = MapFactory.createForSpectator("map_spectating.json".jsonObject, Json)!!

        with(map as SpectatedMapImpl) {
            assertEquals("start", name)
            assertEquals("6671869265", matchId)
            assertEquals(891, gameTime)
            assertEquals(713, clockTime)
            assertTrue(isDaytime)
            assertFalse(isNightStalkerNight)
            assertEquals(12, radiantScore)
            assertEquals(24, direScore)
            assertSame(MatchState.GameInProgress, matchState)
            assertFalse(isPaused)
            assertSame(Team.None, winningTeam)
            assertEquals("", customGameName)
            assertEquals(1, radiantWardPurchaseCooldown)
            assertEquals(2, direWardPurchaseCooldown)
            assertEquals("string", roshanState)
            assertEquals(3, roshanStateEndTimer)
            assertEquals(4, radiantWinChance)
        }
    }

    @Test
    internal fun getClientMode_KeyMissing_ReturnsUnknownMode() {
        val mode = MapFactory.getClientMode("empty.json".jsonObject)

        assertSame(ClientMode.Unknown, mode)
    }

    @Test
    internal fun getClientMode_ContainsPlayerKey_ReturnsPlayingMode() {
        val mode = MapFactory.getClientMode("map_playing.json".jsonObject)

        assertSame(ClientMode.Playing, mode)
    }

    @Test
    internal fun getClientMode_ContainsSpectatorKey_ReturnsSpectatingMode() {
        val mode = MapFactory.getClientMode("map_spectating.json".jsonObject)

        assertSame(ClientMode.Spectating, mode)
    }

    @Test
    internal fun getClientMode_ModeDiscriminatorMissing_ReturnsUnknownMode() {
        val mode = MapFactory.getClientMode("map_invalid.json".jsonObject)

        assertSame(ClientMode.Unknown, mode)
    }
}