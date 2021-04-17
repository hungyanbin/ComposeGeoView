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
              "coordinates": [4000, 5000]
            }
        """
        val jsonElement = Json.parseToJsonElement(data)
        val geoObject = GeoObjectDeserializer.deserialize(jsonElement)

        assertEquals(GeoType.Point, geoObject.type)

        assertEquals(4000f, geoObject.coordinates?.first)
        assertEquals(5000f, geoObject.coordinates?.second)
    }


    @Test
    internal fun `deserialize GeoObject Point with no coordinates should throw SerializationException`() {
        val data = """
            {
              "type": "Point"
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

    @Test
    internal fun `deserialize GeoObject LineString`() {
        val data = """
            {
              "type": "LineString",
              "arcs": [0]
            }
        """
        val jsonElement = Json.parseToJsonElement(data)
        val geoObject = GeoObjectDeserializer.deserialize(jsonElement)

        assertEquals(GeoType.LineString, geoObject.type)
        assertNotNull(geoObject.arcIndex)
    }

    @Test
    internal fun `deserialize GeoObject LineString with no arcs should throw SerializationException`() {
        val data = """
           {
              "type": "LineString"
           }
        """
        assertThrows(SerializationException::class.java) {
            val jsonElement = Json.parseToJsonElement(data)
            GeoObjectDeserializer.deserialize(jsonElement)
        }
    }

    @Test
    internal fun `deserialize GeoObject Polygon`() {
        val data = """
          {
            "type": "Polygon",
            "arcs": [[1]]
          }
        """
        val jsonElement = Json.parseToJsonElement(data)
        val geoObject = GeoObjectDeserializer.deserialize(jsonElement)

        assertEquals(GeoType.Polygon, geoObject.type)
        assertNotNull(geoObject.arcIndex)
    }

    @Test
    internal fun `deserialize GeoObject Polygon with no arcs should throw SerializationException`() {
        val data = """
           {
              "type": "Polygon"
           }
        """
        assertThrows(SerializationException::class.java) {
            val jsonElement = Json.parseToJsonElement(data)
            GeoObjectDeserializer.deserialize(jsonElement)
        }
    }

    @Test
    internal fun `deserialize GeoObject MultiPolygon`() {
        val data = """
          {
            "type": "MultiPolygon",
            "arcs": [[[1]]]
          }
        """
        val jsonElement = Json.parseToJsonElement(data)
        val geoObject = GeoObjectDeserializer.deserialize(jsonElement)

        assertEquals(GeoType.MultiPolygon, geoObject.type)
        assertNotNull(geoObject.arcIndex)
    }

    @Test
    internal fun `deserialize GeoObject MultiPolygon with no arcs should throw SerializationException`() {
        val data = """
           {
              "type": "MultiPolygon"
           }
        """
        assertThrows(SerializationException::class.java) {
            val jsonElement = Json.parseToJsonElement(data)
            GeoObjectDeserializer.deserialize(jsonElement)
        }
    }

//    GeoCollection

    @Test
    internal fun `deserialize GeoObject GeometryCollection`() {
        val data = """
          {
            "type": "GeometryCollection",
            "geometries": [
                {
                  "type": "Point",
                  "coordinates": [4000, 5000]
                }
            ]
          }
        """
        val jsonElement = Json.parseToJsonElement(data)
        val geoObject = GeoObjectDeserializer.deserialize(jsonElement)

        assertEquals(GeoType.GeometryCollection, geoObject.type)
        assertNotNull(geoObject.geometries)
    }
}
