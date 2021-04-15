package com.yanbin.geo.converter

import com.yanbin.geo.core.Geometry
import com.yanbin.geo.core.PathF
import com.yanbin.geo.core.PointF
import com.yanbin.geo.core.PolygonF
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class TopoJSONConverterTest {

    @Test
    internal fun convertOnePolygon() {
        val data = """
        {
          "type": "Topology",
          "objects": {
            "example": {
              "type": "GeometryCollection",
              "geometries": [
                {   
                  "type": "Polygon",
                  "arcs": [[1]]
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

        val expectGeometry = Geometry(
            polygons = listOf(
                PolygonF(
                    contour = PathF(
                        listOf(
                            PointF(0f, 0f),
                            PointF(0f, 9999f),
                            PointF(2000f, 0f),
                            PointF(0f, -9999f),
                            PointF(-2000f, 0f),
                        )
                    )
                )
            ),
            propertyList = emptyList()
        )

        val converter = TopoJSONConverter()
        val geometries = converter.fromString(data)

        assertTrue(geometries.size == 1)
        val geometry = geometries[0]

        assertEquals(expectGeometry, geometry)
    }
}