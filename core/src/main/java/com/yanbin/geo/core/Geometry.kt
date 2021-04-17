package com.yanbin.geo.core

class Geometry(
    val polygons: List<PolygonF>,
    propertyList: List<Property>
) {

    val properties = propertyList.map { property -> Pair(property.key, property.value) }
        .toMap()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Geometry

        if (polygons != other.polygons) return false
        if (properties != other.properties) return false

        return true
    }

    override fun hashCode(): Int {
        var result = polygons.hashCode()
        result = 31 * result + properties.hashCode()
        return result
    }

    override fun toString(): String {
        return "Geometry(polygons=$polygons, properties=$properties)"
    }
}
