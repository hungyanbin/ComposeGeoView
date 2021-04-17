package com.yanbin.geo.geoview

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.yanbin.geo.core.PolygonF

@Composable
fun Polygon(
    modifier: Modifier = Modifier,
    data: PolygonF,
    color: Color = Color.Blue
) {
    Canvas(modifier) {
        data.contour.pairewise().forEach { (startPoint, endPoint) ->
            drawLine(
                start = Offset(startPoint.x, startPoint.y),
                end = Offset(endPoint.x, endPoint.y),
                color = color
            )
        }
    }
}
