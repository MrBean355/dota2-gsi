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

import com.github.mrbean355.dota2.ability.AbilityImpl
import com.github.mrbean355.dota2.json.ClientMode
import com.github.mrbean355.dota2.testutil.jsonObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class AbilitiesFactoryTest {

    @Test
    internal fun createForPlayer_KeyMissing_ReturnsNull() {
        val abilities = AbilitiesFactory.createForPlayer("empty.json".jsonObject)

        assertNull(abilities)
    }

    @Test
    internal fun createForPlayer_EmptyObject_ReturnsEmptyList() {
        val items = AbilitiesFactory.createForPlayer("abilities_invalid.json".jsonObject)!!

        assertTrue(items.isEmpty())
    }

    @Test
    internal fun testCreateForPlayer_DeserializesObject() {
        val abilities = AbilitiesFactory.createForPlayer("abilities_playing.json".jsonObject)!!
            .filterIsInstance<AbilityImpl>()

        assertEquals(7, abilities.size)
        abilities[0].verifyBoulderSmash()
        abilities[1].verifyRollingBoulder()
        abilities[2].verifyGeomagneticGrip()
        abilities[3].verifyStoneCaller()
        abilities[4].verifyMagnetize()
        abilities[5].verifyHighFive()
        abilities[6].verifyGuildBanner()
    }

    @Test
    internal fun createForSpectator_KeyMissing_ReturnsNull() {
        val abilities = AbilitiesFactory.createForSpectator("empty.json".jsonObject)

        assertNull(abilities)
    }

    @Test
    internal fun createForSpectator_EmptyObject_ReturnsEmptyMap() {
        val items = AbilitiesFactory.createForSpectator("abilities_invalid.json".jsonObject)!!

        assertTrue(items.isEmpty())
    }

    @Test
    internal fun testCreateForSpectator_DeserializesObject() {
        val abilities = AbilitiesFactory.createForSpectator("abilities_spectating.json".jsonObject)!!

        assertEquals(10, abilities.size)
        repeat(10) {
            assertTrue("player$it" in abilities)
        }
        val playerAbilities = abilities.getValue("player0")
            .filterIsInstance<AbilityImpl>()
        assertEquals(7, playerAbilities.size)
        playerAbilities[0].verifyBoulderSmash()
        playerAbilities[1].verifyRollingBoulder()
        playerAbilities[2].verifyGeomagneticGrip()
        playerAbilities[3].verifyStoneCaller()
        playerAbilities[4].verifyMagnetize()
        playerAbilities[5].verifyHighFive()
        playerAbilities[6].verifyGuildBanner()
    }

    @Test
    internal fun getClientMode_KeyMissing_ReturnsUnknownMode() {
        val mode = AbilitiesFactory.getClientMode("empty.json".jsonObject)

        assertSame(ClientMode.Unknown, mode)
    }

    @Test
    internal fun getClientMode_ContainsPlayerKey_ReturnsPlayingMode() {
        val mode = AbilitiesFactory.getClientMode("abilities_playing.json".jsonObject)

        assertSame(ClientMode.Playing, mode)
    }

    @Test
    internal fun getClientMode_DoesNotContainPlayerKey_NonEmptyObject_ReturnsSpectatingMode() {
        val mode = AbilitiesFactory.getClientMode("abilities_spectating.json".jsonObject)

        assertSame(ClientMode.Spectating, mode)
    }

    @Test
    internal fun getClientMode_DoesNotContainPlayerKey_EmptyObject_ReturnsUnknownMode() {
        val mode = AbilitiesFactory.getClientMode("abilities_invalid.json".jsonObject)

        assertSame(ClientMode.Unknown, mode)
    }

    private fun AbilityImpl.verifyBoulderSmash() {
        assertEquals("earth_spirit_boulder_smash", name)
        assertEquals(2, level)
        assertTrue(canCast)
        assertFalse(isPassive)
        assertTrue(isAbilityActive)
        assertEquals(0, cooldown)
        assertFalse(isUltimate)
        assertNull(charges)
        assertNull(maxCharges)
        assertNull(chargeCooldown)
    }

    private fun AbilityImpl.verifyRollingBoulder() {
        assertEquals("earth_spirit_rolling_boulder", name)
        assertEquals(2, level)
        assertTrue(canCast)
        assertFalse(isPassive)
        assertTrue(isAbilityActive)
        assertEquals(0, cooldown)
        assertFalse(isUltimate)
        assertNull(charges)
        assertNull(maxCharges)
        assertNull(chargeCooldown)
    }

    private fun AbilityImpl.verifyGeomagneticGrip() {
        assertEquals("earth_spirit_geomagnetic_grip", name)
        assertEquals(1, level)
        assertTrue(canCast)
        assertFalse(isPassive)
        assertTrue(isAbilityActive)
        assertEquals(0, cooldown)
        assertFalse(isUltimate)
        assertNull(charges)
        assertNull(maxCharges)
        assertNull(chargeCooldown)
    }

    private fun AbilityImpl.verifyStoneCaller() {
        assertEquals("earth_spirit_stone_caller", name)
        assertEquals(1, level)
        assertTrue(canCast)
        assertFalse(isPassive)
        assertTrue(isAbilityActive)
        assertEquals(0, cooldown)
        assertFalse(isUltimate)
        assertEquals(6, charges)
        assertEquals(7, maxCharges)
        assertEquals(12, chargeCooldown)
    }

    private fun AbilityImpl.verifyMagnetize() {
        assertEquals("earth_spirit_magnetize", name)
        assertEquals(1, level)
        assertTrue(canCast)
        assertFalse(isPassive)
        assertTrue(isAbilityActive)
        assertEquals(0, cooldown)
        assertTrue(isUltimate)
        assertNull(charges)
        assertNull(maxCharges)
        assertNull(chargeCooldown)
    }

    private fun AbilityImpl.verifyHighFive() {
        assertEquals("plus_high_five", name)
        assertEquals(1, level)
        assertTrue(canCast)
        assertFalse(isPassive)
        assertTrue(isAbilityActive)
        assertEquals(0, cooldown)
        assertFalse(isUltimate)
        assertNull(charges)
        assertNull(maxCharges)
        assertNull(chargeCooldown)
    }

    private fun AbilityImpl.verifyGuildBanner() {
        assertEquals("plus_guild_banner", name)
        assertEquals(1, level)
        assertTrue(canCast)
        assertFalse(isPassive)
        assertTrue(isAbilityActive)
        assertEquals(0, cooldown)
        assertFalse(isUltimate)
        assertNull(charges)
        assertNull(maxCharges)
        assertNull(chargeCooldown)
    }
}