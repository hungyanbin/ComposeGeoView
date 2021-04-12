package com.yanbin.geo.serializer

import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ArcIndexSerializerTest {

    @Test
    internal fun `deserialize 1-d ArcIndex`() {
        val data = "[0]"
        val arcIndex = Json.decodeFromString<ArcIndex>(data)

        assertTrue(arcIndex is ArcIntIndex)
        val arcIntIndex = arcIndex as ArcIntIndex
        assertEquals(1, arcIntIndex.indexes.size)
        assertEquals(0, arcIntIndex.indexes[0])
    }

    @Test
    internal fun `deserialize 2-d ArcIndex`() {
        val data = "[[1, 3]]"
        val arcIndex = Json.decodeFromString<ArcIndex>(data)

        assertTrue(arcIndex is ArcIndexList)
        val arcIndexList = arcIndex as ArcIndexList

        assertEquals(1, arcIndexList.indexes.size)
        assertTrue(arcIndexList.indexes[0] is ArcIntIndex)

        val arcIntIndex = arcIndexList.indexes[0] as ArcIntIndex
        assertEquals(2, arcIntIndex.indexes.size)
        assertEquals(1, arcIntIndex.indexes[0])
        assertEquals(3, arcIntIndex.indexes[1])
    }

}