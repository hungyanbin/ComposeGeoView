package com.example.composegeoview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.composegeoview.ui.theme.ComposeGeoViewTheme
import com.yanbin.geo.converter.TopoJSONConverter
import com.yanbin.geo.geoview.Polygon

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeGeoViewTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Yanbin")
                    GeoDemo()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeGeoViewTheme {
        Greeting("Android")
    }
}

@Composable
fun GeoDemo() {
    val data = """
                {
                  "type": "Topology",
                  "objects": {
                    "example": {
                      "type": "GeometryCollection",
                      "geometries": [
                        {   
                          "type": "Polygon",
                          "arcs": [[1]]
                        }
                      ]
                    }
                  },
                  "arcs": [
                    [[400, 0], [199, 999], [200, -999], [200, 999]],
                    [[10, 10], [0, 999], [300, 0], [0, -999], [-300, 0]]
                  ]
                }
    """.trimIndent()

    val model = TopoJSONConverter().fromString(data)

    model.forEach { geometry ->
        geometry.polygons.forEach { polygonF ->
            Polygon(data = polygonF)
        }
    }
}
