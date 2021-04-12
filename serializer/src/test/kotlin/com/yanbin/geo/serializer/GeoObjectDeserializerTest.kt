package com.yanbin.geo.serializer

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GeoObjectDeserializerTest {

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
        val jsonElement = Json.parseToJsonElement(data)
        val geoObject = GeoObjectDeserializer.deserialize(jsonElement)

        assertEquals(GeoType.Point, geoObject.type)

        assertEquals(4000f, geoObject.coordinates.first)
        assertEquals(5000f, geoObject.coordinates.second)
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
            val jsonElement = Json.parseToJsonElement(data)
            GeoObjectDeserializer.deserialize(jsonElement)
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
        val jsonElement = Json.parseToJsonElement(data)
        val geoObject = GeoObjectDeserializer.deserialize(jsonElement)

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
        val jsonElement = Json.parseToJsonElement(data)
        val geoObject = GeoObjectDeserializer.deserialize(jsonElement)

        assertEquals(0, geoObject.properties.size)
    }
}