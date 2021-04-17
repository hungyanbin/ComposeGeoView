package com.yanbin.geo.converter

import com.yanbin.geo.core.Geometry
import com.yanbin.geo.core.PointF
import com.yanbin.geo.core.PolygonF
import com.yanbin.geo.serializer.Arc
import com.yanbin.geo.serializer.ArcIndexList
import com.yanbin.geo.serializer.ArcIntIndex
import com.yanbin.geo.serializer.GeoObject
import com.yanbin.geo.serializer.GeoType
import com.yanbin.geo.serializer.MultiArc
import com.yanbin.geo.serializer.PositionArc
import com.yanbin.geo.serializer.TopoJSON
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class TopoJSONConverter {

    fun fromString(content: String): List<Geometry> {
        val topoJSON = Json.decodeFromString<TopoJSON>(content)

        return convertGeoObject(topoJSON.geoObject, topoJSON)
    }

    private fun convertGeoObject(geoObject: GeoObject, topoJSON: TopoJSON): List<Geometry> {
       return when(geoObject.type) {
           GeoType.GeometryCollection -> geoObject.geometries!!.flatMap {
               convertGeoObject(it, topoJSON)
           }
           GeoType.Polygon -> listOf(convertPolygon(geoObject, topoJSON))
            else -> listOf()
        }
    }

    private fun convertPolygon(geoObject: GeoObject, topoJSON: TopoJSON): Geometry {
        val indexes = (geoObject.arcIndex as ArcIndexList).indexes[0].let { innerArcIndex ->
            (innerArcIndex as ArcIntIndex).indexes
        }

        require(indexes.size == 1)
        val index = indexes[0]

        val pathInArc = (topoJSON.arcs as MultiArc).arcs[index] as MultiArc
        val pathInDeltaPoints = pathInArc.arcs
            .asSequence()
            .filterIsInstance<PositionArc>()
            .map { position ->
                PointF(position.x.toFloat(), position.y.toFloat())
            }

        val pathInAbsolutePoints = pathInDeltaPoints.scan(PointF.DEFAULT) { accPoint, delta ->
            val endX = accPoint.x + delta.x
            val endY = accPoint.y + delta.y

            PointF(endX, endY)
        }.drop(1)

        val scaleX = topoJSON.transform?.scale?.first ?: 1f
        val scaleY = topoJSON.transform?.scale?.second ?: 1f
        val offsetX = topoJSON.transform?.translate?.first ?: 0f
        val offsetY = topoJSON.transform?.translate?.second ?: 0f

        val transformedPoints = pathInAbsolutePoints
            .map { normalizedPoint ->
                PointF(
                    normalizedPoint.x * scaleX + offsetX,
                    normalizedPoint.y * scaleY + offsetY
                )
            }

        val polygon = PolygonF(transformedPoints.toList())

        return Geometry(
            polygons = listOf(polygon),
            propertyList = emptyList()
        )
    }
}
