package ui.climbingArea

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dao.api.ApiClimbingSpot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ui.climbin.EditClimbingAreaDialog
import ui.climbin.GridClimbingAreas
import webScraper.OutdorSpotsRoutes.ClimbingSpot
import webScraper.OutdorSpotsRoutes.LeadClimbingScraper

@Composable
fun ScrapeClimbingArea(
    onAddAreas: (List<ClimbingSpot>) -> Unit
) {
    var spots by remember { mutableStateOf<List<ClimbingSpot>>(emptyList()) }
    var filteredSpots by remember { mutableStateOf<List<ClimbingSpot>>(emptyList()) }
    var filterText by remember { mutableStateOf("") }
    var selectedSpot by remember { mutableStateOf<ClimbingSpot?>(null) }
    var isDialogOpen by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }

    val dao = ApiClimbingSpot()

    LaunchedEffect(Unit) {
        isLoading = true
        spots = withContext(Dispatchers.IO) {
            LeadClimbingScraper().scrapeAllClimbingSpots()
        }
        filteredSpots = spots
        isLoading = false
    }

    LaunchedEffect(filterText) {
        filteredSpots = spots.filter {
            it.name.contains(filterText, ignoreCase = true) ||
                    it.coordinates.first.toString().contains(filterText, ignoreCase = true) ||
                    it.coordinates.second.toString().contains(filterText, ignoreCase = true)
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color(0xFF0288D1))
        }
    } else {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(text = "Filtered areas: ${filteredSpots.size}")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = filterText,
                    onValueChange = { filterText = it },
                    label = { Text("Filter Climbing Areas") },
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                )
                Button(
                    onClick = {
                        val added = spots.filter { dao.insert(it) }
                        onAddAreas(added)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0288D1))
                ) {
                    Text("Add All Areas", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ⬇️ Reuse your styled UI
            GridClimbingAreas (
                climbingAreas = filteredSpots,
                onCardClick = {
                    selectedSpot = it
                    isDialogOpen = true
                },
                onDeleteClick = { toDelete ->
                    spots = spots.filter { it != toDelete }
                    filteredSpots = filteredSpots.filter { it != toDelete }
                }
            )
        }

        if (isDialogOpen && selectedSpot != null) {
            EditClimbingAreaDialog(
                area = selectedSpot!!,
                onDismiss = { isDialogOpen = false },
                onSave = { updatedSpot ->
                    spots = spots.map { if (it == selectedSpot) updatedSpot else it }
                    filteredSpots = filteredSpots.map { if (it == selectedSpot) updatedSpot else it }
                    isDialogOpen = false
                }
            )
        }
    }
}

