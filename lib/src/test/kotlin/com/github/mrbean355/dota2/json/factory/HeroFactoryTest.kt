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

import com.github.mrbean355.dota2.hero.HeroImpl
import com.github.mrbean355.dota2.hero.SpectatedHeroImpl
import com.github.mrbean355.dota2.json.ClientMode
import com.github.mrbean355.dota2.testutil.jsonObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class HeroFactoryTest {

    @Test
    internal fun createForPlayer_KeyMissing_ReturnsNull() {
        val hero = HeroFactory.createForPlayer("empty.json".jsonObject)

        assertNull(hero)
    }

    @Test
    internal fun createForPlayer_EmptyObject_ReturnsNull() {
        val hero = HeroFactory.createForPlayer("hero_invalid.json".jsonObject)

        assertNull(hero)
    }

    @Test
    internal fun testCreateForPlayer_IncompleteHero_ReturnsNull() {
        val hero = HeroFactory.createForPlayer("hero_playing_incomplete.json".jsonObject)

        assertNull(hero)
    }

    @Test
    internal fun testCreateForPlayer_DeserializesObject() {
        val hero = HeroFactory.createForPlayer("hero_playing.json".jsonObject)!!

        with(hero as HeroImpl) {
            assertEquals(-1664, xPos)
            assertEquals(-1216, yPos)
            assertEquals(111, id)
            assertEquals("npc_dota_hero_oracle", name)
            assertEquals(1, level)
            assertEquals(0, xp)
            assertTrue(alive)
            assertEquals(3, respawnSeconds)
            assertEquals(7899, buybackCost)
            assertEquals(4, buybackCooldown)
            assertEquals(600, health)
            assertEquals(600, maxHealth)
            assertEquals(100, healthPercent)
            assertEquals(387, mana)
            assertEquals(387, maxMana)
            assertEquals(100, manaPercent)
            assertFalse(silenced)
            assertTrue(stunned)
            assertFalse(disarmed)
            assertTrue(magicImmune)
            assertFalse(hexed)
            assertTrue(muted)
            assertFalse(`break`)
            assertTrue(aghanimsScepter)
            assertFalse(aghanimsShard)
            assertTrue(smoked)
            assertFalse(hasDebuff)
            assertTrue(talent1)
            assertFalse(talent2)
            assertTrue(talent3)
            assertFalse(talent4)
            assertTrue(talent5)
            assertFalse(talent6)
            assertTrue(talent7)
            assertFalse(talent8)
        }
    }

    @Test
    internal fun createForSpectator_KeyMissing_ReturnsNull() {
        val hero = HeroFactory.createForSpectator("empty.json".jsonObject)

        assertNull(hero)
    }

    @Test
    internal fun createForSpectator_EmptyObject_ReturnsEmptyMap() {
        val hero = HeroFactory.createForSpectator("hero_invalid.json".jsonObject)!!

        assertTrue(hero.isEmpty())
    }

    @Test
    internal fun testCreateForSpectator_IncompleteHero_ReturnsNull() {
        val hero = HeroFactory.createForSpectator("hero_spectating_incomplete.json".jsonObject)!!

        assertTrue(hero.isEmpty())
    }

    @Test
    internal fun testCreateForSpectator_DeserializesObject() {
        val hero = HeroFactory.createForSpectator("hero_spectating.json".jsonObject)!!

        assertEquals(10, hero.size)
        repeat(10) {
            assertTrue("player$it" in hero)
        }
        with(hero.getValue("player6") as SpectatedHeroImpl) {
            assertEquals(5630, xPos)
            assertEquals(-5169, yPos)
            assertEquals(33, id)
            assertEquals("npc_dota_hero_enigma", name)
            assertEquals(6, level)
            assertEquals(2608, xp)
            assertTrue(alive)
            assertEquals(0, respawnSeconds)
            assertEquals(409, buybackCost)
            assertEquals(0, buybackCooldown)
            assertEquals(757, health)
            assertEquals(980, maxHealth)
            assertEquals(77, healthPercent)
            assertEquals(403, mana)
            assertEquals(553, maxMana)
            assertEquals(72, manaPercent)
            assertFalse(silenced)
            assertTrue(stunned)
            assertFalse(disarmed)
            assertTrue(magicImmune)
            assertFalse(hexed)
            assertTrue(muted)
            assertFalse(`break`)
            assertTrue(aghanimsScepter)
            assertFalse(aghanimsShard)
            assertTrue(smoked)
            assertFalse(hasDebuff)
            assertTrue(selectedUnit)
            assertFalse(talent1)
            assertTrue(talent2)
            assertFalse(talent3)
            assertTrue(talent4)
            assertFalse(talent5)
            assertTrue(talent6)
            assertFalse(talent7)
            assertTrue(talent8)
        }
    }

    @Test
    internal fun getClientMode_KeyMissing_ReturnsUnknownMode() {
        val mode = HeroFactory.getClientMode("empty.json".jsonObject)

        assertSame(ClientMode.Unknown, mode)
    }

    @Test
    internal fun getClientMode_ContainsPlayerKey_ReturnsPlayingMode() {
        val mode = HeroFactory.getClientMode("hero_playing.json".jsonObject)

        assertSame(ClientMode.Playing, mode)
    }

    @Test
    internal fun getClientMode_DoesNotContainPlayerKey_NonEmptyObject_ReturnsSpectatingMode() {
        val mode = HeroFactory.getClientMode("hero_spectating.json".jsonObject)

        assertSame(ClientMode.Spectating, mode)
    }

    @Test
    internal fun getClientMode_DoesNotContainPlayerKey_EmptyObject_ReturnsUnknownMode() {
        val mode = HeroFactory.getClientMode("hero_invalid.json".jsonObject)

        assertSame(ClientMode.Unknown, mode)
    }
}