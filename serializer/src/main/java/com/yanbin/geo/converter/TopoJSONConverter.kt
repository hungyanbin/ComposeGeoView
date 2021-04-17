package com.yanbin.geo.converter

import com.yanbin.geo.core.Geometry
import com.yanbin.geo.core.PointF
import com.yanbin.geo.core.PolygonF
import com.yanbin.geo.serializer.ArcIndex
import com.yanbin.geo.serializer.ArcIndexList
import com.yanbin.geo.serializer.ArcIntIndex
import com.yanbin.geo.serializer.GeoObject
import com.yanbin.geo.serializer.GeoType
import com.yanbin.geo.serializer.MultiArc
import com.yanbin.geo.serializer.PositionArc
import com.yanbin.geo.serializer.TopoJSON
import com.yanbin.geo.serializer.Transform
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
           GeoType.MultiPolygon -> listOf(convertMultiPolygon(geoObject, topoJSON))
            else -> listOf()
        }
    }

    private fun convertMultiPolygon(geoObject: GeoObject, topoJSON: TopoJSON): Geometry {
        val polygonIndexes = (geoObject.arcIndex as ArcIndexList).indexes

        val polygons = polygonIndexes
            .map { arcIndex ->
                findAllPolygonArcIndexes(arcIndex)
            }.map { polygonArcIndexes ->
                constructSinglePolygon(polygonArcIndexes, topoJSON)
            }

        return Geometry(
            polygons = polygons,
            propertyList = emptyList()
        )
    }

    private fun findAllPolygonArcIndexes(arcIndex: ArcIndex): List<Int> {
        return (arcIndex as ArcIndexList).indexes[0].let { innerArcIndex ->
            (innerArcIndex as ArcIntIndex).indexes
        }
    }

    private fun convertPolygon(geoObject: GeoObject, topoJSON: TopoJSON): Geometry {
        val polygonArcIndexes = findAllPolygonArcIndexes(geoObject.arcIndex!!)
        val polygon = constructSinglePolygon(polygonArcIndexes, topoJSON)

        return Geometry(
            polygons = listOf(polygon),
            propertyList = emptyList()
        )
    }

    private fun constructSinglePolygon(
        arcIndexes: List<Int>,
        topoJSON: TopoJSON
    ): PolygonF {
        val pathInAbsolutePoints = arcIndexes.flatMapIndexed { index: Int, arcIndex: Int ->
            val arc = constructArcFromIndex(topoJSON, arcIndex)
            if (index != 0) {
                arc.drop(1)
            } else {
                arc
            }
        }

        val polygon = PolygonF(
            pathInAbsolutePoints
                .applyTransform(topoJSON.transform)
                .toList()
        )
        return polygon
    }

    private fun List<PointF>.applyTransform(transform: Transform?): List<PointF> {
        val scaleX = transform?.scale?.first ?: 1f
        val scaleY = transform?.scale?.second ?: 1f
        val offsetX = transform?.translate?.first ?: 0f
        val offsetY = transform?.translate?.second ?: 0f

        return this.map { normalizedPoint ->
            PointF(
                normalizedPoint.x * scaleX + offsetX,
                normalizedPoint.y * scaleY + offsetY
            )
        }
    }

    private fun constructArcFromIndex(
        topoJSON: TopoJSON,
        index: Int
    ): Sequence<PointF> {
        val pathInArc = (topoJSON.arcs as MultiArc).arcs[index] as MultiArc
        val pathInDeltaPoints = pathInArc.arcs
            .asSequence()
            .filterIsInstance<PositionArc>()
            .map { position ->
                PointF(position.x.toFloat(), position.y.toFloat())
            }

        return pathInDeltaPoints.scan(PointF.DEFAULT) { accPoint, delta ->
            val endX = accPoint.x + delta.x
            val endY = accPoint.y + delta.y

            PointF(endX, endY)
        }.drop(1)
    }
}
