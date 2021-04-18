package com.yanbin.geo.geoview

import androidx.compose.foundation.Canvas
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.withTransform
import com.yanbin.geo.core.PolygonF

@Composable
fun Polygon(
    modifier: Modifier = Modifier,
    data: PolygonF,
    color: Color = MaterialTheme.colors.primary
) {
    val scale = CoordinateLocal.current.scale
    val translateX = -CoordinateLocal.current.longitude
    val translateY = CoordinateLocal.current.latitude

    Canvas(modifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        withTransform({
            // Why reversed order???
            scale(scale)
            translate(canvasWidth / 2 + translateX, canvasHeight / 2 + translateY)
        }) {
            data.contour.pairewise().forEach { (startPoint, endPoint) ->
                drawLine(
                    start = Offset(startPoint.x, -startPoint.y),
                    end = Offset(endPoint.x, -endPoint.y),
                    color = color
                )
            }
        }
    }
}
