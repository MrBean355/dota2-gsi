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

import com.github.mrbean355.dota2.hero.HeroImpl
import com.github.mrbean355.dota2.hero.SpectatedHeroImpl
import com.github.mrbean355.dota2.json.ClientMode
import com.github.mrbean355.dota2.testutil.jsonObject
import kotlinx.serialization.json.Json

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class HeroFactoryTest {

    @Test
    internal fun createForPlayer_KeyMissing_ReturnsNull() {
        val hero = HeroFactory.createForPlayer("empty.json".jsonObject, Json)

        assertNull(hero)
    }

    @Test
    internal fun createForPlayer_EmptyObject_ReturnsNull() {
        val hero = HeroFactory.createForPlayer("hero_invalid.json".jsonObject, Json)

        assertNull(hero)
    }

    @Test
    internal fun testCreateForPlayer_IncompleteHero_ReturnsNull() {
        val hero = HeroFactory.createForPlayer("hero_playing_incomplete.json".jsonObject, Json)

        assertNull(hero)
    }

    @Test
    internal fun testCreateForPlayer_DeserializesObject() {
        val hero = HeroFactory.createForPlayer("hero_playing.json".jsonObject, Json)!!

        with(hero as HeroImpl) {
            assertEquals(-1664, xPos)
            assertEquals(-1216, yPos)
            assertEquals(111, id)
            assertEquals("npc_dota_hero_oracle", name)
            assertEquals(1, level)
            assertEquals(0, xp)
            assertTrue(isAlive)
            assertEquals(3, respawnTimer)
            assertEquals(7899, buybackCost)
            assertEquals(4, buybackCooldown)
            assertEquals(600, health)
            assertEquals(600, maxHealth)
            assertEquals(100, healthPercent)
            assertEquals(387, mana)
            assertEquals(387, maxMana)
            assertEquals(100, manaPercent)
            assertFalse(isSilenced)
            assertTrue(isStunned)
            assertFalse(isDisarmed)
            assertTrue(isMagicImmune)
            assertFalse(isHexed)
            assertTrue(isMuted)
            assertFalse(isBroken)
            assertTrue(hasAghanimsScepter)
            assertFalse(hasAghanimsShard)
            assertTrue(isSmoked)
            assertFalse(hasDebuff)
            with(talentTree) {
                assertFalse(level10.hasLeft)
                assertTrue(level10.hasRight)
                assertFalse(level15.hasLeft)
                assertTrue(level15.hasRight)
                assertFalse(level20.hasLeft)
                assertTrue(level20.hasRight)
                assertFalse(level25.hasLeft)
                assertTrue(level25.hasRight)
            }
            assertEquals(2, attributesLevel)
        }
    }

    @Test
    internal fun createForSpectator_KeyMissing_ReturnsNull() {
        val hero = HeroFactory.createForSpectator("empty.json".jsonObject, Json)

        assertNull(hero)
    }

    @Test
    internal fun createForSpectator_EmptyObject_ReturnsEmptyMap() {
        val hero = HeroFactory.createForSpectator("hero_invalid.json".jsonObject, Json)!!

        assertTrue(hero.isEmpty())
    }

    @Test
    internal fun testCreateForSpectator_IncompleteHero_ReturnsNull() {
        val hero = HeroFactory.createForSpectator("hero_spectating_incomplete.json".jsonObject, Json)!!

        assertTrue(hero.isEmpty())
    }

    @Test
    internal fun testCreateForSpectator_DeserializesObject() {
        val hero = HeroFactory.createForSpectator("hero_spectating.json".jsonObject, Json)!!

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
            assertTrue(isAlive)
            assertEquals(0, respawnTimer)
            assertEquals(409, buybackCost)
            assertEquals(0, buybackCooldown)
            assertEquals(757, health)
            assertEquals(980, maxHealth)
            assertEquals(77, healthPercent)
            assertEquals(403, mana)
            assertEquals(553, maxMana)
            assertEquals(72, manaPercent)
            assertFalse(isSilenced)
            assertTrue(isStunned)
            assertFalse(isDisarmed)
            assertTrue(isMagicImmune)
            assertFalse(isHexed)
            assertTrue(isMuted)
            assertFalse(isBroken)
            assertTrue(hasAghanimsScepter)
            assertFalse(hasAghanimsShard)
            assertTrue(isSmoked)
            assertFalse(hasDebuff)
            assertTrue(isSelectedUnit)
            with(talentTree) {
                assertTrue(level10.hasLeft)
                assertFalse(level10.hasRight)
                assertTrue(level15.hasLeft)
                assertFalse(level15.hasRight)
                assertTrue(level20.hasLeft)
                assertFalse(level20.hasRight)
                assertTrue(level25.hasLeft)
                assertFalse(level25.hasRight)
            }
            assertEquals(7, attributesLevel)
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