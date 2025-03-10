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

import com.github.mrbean355.dota2.item.BottledRune
import com.github.mrbean355.dota2.item.Item
import com.github.mrbean355.dota2.item.Items
import com.github.mrbean355.dota2.json.ClientMode
import com.github.mrbean355.dota2.testutil.jsonObject
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class ItemsFactoryTest {

    @Test
    internal fun createForPlayer_KeyMissing_ReturnsNull() {
        val items = ItemsFactory.createForPlayer("empty.json".jsonObject, Json)

        assertNull(items)
    }

    @Test
    internal fun createForPlayer_EmptyObject_ReturnsNull() {
        val items = ItemsFactory.createForPlayer("items_invalid.json".jsonObject, Json)

        assertNull(items)
    }

    @Test
    internal fun testCreateForPlayer_DeserializesObject() {
        val items = ItemsFactory.createForPlayer("items_playing.json".jsonObject, Json)!!

        assertEquals(9, items.inventory.size)
        assertEquals(6, items.stash.size)

        with(items) {
            verifyUrnOfShadows()
            verifyMagicWand()
            verifyAetherLens()
            inventory.verifyEmptySlot(3)
            verifyArcaneBoots()
            inventory.verifyEmptySlot(5)
            verifySmokeOfDeceit()
            inventory.verifyEmptySlot(7)
            verifyBottle()

            verifyAeonDisk()
            stash.verifyEmptySlot(1)
            verifyHandOfMidas()
            verifyGemOfTrueSight()
            stash.verifyEmptySlot(4)
            verifySentryWard()

            verifyTeleport()
            verifyNeutral()
            verifyNeutralEnchantment()
            verifyPreservedNeutrals()
        }
    }

    @Test
    internal fun createForSpectator_KeyMissing_ReturnsNull() {
        val items = ItemsFactory.createForSpectator("empty.json".jsonObject, Json)

        assertNull(items)
    }

    @Test
    internal fun createForSpectator_EmptyObject_ReturnsEmptyMap() {
        val items = ItemsFactory.createForSpectator("items_invalid.json".jsonObject, Json)!!

        assertTrue(items.isEmpty())
    }

    @Test
    internal fun createForSpectator_DeserializesObject() {
        val items = ItemsFactory.createForSpectator("items_spectating.json".jsonObject, Json)!!

        assertEquals(10, items.size)
        repeat(10) {
            assertTrue("player$it" in items)
        }
        with(items.getValue("player9")) {
            verifyUrnOfShadows()
            verifyMagicWand()
            verifyAetherLens()
            inventory.verifyEmptySlot(3)
            verifyArcaneBoots()
            inventory.verifyEmptySlot(5)
            verifySmokeOfDeceit()
            inventory.verifyEmptySlot(7)
            verifyBottle()

            verifyAeonDisk()
            stash.verifyEmptySlot(1)
            verifyHandOfMidas()
            verifyGemOfTrueSight()
            stash.verifyEmptySlot(4)
            verifySentryWard()

            verifyTeleport()
            verifyNeutral()
            verifyNeutralEnchantment()
            verifyPreservedNeutrals()
        }
    }

    @Test
    internal fun getClientMode_KeyMissing_ReturnsUnknownMode() {
        val mode = ItemsFactory.getClientMode("empty.json".jsonObject)

        assertSame(ClientMode.Unknown, mode)
    }

    @Test
    internal fun getClientMode_ContainsPlayerKey_ReturnsPlayingMode() {
        val mode = ItemsFactory.getClientMode("items_playing.json".jsonObject)

        assertSame(ClientMode.Playing, mode)
    }

    @Test
    internal fun getClientMode_DoesNotContainPlayerKey_NonEmptyObject_ReturnsSpectatingMode() {
        val mode = ItemsFactory.getClientMode("items_spectating.json".jsonObject)

        assertSame(ClientMode.Spectating, mode)
    }

    @Test
    internal fun getClientMode_DoesNotContainPlayerKey_EmptyObject_ReturnsUnknownMode() {
        val mode = ItemsFactory.getClientMode("items_invalid.json".jsonObject)

        assertSame(ClientMode.Unknown, mode)
    }

    private fun Items.verifyUrnOfShadows() {
        with(inventory[0]) {
            assertEquals("item_urn_of_shadows", name)
            assertEquals(0, purchaser)
            assertNull(level)
            assertNull(bottledRune)
            assertFalse(canCast!!)
            assertEquals(0, cooldown)
            assertFalse(isPassive!!)
            assertNull(itemCharges)
            assertNull(abilityCharges)
            assertNull(maxCharges)
            assertNull(chargeCooldown)
            assertEquals(0, charges)
        }
    }

    private fun Items.verifyMagicWand() {
        with(inventory[1]) {
            assertEquals("item_magic_wand", name)
            assertEquals(0, purchaser)
            assertNull(level)
            assertNull(bottledRune)
            assertFalse(canCast!!)
            assertEquals(0, cooldown)
            assertFalse(isPassive!!)
            assertNull(itemCharges)
            assertNull(abilityCharges)
            assertNull(maxCharges)
            assertNull(chargeCooldown)
            assertEquals(0, charges)
        }
    }

    private fun Items.verifyAetherLens() {
        with(inventory[2]) {
            assertEquals("item_aether_lens", name)
            assertEquals(0, purchaser)
            assertNull(level)
            assertNull(bottledRune)
            assertNull(canCast)
            assertNull(cooldown)
            assertTrue(isPassive!!)
            assertNull(itemCharges)
            assertNull(abilityCharges)
            assertNull(maxCharges)
            assertNull(chargeCooldown)
            assertNull(charges)
        }
    }

    private fun List<Item>.verifyEmptySlot(index: Int) {
        with(get(index)) {
            assertEquals("empty", name)
            assertNull(purchaser)
            assertNull(level)
            assertNull(bottledRune)
            assertNull(canCast)
            assertNull(cooldown)
            assertNull(isPassive)
            assertNull(itemCharges)
            assertNull(abilityCharges)
            assertNull(maxCharges)
            assertNull(chargeCooldown)
            assertNull(charges)
        }
    }

    private fun Items.verifyArcaneBoots() {
        with(inventory[4]) {
            assertEquals("item_arcane_boots", name)
            assertEquals(0, purchaser)
            assertNull(level)
            assertNull(bottledRune)
            assertTrue(canCast!!)
            assertEquals(0, cooldown)
            assertFalse(isPassive!!)
            assertNull(itemCharges)
            assertNull(abilityCharges)
            assertNull(maxCharges)
            assertNull(chargeCooldown)
            assertNull(charges)
        }
    }

    private fun Items.verifySmokeOfDeceit() {
        with(inventory[6]) {
            assertEquals("item_smoke_of_deceit", name)
            assertEquals(0, purchaser)
            assertNull(level)
            assertNull(bottledRune)
            assertTrue(canCast!!)
            assertEquals(0, cooldown)
            assertFalse(isPassive!!)
            assertNull(itemCharges)
            assertNull(abilityCharges)
            assertNull(maxCharges)
            assertNull(chargeCooldown)
            assertEquals(1, charges)
        }
    }

    private fun Items.verifyBottle() {
        with(inventory[8]) {
            assertEquals("item_bottle", name)
            assertEquals(0, purchaser)
            assertNull(level)
            assertSame(BottledRune.Regeneration, bottledRune)
            assertTrue(canCast!!)
            assertEquals(0, cooldown)
            assertFalse(isPassive!!)
            assertNull(itemCharges)
            assertNull(abilityCharges)
            assertNull(maxCharges)
            assertNull(chargeCooldown)
            assertEquals(3, charges)
        }
    }

    private fun Items.verifyAeonDisk() {
        with(stash[0]) {
            assertEquals("item_aeon_disk", name)
            assertEquals(0, purchaser)
            assertNull(level)
            assertNull(bottledRune)
            assertNull(canCast)
            assertNull(cooldown)
            assertTrue(isPassive!!)
            assertNull(itemCharges)
            assertNull(abilityCharges)
            assertNull(maxCharges)
            assertNull(chargeCooldown)
            assertNull(charges)
        }
    }

    private fun Items.verifyHandOfMidas() {
        with(stash[2]) {
            assertEquals("item_hand_of_midas", name)
            assertEquals(0, purchaser)
            assertEquals(1, level)
            assertNull(bottledRune)
            assertTrue(canCast!!)
            assertEquals(0, cooldown)
            assertFalse(isPassive!!)
            assertEquals(1, itemCharges)
            assertEquals(3, abilityCharges)
            assertEquals(2, maxCharges)
            assertEquals(50, chargeCooldown)
            assertEquals(1, charges)
        }
    }

    private fun Items.verifyGemOfTrueSight() {
        with(stash[3]) {
            assertEquals("item_gem", name)
            assertEquals(0, purchaser)
            assertNull(level)
            assertNull(bottledRune)
            assertTrue(canCast!!)
            assertEquals(0, cooldown)
            assertFalse(isPassive!!)
            assertNull(itemCharges)
            assertNull(abilityCharges)
            assertNull(maxCharges)
            assertNull(chargeCooldown)
            assertNull(charges)
        }
    }

    private fun Items.verifySentryWard() {
        with(stash[5]) {
            assertEquals("item_ward_sentry", name)
            assertEquals(0, purchaser)
            assertNull(level)
            assertNull(bottledRune)
            assertTrue(canCast!!)
            assertEquals(0, cooldown)
            assertFalse(isPassive!!)
            assertNull(itemCharges)
            assertNull(abilityCharges)
            assertNull(maxCharges)
            assertNull(chargeCooldown)
            assertEquals(1, charges)
        }
    }

    private fun Items.verifyTeleport() {
        assertEquals("item_tpscroll", teleport.name)
        assertEquals(0, teleport.purchaser)
        assertNull(teleport.level)
        assertNull(teleport.bottledRune)
        assertFalse(teleport.canCast!!)
        assertEquals(60, teleport.cooldown)
        assertFalse(teleport.isPassive!!)
        assertNull(teleport.itemCharges)
        assertNull(teleport.abilityCharges)
        assertNull(teleport.maxCharges)
        assertNull(teleport.chargeCooldown)
        assertEquals(1, teleport.charges)
    }

    private fun Items.verifyNeutral() {
        assertEquals("item_keen_optic", neutral.name)
        assertEquals(-1, neutral.purchaser)
        assertNull(neutral.level)
        assertNull(neutral.bottledRune)
        assertNull(neutral.canCast)
        assertNull(neutral.cooldown)
        assertTrue(neutral.isPassive!!)
        assertNull(neutral.itemCharges)
        assertNull(neutral.abilityCharges)
        assertNull(neutral.maxCharges)
        assertNull(neutral.chargeCooldown)
        assertNull(neutral.charges)
    }

    private fun Items.verifyNeutralEnchantment() {
        assertEquals("item_enhancement_keen_eyed", neutralEnchantment.name)
        assertEquals(-1, neutralEnchantment.purchaser)
        assertNull(neutralEnchantment.level)
        assertNull(neutralEnchantment.bottledRune)
        assertNull(neutralEnchantment.canCast)
        assertNull(neutralEnchantment.cooldown)
        assertTrue(neutralEnchantment.isPassive!!)
        assertNull(neutralEnchantment.itemCharges)
        assertNull(neutralEnchantment.abilityCharges)
        assertNull(neutralEnchantment.maxCharges)
        assertNull(neutralEnchantment.chargeCooldown)
        assertNull(neutralEnchantment.charges)
    }

    private fun Items.verifyPreservedNeutrals() {
        assertEquals(5, preservedNeutrals.size)

        preservedNeutrals.forEach { item ->
            assertEquals("empty", item.name)
            assertNull(item.purchaser)
            assertNull(item.level)
            assertNull(item.bottledRune)
            assertNull(item.canCast)
            assertNull(item.cooldown)
            assertNull(item.isPassive)
            assertNull(item.itemCharges)
            assertNull(item.abilityCharges)
            assertNull(item.maxCharges)
            assertNull(item.chargeCooldown)
            assertNull(item.charges)
        }
    }
}