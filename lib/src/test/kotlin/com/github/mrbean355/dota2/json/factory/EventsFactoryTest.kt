package com.github.mrbean355.dota2.json.factory

import com.github.mrbean355.dota2.event.DotaEventImpl
import com.github.mrbean355.dota2.testutil.jsonObject
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class EventsFactoryTest {

    @Test
    internal fun testCreate_KeyMissing_ReturnsNull() {
        val events = EventsFactory.create("empty.json".jsonObject)

        assertNull(events)
    }

    @Test
    internal fun testCreate_DeserializesObject() {
        val events = EventsFactory.create("events.json".jsonObject)!!

        assertEquals(3, events.size)
        with(events[0] as DotaEventImpl) {
            assertEquals(2334, gameTime)
            assertEquals("aegis_picked_up", eventType)
            assertEquals(2, payload.size)
            assertEquals(1, payload.getValue("player_id").jsonPrimitive.int)
            assertFalse(payload.getValue("snatched").jsonPrimitive.boolean)
        }
        with(events[1] as DotaEventImpl) {
            assertEquals(2333, gameTime)
            assertEquals("roshan_killed", eventType)
            assertEquals(2, payload.size)
            assertEquals("dire", payload.getValue("killed_by_team").jsonPrimitive.content)
            assertEquals(1, payload.getValue("killer_player_id").jsonPrimitive.int)
        }
        with(events[2] as DotaEventImpl) {
            assertEquals(2542, gameTime)
            assertEquals("tip", eventType)
            assertEquals(3, payload.size)
            assertEquals(3, payload.getValue("sender_player_id").jsonPrimitive.int)
            assertEquals(0, payload.getValue("receiver_player_id").jsonPrimitive.int)
            assertEquals(50, payload.getValue("tip_amount").jsonPrimitive.int)
        }
    }
}