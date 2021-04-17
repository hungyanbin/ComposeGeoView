package com.yanbin.geo.serializer

import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.*
import kotlinx.serialization.decodeFromString
import org.junit.jupiter.api.Test

internal class TopoJsonSerializerTest {

    @Test
    internal fun `deserialize TopoJson`() {
        val data = """
        {
          "type": "Topology",
          "transform": {
            "scale": [0.0005000500050005, 0.00010001000100010001],
            "translate": [100, 0]
          },
          "objects": {
            "example": {
              "type": "GeometryCollection",
              "geometries": [
                {
                  "type": "Point",
                  "properties": {
                    "prop0": "value0"
                  },
                  "coordinates": [4000, 5000]
                }
              ]
            }
          },
          "arcs": [
            [[4000, 0], [1999, 9999], [2000, -9999], [2000, 9999]],
            [[0, 0], [0, 9999], [2000, 0], [0, -9999], [-2000, 0]]
          ]
        }
    """.trimIndent()

        val topoJSON = Json.decodeFromString<TopoJSON>(data)

        assertEquals("Topology", topoJSON.type)
        assertEquals(GeoType.GeometryCollection, topoJSON.geoObject.type)
        assertTrue(topoJSON.arcs is MultiArc)
        assertNotNull(topoJSON.transform)
    }

    @Test
    internal fun `deserialize TopoJson with no transform`() {
        val data = """
        {
          "type": "Topology",
          "objects": {
            "example": {
              "type": "GeometryCollection",
              "geometries": [
                {
                  "type": "Point",
                  "properties": {
                    "prop0": "value0"
                  },
                  "coordinates": [4000, 5000]
                }
              ]
            }
          },
          "arcs": [
            [[4000, 0], [1999, 9999], [2000, -9999], [2000, 9999]],
            [[0, 0], [0, 9999], [2000, 0], [0, -9999], [-2000, 0]]
          ]
        }
    """.trimIndent()

        val topoJSON = Json.decodeFromString<TopoJSON>(data)

        assertEquals("Topology", topoJSON.type)
        assertEquals(GeoType.GeometryCollection, topoJSON.geoObject.type)
        assertTrue(topoJSON.arcs is MultiArc)
        assertNull(topoJSON.transform)
    }
}
