package ui.climbin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dao.api.ApiClimbingSpot
import webScraper.OutdorSpotsRoutes.ClimbingSpot
import webScraper.OutdorSpotsRoutes.ClimbingRoute
import webScraper.OutdorSpotsRoutes.RouteType
import java.util.*
import kotlin.random.Random

@Composable
fun GenerateClimbingAreas(
    onGenerate: (List<ClimbingSpot>) -> Unit
) {
    val dao = ApiClimbingSpot()
    var itemCount by remember { mutableStateOf("") }
    var minLatitude by remember { mutableStateOf("") }
    var maxLatitude by remember { mutableStateOf("") }
    var minLongitude by remember { mutableStateOf("") }
    var maxLongitude by remember { mutableStateOf("") }
    var routeNumber by remember { mutableStateOf("0") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = itemCount,
            onValueChange = { itemCount = it },
            label = { Text("Number of areas to generate") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = minLatitude,
            onValueChange = { minLatitude = it },
            label = { Text("Min Latitude") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = maxLatitude,
            onValueChange = { maxLatitude = it },
            label = { Text("Max Latitude") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = minLongitude,
            onValueChange = { minLongitude = it },
            label = { Text("Min Longitude") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = maxLongitude,
            onValueChange = { maxLongitude = it },
            label = { Text("Max Longitude") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = routeNumber,
            onValueChange = { routeNumber = it },
            label = { Text("Route Number") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        Button(
            onClick = {
                val generated = generateClimbingAreas(
                    number = itemCount.toIntOrNull() ?: 0,
                    minLatitude = minLatitude.toDoubleOrNull(),
                    maxLatitude = maxLatitude.toDoubleOrNull(),
                    minLongitude = minLongitude.toDoubleOrNull(),
                    maxLongitude = maxLongitude.toDoubleOrNull(),
                    routeNumber = routeNumber.toIntOrNull(),

                )
                val successfullyAdded = generated.filter {dao.insert(it)}
                onGenerate(generated)
            },
            colors = ButtonDefaults.buttonColors(Color(0xFF01579B)),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Generate")
        }
    }
}

fun generateClimbingAreas(
    number: Int,
    minLatitude: Double? = null,
    maxLatitude: Double? = null,
    minLongitude: Double? = null,
    maxLongitude: Double? = null,
    routeNumber: Int? = null,

): List<ClimbingSpot> {
    val random = Random(System.currentTimeMillis())
    val finalMinLat = minLatitude ?: 45.7
    val finalMaxLat = maxLatitude ?: 46.3
    val finalMinLon = minLongitude ?: 13.9
    val finalMaxLon = maxLongitude ?: 15.6
    val finalRouteNumber = routeNumber ?: 5


    val generated = mutableListOf<ClimbingSpot>()

    repeat(number) { index ->
        val name = "Generated Area (${index + 1})" // <-- tukaj dodaš zaporedno številko

        val lat = finalMinLat + (finalMaxLat - finalMinLat) * random.nextDouble()
        val lon = finalMinLon + (finalMaxLon - finalMinLon) * random.nextDouble()
        val routes = List(finalRouteNumber) { routeIndex ->
            ClimbingRoute(
                name = "Route ${routeIndex + 1}",
                length = listOf(10.0, 20.0, 30.0, null).random(),
                type = RouteType.entries.random()
            )
        }

        generated.add(
            ClimbingSpot(
                name = name,
                coordinates = Pair(lat, lon),
                routes = routes.toMutableList()
            )
        )
    }

    return generated
}
