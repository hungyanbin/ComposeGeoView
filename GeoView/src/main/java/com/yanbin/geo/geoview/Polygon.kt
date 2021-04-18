package com.yanbin.geo.geoview

import androidx.compose.foundation.Canvas
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.withTransform
import com.yanbin.geo.core.PolygonF

@Composable
fun OutlinedPolygon(
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
            val contour = data.contour
            val path = Path().apply {
                moveTo(contour[0].x, -contour[0].y)
                contour.drop(1)
                    .forEach { (x, y) ->
                        lineTo(x, -y)
                    }
            }

            drawPath(
                path = path,
                color = color,
                style = Stroke()
            )
        }
    }
}

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
            val contour = data.contour
            val path = Path().apply {
                moveTo(contour[0].x, -contour[0].y)
                contour.drop(1)
                    .forEach { (x, y) ->
                        lineTo(x, -y)
                    }
            }

            drawPath(
                path = path,
                color = color
            )
        }
    }
}
