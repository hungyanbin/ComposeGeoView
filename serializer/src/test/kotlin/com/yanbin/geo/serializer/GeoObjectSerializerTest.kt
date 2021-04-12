package com.yanbin.geo.serializer

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GeoObjectSerializerTest {

    @Test
    internal fun `deserialize GeoObject Point`() {
        val data = """
            {
              "type": "Point",
              "properties": {
                "prop0": "value0"
              },
              "coordinates": [4000, 5000]
            }
        """
        val geoObject = Json.decodeFromString<GeoObject>(data)

        assertTrue(geoObject is GeoObject.Point)

        val point = geoObject as GeoObject.Point

        assertEquals(4000f, point.coordinates.first)
        assertEquals(5000f, point.coordinates.second)
    }


    @Test
    internal fun `deserialize GeoObject Point with no coordinates should throw SerializationException`() {
        val data = """
            {
              "type": "Point",
              "properties": {
                "prop0": "value0"
              }
            }
        """
        assertThrows(SerializationException::class.java) {
            Json.decodeFromString<GeoObject>(data)
        }
    }

    @Test
    internal fun `deserialize GeoObject properties`() {
        val data = """
            {
              "type": "Point",
              "properties": {
                "prop0": "value0"
              },
              "coordinates": [4000, 5000]
            }
        """
        val geoObject = Json.decodeFromString<GeoObject>(data)

        assertEquals("\"value0\"", geoObject.properties["prop0"])
    }

    @Test
    internal fun `deserialize GeoObject with no properties`() {
        val data = """
            {
              "type": "Point",
              "coordinates": [4000, 5000]
            }
        """
        val geoObject = Json.decodeFromString<GeoObject>(data)

        assertEquals(0, geoObject.properties.size)
    }
}