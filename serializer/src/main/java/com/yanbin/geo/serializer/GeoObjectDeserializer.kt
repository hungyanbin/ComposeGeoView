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
            "LineString" -> parseLineString(
                jsonObject,
                properties
            )
            "Polygon" -> parsePolygon(
                jsonObject,
                properties
            )
            "MultiPolygon" -> parseMultiPolygon(
                jsonObject,
                properties
            )
            "GeometryCollection" -> parseGeometryCollection(
                jsonObject,
                properties
            )
            // MultiPoint and MultiLineString not implemented yet
            else -> TODO()
        }
    }

    private fun parseGeometryCollection(
        jsonObject: JsonObject,
        properties: Map<String, String>
    ): GeoObject {
        val rawGeometries: JsonArray = jsonObject["geometries"]?.jsonArray ?: throw SerializationException("Should have geometries for GeometryCollection")

        val geometries = rawGeometries.map { jsonElement ->
            parseGeoObject(jsonElement.jsonObject)
        }

        return GeoObject(
            type = GeoType.GeometryCollection,
            properties = properties,
            geometries = geometries
        )
    }

    private fun parsePoint(jsonObject: JsonObject, properties: Map<String, String>): GeoObject {
        val rawCoordinates = jsonObject["coordinates"]?.jsonArray ?: throw SerializationException("Should have coordinates for Point")
        require(rawCoordinates.size == 2)

        val coordinates = rawCoordinates[0].jsonPrimitive.float to rawCoordinates[1].jsonPrimitive.float
        return GeoObject(
            type = GeoType.Point,
            properties = properties,
            coordinates = coordinates)
    }

    private fun parseLineString(jsonObject: JsonObject, properties: Map<String, String>): GeoObject {
        val arcIndexes = parseArcIndexes(jsonObject, GeoType.LineString)
        return GeoObject(
            type = GeoType.LineString,
            properties = properties,
            arcIndex = arcIndexes
        )
    }

    private fun parsePolygon(jsonObject: JsonObject, properties: Map<String, String>): GeoObject {
        val arcIndexes = parseArcIndexes(jsonObject, GeoType.Polygon)
        return GeoObject(
            type = GeoType.Polygon,
            properties = properties,
            arcIndex = arcIndexes
        )
    }

    private fun parseMultiPolygon(jsonObject: JsonObject, properties: Map<String, String>): GeoObject {
        val arcIndexes = parseArcIndexes(jsonObject, GeoType.MultiPolygon)
        return GeoObject(
            type = GeoType.MultiPolygon,
            properties = properties,
            arcIndex = arcIndexes
        )
    }

    private fun parseArcIndexes(jsonObject: JsonObject, type: GeoType): ArcIndex {
        val rawArcs = jsonObject["arcs"]?.jsonArray
            ?: throw SerializationException("Should have arcs for $type")
        val arcIndexes =
            ArcIndexDeserializer.deserialize(
                rawArcs
            )
        return arcIndexes
    }
}