package com.yanbin.geo.serializer

import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ArcsDeserializerTest {

    @Test
    internal fun `deserialize 1-d position arcs`() {
        val data = "[4000, 0]"
        val jsonElement = Json.parseToJsonElement(data)
        val arcs = ArcsDeserializer.deserialize(jsonElement)

        assertTrue(arcs is PositionArc)
        val positionArc = arcs as PositionArc
        assertEquals(4000, positionArc.x)
        assertEquals(0, positionArc.y)
    }

    @Test
    internal fun `deserialize 2-d multi arcs`() {
        val data = "[[4000, 0], [1999, 9999], [2000, -9999], [2000, 9999]]"
        val jsonElement = Json.parseToJsonElement(data)
        val arcs = ArcsDeserializer.deserialize(jsonElement)

        assertTrue(arcs is MultiArc)
        val multiArc = arcs as MultiArc
        assertEquals(4, multiArc.arcs.size)

        val firstPositionArc = multiArc.arcs[0] as PositionArc
        assertEquals(4000, firstPositionArc.x)
        assertEquals(0, firstPositionArc.y)

        val secondPositionArc = multiArc.arcs[1] as PositionArc
        assertEquals(1999, secondPositionArc.x)
        assertEquals(9999, secondPositionArc.y)

        val thirdPositionArc = multiArc.arcs[2] as PositionArc
        assertEquals(2000, thirdPositionArc.x)
        assertEquals(-9999, thirdPositionArc.y)

        val fourthPositionArc = multiArc.arcs[3] as PositionArc
        assertEquals(2000, fourthPositionArc.x)
        assertEquals(9999, fourthPositionArc.y)
    }

    @Test
    internal fun `deserialize 3-d multi arcs`() {
        val data = """[
            [[4000, 0], [1999, 9999], [2000, -9999], [2000, 9999]],
            [[0, 0], [0, 9999], [2000, 0], [0, -9999], [-2000, 0]]
          ]"""

        val jsonElement = Json.parseToJsonElement(data)
        val arcs = ArcsDeserializer.deserialize(jsonElement)

        assertTrue(arcs is MultiArc)
        val multiArcLayer1 = arcs as MultiArc
        assertEquals(2, multiArcLayer1.arcs.size)

        assertTrue(arcs.arcs[1] is MultiArc)
        val multiArcLayer2 = arcs.arcs[1] as MultiArc
        assertEquals(5, multiArcLayer2.arcs.size)

        val firstPositionArc = multiArcLayer2.arcs[0] as PositionArc
        assertEquals(0, firstPositionArc.x)
        assertEquals(0, firstPositionArc.y)
    }
}