package ui.climbingArea

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dao.api.ApiClimbingSpot
import ui.climbin.EditClimbingAreaDialog
import ui.climbin.GridClimbingAreas
import webScraper.OutdorSpotsRoutes.ClimbingRoute
import webScraper.OutdorSpotsRoutes.ClimbingSpot
import webScraper.OutdorSpotsRoutes.RouteType


@Preview
@Composable
fun ListClimbingAreas() {
    val dao = ApiClimbingSpot()
    var selectedArea by remember {mutableStateOf<ClimbingSpot?>(null)}
    var climbingSpots by remember { mutableStateOf(dao.getAll() ?: emptyList()) }
    var filteredSpots by remember { mutableStateOf(dao.getAll() ?: emptyList()) }
    println(climbingSpots)
    var filterText by remember { mutableStateOf("") }
    var isDialogOpen by remember { mutableStateOf(false) }
    var minRoutes by remember { mutableStateOf(0f) }
    var minRoutesText by remember { mutableStateOf(minRoutes.toInt().toString()) }
    fun refreshClimbingSpots() {
        climbingSpots = dao.getAll() ?: emptyList()
        filteredSpots = climbingSpots.filter{ spot ->
            val nameMatch = spot.name.contains(filterText, ignoreCase = true)
            val routeCountMatch = spot.routes.size >= minRoutes.toInt()
            return@filter nameMatch && routeCountMatch

        }
    }
    fun filterSpots() {
        filteredSpots = climbingSpots.filter{ spot ->
            val nameMatch = spot.name.contains(filterText, ignoreCase = true)
            val routeCountMatch = spot.routes.size >= minRoutes.toInt()
            return@filter nameMatch && routeCountMatch

        }
    }


    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = filterText,
            onValueChange = {
                filterText = it
                filteredSpots = climbingSpots.filter{ spot ->
                    val nameMatch = spot.name.contains(filterText, ignoreCase = true)
                    val routeCountMatch = spot.routes.size >= minRoutes.toInt()
                    return@filter nameMatch && routeCountMatch

                }
            },
            label = { Text("Filter") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text("Minimum number of routes: ${minRoutes.toInt()}")

        Slider(
            value = minRoutes,
            onValueChange = {
                val snapped = it.toInt().toFloat()
                minRoutes = snapped
                minRoutesText = it.toInt().toString()
                filteredSpots = climbingSpots.filter{ spot ->
                    val nameMatch = spot.name.contains(filterText, ignoreCase = true)
                    val routeCountMatch = spot.routes.size >= minRoutes.toInt()
                    return@filter nameMatch && routeCountMatch
                }
            },
            valueRange = 0f..100f,
            steps = 19,
            modifier = Modifier.fillMaxWidth()
        )


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
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
                        minRoutes = num.toFloat().coerceIn(0f, 400f)
                    }
                    refreshClimbingSpots()
                },
                modifier = Modifier.height(50.dp).width(60.dp), // mali input
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        GridClimbingAreas(
            climbingAreas = filteredSpots,
            onCardClick = { area ->
                println("Card clicked: $area")
                selectedArea = area
                isDialogOpen = true
            },
            onDeleteClick = { area ->
                println("Delete clicked: $area")
                dao.delete(area)
                refreshClimbingSpots()
            }
        )

    }
    if(isDialogOpen && selectedArea != null) {
        EditClimbingAreaDialog(
            area = selectedArea!!,
            onDismiss = { isDialogOpen = false },
            onSave={ updatedArea ->
                dao.update(updatedArea)
                refreshClimbingSpots()
                isDialogOpen = false
            }
        )
    }


    //Text("List", modifier = Modifier.padding(8.dp))
}


