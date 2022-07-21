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

package com.github.mrbean355.dota2.json.factory

import com.github.mrbean355.dota2.player.PlayerImpl
import com.github.mrbean355.dota2.player.SpectatedPlayerImpl
import com.github.mrbean355.dota2.testutil.jsonObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class PlayerFactoryTest {

    @Test
    internal fun createForPlayer_KeyMissing_ReturnsNull() {
        val map = PlayerFactory.createForPlayer("empty.json".jsonObject)

        assertNull(map)
    }

    @Test
    internal fun testCreateForPlayer_DeserializesObject() {
        val map = PlayerFactory.createForPlayer("player_playing.json".jsonObject)!!

        with(map as PlayerImpl) {
            assertEquals("2924123062", steamId)
            assertEquals("Mr_Bean", name)
            assertEquals("playing", activity)
            assertEquals(0, kills)
            assertEquals(1, deaths)
            assertEquals(2, assists)
            assertEquals(3, lastHits)
            assertEquals(4, denies)
            assertEquals(5, killStreak)
            assertEquals(6, commandsIssued)
            assertEquals(listOf(7), killList)
            assertEquals("radiant", teamName)
            assertEquals(600, gold)
            assertEquals(8, goldReliable)
            assertEquals(550, goldUnreliable)
            assertEquals(9, goldFromHeroKills)
            assertEquals(10, goldFromCreepKills)
            assertEquals(11, goldFromIncome)
            assertEquals(12, goldFromShared)
            assertEquals(13, gpm)
            assertEquals(14, xpm)
        }
    }

    @Test
    internal fun createForSpectator_KeyMissing_ReturnsNull() {
        val map = PlayerFactory.createForSpectator("empty.json".jsonObject)

        assertNull(map)
    }

    @Test
    internal fun testCreateForSpectator_DeserializesObject() {
        val map = PlayerFactory.createForSpectator("player_spectating.json".jsonObject)!!

        assertEquals(10, map.size)
        repeat(10) {
            assertTrue("player$it" in map)
        }
        with(map.getValue("player2") as SpectatedPlayerImpl) {
            assertEquals("0524429230", steamId)
            assertEquals("9 subhumans ^_^", name)
            assertEquals("playing", activity)
            assertEquals(3, kills)
            assertEquals(1, deaths)
            assertEquals(1, assists)
            assertEquals(55, lastHits)
            assertEquals(6, denies)
            assertEquals(2, killStreak)
            assertEquals(3170, commandsIssued)
            assertEquals(listOf(2, 1), killList)
            assertEquals("radiant", teamName)
            assertEquals(1396, gold)
            assertEquals(441, goldReliable)
            assertEquals(955, goldUnreliable)
            assertEquals(637, goldFromHeroKills)
            assertEquals(1837, goldFromCreepKills)
            assertEquals(1020, goldFromIncome)
            assertEquals(158, goldFromShared)
            assertEquals(417, gpm)
            assertEquals(368, xpm)
            assertEquals(4596, netWorth)
            assertEquals(3240, heroDamage)
            assertEquals(0, wardsPurchased)
            assertEquals(6, wardsPlaced)
            assertEquals(2, wardsDestroyed)
            assertEquals(1, runesActivated)
            assertEquals(9, campsStacked)
            assertEquals(22, supportGoldSpent)
            assertEquals(340, consumableGoldSpent)
            assertEquals(2950, itemGoldSpent)
            assertEquals(52, goldLostToDeath)
            assertEquals(33, goldSpentOnBuybacks)
        }
    }
}