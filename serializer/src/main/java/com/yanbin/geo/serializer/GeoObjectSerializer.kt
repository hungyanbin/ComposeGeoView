package com.yanbin.geo.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

internal object GeoObjectSerializer: KSerializer<GeoObject> {

    override fun deserialize(decoder: Decoder): GeoObject {
        val jsonDecoder = decoder as JsonDecoder

        return parseGeoObject(jsonDecoder.decodeJsonElement().jsonObject)
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

    private fun parsePoint(jsonObject: JsonObject, properties: Map<String, String>): GeoObject.Point {
        val rawCoordinates = jsonObject["coordinates"]?.jsonArray ?: throw SerializationException("Should have coordinates for GeoObject.Point")
        require(rawCoordinates.size == 2)

        val coordinates = rawCoordinates[0].jsonPrimitive.float to rawCoordinates[1].jsonPrimitive.float
        return GeoObject.Point(coordinates, properties)
    }

    override val descriptor: SerialDescriptor
        get() = serialDescriptor<GeoObject>()

    override fun serialize(encoder: Encoder, value: GeoObject) {
        throw UnsupportedOperationException("Do not need to serialize Arc in this project!")
    }
}