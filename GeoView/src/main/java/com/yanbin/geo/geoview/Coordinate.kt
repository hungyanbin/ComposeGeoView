package com.yanbin.geo.geoview

import androidx.compose.runtime.compositionLocalOf

class Coordinate(
    val scale: Float,
    val longitude: Float ,
    val latitude: Float
) {
    companion object {
        val TAIWAN = Coordinate(200f, 121f, 24f)
    }
}

val CoordinateLocal = compositionLocalOf<Coordinate> {
    error("No Coordinate provided")
}
