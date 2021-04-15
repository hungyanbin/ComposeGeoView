package com.yanbin.geo.serializer

import kotlinx.serialization.Serializable

@Serializable(with = TopoJsonSerializer::class)
internal class TopoJSON(
    val type: String,
    val geoObject: GeoObject,
    val arcs: Arc,
    val transform: Transform?
)

internal sealed class Arc

internal class MultiArc(val arcs: List<Arc>):
    Arc()
internal class PositionArc(val x: Int, val y: Int):
    Arc()

internal sealed class ArcIndex

internal class ArcIntIndex(val indexes: List<Int>):
    ArcIndex() {
    override fun toString(): String {
        return indexes.toString()
    }
}
internal class ArcIndexList(val indexes: List<ArcIndex>):
    ArcIndex() {
    override fun toString(): String {
        return indexes.joinToString(", ") {
            it.toString()
        }
    }
}

internal class Transform(
    val scale: Pair<Float, Float>,
    val translate: Pair<Float, Float>
)

internal class GeoObject(
    val type: GeoType,
    val properties: Map<String, String>,
    val coordinates: Pair<Float, Float>? = null,
    val arcIndex: ArcIndex? = null,
    val geometries: List<GeoObject>? = null
)

internal enum class GeoType {
    Point, LineString, Polygon, MultiPolygon, GeometryCollection
}
