package ui.climbingArea

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dao.api.ApiClimbingSpot
import ui.climbin.GridClimbingAreas


@Preview
@Composable
fun ListClimbingAreas() {
    val dao = ApiClimbingSpot()

    var climbingSpots by remember { mutableStateOf(dao.getAll() ?: emptyList()) }
    var filteredSpots by remember { mutableStateOf(dao.getAll() ?: emptyList()) }
    println(climbingSpots)
    var filterText by remember { mutableStateOf("") }

    var minRoutes by remember { mutableStateOf(0f) }
    var minRoutesText by remember { mutableStateOf(minRoutes.toInt().toString()) }
    fun refreshClimbingSpots() {
        climbingSpots = dao.getAll() ?: emptyList()
    }


    Column (modifier = Modifier.fillMaxSize().padding(16.dp)){
        OutlinedTextField(
            value = filterText,
            onValueChange = {
                filterText = it
            },
            label = { Text("Filter") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text("Minimum number of routes: ${minRoutes.toInt()}")

        Slider(
            value = minRoutes,
            onValueChange = {
                val snapped = it.toInt().toFloat()
                minRoutes = snapped
                minRoutesText = it.toInt().toString()
            },
            valueRange = 0f..10f,
            steps = 9,
            modifier = Modifier.fillMaxWidth()
        )


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Minimum number of routes:",
                modifier = Modifier.padding(end = 8.dp)
            )
            OutlinedTextField(
                value = minRoutesText,
                onValueChange = {
                    minRoutesText = it
                    it.toIntOrNull()?.let { num ->
                        minRoutes = num.toFloat().coerceIn(0f, 100f)
                    }
                },
                modifier = Modifier.height(50.dp).width(60.dp), // mali input
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.weight(1f)) {
            GridClimbingAreas(
                climbingAreas = filteredSpots
            )
        }

    }




    //Text("List", modifier = Modifier.padding(8.dp))
}
