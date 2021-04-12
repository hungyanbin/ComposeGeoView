package com.yanbin.geo.core

class Geometry(
    val polygons: List<PolygonF>,
    propertyList: List<Property>
) {

    val properties = propertyList.map { property -> Pair(property.key, property.value) }
        .toMap()
}

