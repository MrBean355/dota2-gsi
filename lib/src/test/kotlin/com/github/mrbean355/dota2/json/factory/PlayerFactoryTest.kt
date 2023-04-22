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

package com.github.mrbean355.dota2.json.factory

import com.github.mrbean355.dota2.json.ClientMode
import com.github.mrbean355.dota2.map.Team
import com.github.mrbean355.dota2.player.PlayerImpl
import com.github.mrbean355.dota2.player.SpectatedPlayerImpl
import com.github.mrbean355.dota2.testutil.jsonObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class PlayerFactoryTest {

    @Test
    internal fun createForPlayer_KeyMissing_ReturnsNull() {
        val player = PlayerFactory.createForPlayer("empty.json".jsonObject)

        assertNull(player)
    }

    @Test
    internal fun testCreateForPlayer_DeserializesObject() {
        val player = PlayerFactory.createForPlayer("player_playing.json".jsonObject)!!

        with(player as PlayerImpl) {
            assertEquals("2924123062", steamId)
            assertEquals("123456789", accountId)
            assertEquals("Mr_Bean", name)
            assertEquals("playing", activity)
            assertEquals(0, kills)
            assertEquals(1, deaths)
            assertEquals(2, assists)
            assertEquals(3, lastHits)
            assertEquals(4, denies)
            assertEquals(5, killStreak)
            assertEquals(6, commandsIssued)
            assertEquals(mapOf("victimid_1" to 7), killList)
            assertSame(Team.Radiant, team)
            assertEquals(15, playerSlot)
            assertEquals(16, teamSlot)
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
        val player = PlayerFactory.createForSpectator("empty.json".jsonObject)

        assertNull(player)
    }

    @Test
    internal fun createForSpectator_EmptyObject_ReturnsEmptyMap() {
        val player = PlayerFactory.createForSpectator("player_invalid.json".jsonObject)!!

        assertTrue(player.isEmpty())
    }

    @Test
    internal fun testCreateForSpectator_DeserializesObject() {
        val player = PlayerFactory.createForSpectator("player_spectating.json".jsonObject)!!

        assertEquals(2, player.size)
        assertTrue("team2" in player)
        assertTrue("team3" in player)

        with(player.getValue("team2")[2] as SpectatedPlayerImpl) {
            assertEquals("0524429230", steamId)
            assertEquals("0329244250", accountId)
            assertEquals("9 subhumans ^_^", name)
            assertEquals("playing", activity)
            assertEquals(3, kills)
            assertEquals(1, deaths)
            assertEquals(1, assists)
            assertEquals(55, lastHits)
            assertEquals(6, denies)
            assertEquals(2, killStreak)
            assertEquals(3170, commandsIssued)
            assertEquals(mapOf("victimid_8" to 2, "victimid_9" to 1), killList)
            assertEquals(Team.Radiant, team)
            assertEquals(3, playerSlot)
            assertEquals(3, teamSlot)
            assertEquals(1396, gold)
            assertEquals(441, goldReliable)
            assertEquals(955, goldUnreliable)
            assertEquals(637, goldFromHeroKills)
            assertEquals(1837, goldFromCreepKills)
            assertEquals(1020, goldFromIncome)
            assertEquals(158, goldFromShared)
            assertEquals(417, gpm)
            assertEquals(368, xpm)
            assertEquals("player2", id)
            assertEquals(4596, netWorth)
            assertEquals(3240, heroDamage)
            assertEquals(324, heroHealing)
            assertEquals(240, towerDamage)
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

    @Test
    internal fun getClientMode_KeyMissing_ReturnsUnknownMode() {
        val mode = PlayerFactory.getClientMode("empty.json".jsonObject)

        assertSame(ClientMode.Unknown, mode)
    }

    @Test
    internal fun getClientMode_ContainsPlayerKey_ReturnsPlayingMode() {
        val mode = PlayerFactory.getClientMode("player_playing.json".jsonObject)

        assertSame(ClientMode.Playing, mode)
    }

    @Test
    internal fun getClientMode_DoesNotContainPlayerKey_NonEmptyObject_ReturnsSpectatingMode() {
        val mode = PlayerFactory.getClientMode("player_spectating.json".jsonObject)

        assertSame(ClientMode.Spectating, mode)
    }

    @Test
    internal fun getClientMode_DoesNotContainPlayerKey_EmptyObject_ReturnsUnknownMode() {
        val mode = PlayerFactory.getClientMode("player_invalid.json".jsonObject)

        assertSame(ClientMode.Unknown, mode)
    }
}