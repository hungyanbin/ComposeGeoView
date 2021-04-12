package com.yanbin.geo.serializer

import kotlinx.serialization.Serializable

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
    val coordinates: Pair<Float, Float>) {
//    class GeoCollection(
//        val name: String,
//        val geometries: List<GeoObject>
//    ): GeoObject(emptyMap())
//
//    class LineString(
//        val arcIndex: ArcIndex,
//        properties: Map<String, String>
//    ): GeoObject(properties)

//    class Polygon(
//        val arcIndex: ArcIndex,
//        properties: Map<String, String>
//    ): GeoObject(properties)
//
//    class MultiPolygon(
//        val arcIndex: ArcIndex,
//        properties: Map<String, String>
//    ): GeoObject(properties)
}

internal enum class GeoType {
    Point, LineString
}
