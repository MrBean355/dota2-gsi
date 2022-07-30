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
import com.github.mrbean355.dota2.testutil.jsonObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class DraftFactoryTest {

    @Test
    internal fun createForSpectator_KeyMissing_ReturnsNull() {
        val draft = DraftFactory.createForSpectator("empty.json".jsonObject)

        assertNull(draft)
    }

    @Test
    internal fun createForSpectator_EmptyObject_ReturnsNull() {
        val draft = DraftFactory.createForSpectator("draft_invalid.json".jsonObject)

        assertNull(draft)
    }

    @Test
    internal fun createForSpectator_DeserializesObject() {
        val draft = DraftFactory.createForSpectator("draft_spectator.json".jsonObject)!!

        with(draft) {
            assertEquals(2, activeTeam)
            assertTrue(isPick)
            assertEquals(10, activeTeamTimeRemaining)
            assertEquals(118, radiantBonusTime)
            assertEquals(117, direBonusTime)

            with(radiantDraft) {
                assertFalse(isHomeTeam)
                assertEquals(2, picks.size)
                assertEquals(19, picks[0].id)
                assertEquals("tiny", picks[0].name)
                assertEquals(0, picks[1].id)
                assertEquals("", picks[1].name)
                assertEquals(2, bans.size)
                assertEquals(135, bans[0].id)
                assertEquals("dawnbreaker", bans[0].name)
                assertEquals(101, bans[1].id)
                assertEquals("skywrath_mage", bans[1].name)
            }

            with(direDraft) {
                assertTrue(isHomeTeam)
                assertEquals(2, picks.size)
                assertEquals(136, picks[0].id)
                assertEquals("marci", picks[0].name)
                assertEquals(78, picks[1].id)
                assertEquals("brewmaster", picks[1].name)
                assertEquals(2, bans.size)
                assertEquals(92, bans[0].id)
                assertEquals("visage", bans[0].name)
                assertEquals(22, bans[1].id)
                assertEquals("zuus", bans[1].name)
            }
        }
    }

    @Test
    internal fun getClientMode_KeyMissing_ReturnsUnknownMode() {
        val mode = DraftFactory.getClientMode("empty.json".jsonObject)

        assertSame(ClientMode.Unknown, mode)
    }

    @Test
    internal fun getClientMode_EmptyObject_ReturnsUnknownMode() {
        val mode = DraftFactory.getClientMode("draft_invalid.json".jsonObject)

        assertSame(ClientMode.Unknown, mode)
    }

    @Test
    internal fun getClientMode_NonEmptyObject_ReturnsSpectatingMode() {
        val mode = DraftFactory.getClientMode("draft_spectator.json".jsonObject)

        assertSame(ClientMode.Spectating, mode)
    }
}