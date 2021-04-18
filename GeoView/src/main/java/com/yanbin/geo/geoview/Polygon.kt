package com.yanbin.geo.geoview

import androidx.compose.foundation.Canvas
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.yanbin.geo.core.PolygonF

@Composable
fun Polygon(
    modifier: Modifier = Modifier,
    data: PolygonF,
    color: Color = MaterialTheme.colors.primary
) {
    val scale = 150
    val translateX = -118
    val translateY = 31
    Canvas(modifier) {
        data.contour.pairewise().forEach { (startPoint, endPoint) ->
            drawLine(
                start = Offset((startPoint.x + translateX) * scale, -(startPoint.y - translateY) * scale),
                end = Offset((endPoint.x + translateX) * scale, -(endPoint.y - translateY) * scale),
                color = color
            )
        }
    }
}
