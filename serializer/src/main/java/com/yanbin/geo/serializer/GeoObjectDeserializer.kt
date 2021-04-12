package com.yanbin.geo.serializer

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.*

internal object GeoObjectDeserializer: JsonElementDeserializer<GeoObject> {

    override fun deserialize(jsonElement: JsonElement): GeoObject {
        return parseGeoObject(jsonElement.jsonObject)
    }

    private fun parseGeoObject(jsonObject: JsonObject): GeoObject {
        val type = jsonObject["type"]?.jsonPrimitive?.content

        val properties: Map<String, String> = jsonObject["properties"]?.let{
            it.jsonObject
                .mapValues {(_, value) ->
                    value.toString()
                }
        } ?: mapOf()

        return when(type) {
            "Point" -> parsePoint(jsonObject, properties)
//            "Polygon" -> parsePolygon(
//                jsonObject,
//                properties
//            )
//            "LineString" -> parseGeoLineString(
//                jsonObject,
//                properties
//            )
//            "MultiPolygon" -> parseMultiPolygon(
//                jsonObject,
//                properties
//            )
            else -> TODO()
        }
    }

    private fun parsePoint(jsonObject: JsonObject, properties: Map<String, String>): GeoObject {
        val rawCoordinates = jsonObject["coordinates"]?.jsonArray ?: throw SerializationException("Should have coordinates for GeoObject.Point")
        require(rawCoordinates.size == 2)

        val coordinates = rawCoordinates[0].jsonPrimitive.float to rawCoordinates[1].jsonPrimitive.float
        return GeoObject(GeoType.Point, properties, coordinates)
    }
}