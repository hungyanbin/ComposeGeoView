package com.yanbin.geo.geoview

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput

data class Coordinate(
    val scale: Float,
    val longitude: Float ,
    val latitude: Float
) {
    companion object {
        val TAIWAN = Coordinate(400f, 121f, 24f)
    }
}

val CoordinateLocal = compositionLocalOf<Coordinate> {
    error("No Coordinate provided")
}

// TODO should implement by using PointerInputScope
@Composable
fun TransformableCoordinate(
    defaultCoordinate: Coordinate = Coordinate.TAIWAN,
    content: @Composable () -> Unit
) {
    var coordinateState by remember { mutableStateOf(defaultCoordinate) }
    Box(
        Modifier
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consumeAllChanges()
                    val newLongitude = coordinateState.longitude - dragAmount.x/coordinateState.scale
                    val newLatitude = coordinateState.latitude + dragAmount.y/coordinateState.scale
                    coordinateState = coordinateState.copy(longitude = newLongitude, latitude = newLatitude)
                }
            }
            .fillMaxSize()
    ) {
        CompositionLocalProvider(
            CoordinateLocal provides coordinateState
        ) {
            content()
        }
    }
}
