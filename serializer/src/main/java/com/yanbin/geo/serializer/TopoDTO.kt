package com.yanbin.geo.serializer

import kotlinx.serialization.Serializable

@Serializable(with = ArcsSerializer::class)
internal sealed class Arc

internal class MultiArc(val arcs: List<Arc>):
    Arc()
internal class PositionArc(val x: Int, val y: Int):
    Arc()

@Serializable(with = ArcIndexSerializer::class)
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

@Serializable(with = TransformSerializer::class)
internal class Transform(
    val scale: Pair<Float, Float>,
    val translate: Pair<Float, Float>
)

@Serializable(with = GeoObjectSerializer::class)
internal sealed class GeoObject(val properties: Map<String, String>) {
    class GeoCollection(
        val name: String,
        val geometries: List<GeoObject>
    ): GeoObject(emptyMap())

    class LineString(
        val arcIndex: ArcIndex,
        properties: Map<String, String>
    ): GeoObject(properties)

    class Point(
        val coordinates: Pair<Float, Float>,
        properties: Map<String, String>
    ): GeoObject(properties)

    class Polygon(
        val arcIndex: ArcIndex,
        properties: Map<String, String>
    ): GeoObject(properties)

    class MultiPolygon(
        val arcIndex: ArcIndex,
        properties: Map<String, String>
    ): GeoObject(properties)
}
