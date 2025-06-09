package ui.climbingArea

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dao.api.ApiClimbingSpot
import webScraper.OutdorSpotsRoutes.ClimbingSpot

@Composable
fun AddClimbingArea(
    dao: ApiClimbingSpot = ApiClimbingSpot(),
    onAddClimbingSpot: (ClimbingSpot) -> Unit = {it -> println("onAddClimbingSpot: ${it}")},
    onNavigateToDefault: () -> Unit= {println("navigateToDefault")},
) {
    var name by remember { mutableStateOf("") }
    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Add New Climbing Area", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = latitude,
            onValueChange = { latitude = it },
            label = { Text("Latitude") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = longitude,
            onValueChange = { longitude = it },
            label = { Text("Longitude") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val lat = latitude.toDoubleOrNull()
                val lng = longitude.toDoubleOrNull()

                if (lat != null && lng != null && name.isNotBlank()) {
                    val coordinates = Pair(lat, lng)
                    val newSpot = ClimbingSpot(name=name, coordinates = coordinates, )
                    println("Attempting to add: $newSpot")

                    if (dao.insert(newSpot)) {
                        println("Insert successful")
                        onAddClimbingSpot(newSpot)
                        onNavigateToDefault() // Navigate to default page
                        name = ""
                        latitude = ""
                        longitude = ""
                    } else {
                        println("Insert failed")
                    }
                } else {
                    println("Invalid input: name='$name', latitude='$latitude', longitude='$longitude'")
                }
            },
            colors = ButtonDefaults.buttonColors(Color(0xFF01579B))
        ) {
            Text("Add Area", color = Color.White)
        }
    }
    //Text("Add Area", modifier = Modifier.padding(8.dp))
}
