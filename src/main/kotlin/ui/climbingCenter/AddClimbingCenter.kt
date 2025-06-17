package ui.climbingCenter

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dao.InnerClimbingCenterDao
import webScraper.InnerClimbingCenter.InnerClimbingCenter

@Composable
fun AddClimbingCenter(
    dao: InnerClimbingCenterDao,
    onAddClimbingCenter: (InnerClimbingCenter) -> Unit,
    onNavigateToDefault: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Add New Climbing Center", style = MaterialTheme.typography.titleLarge)

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
                    val newCenter = InnerClimbingCenter(name, lat, lng)
                    println("Attempting to add: $newCenter")

                    if (dao.insert(newCenter)) {
                        println("Insert successful")
                        onAddClimbingCenter(newCenter)
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
            Text("Add Center", color = Color.White)
        }
    }
}