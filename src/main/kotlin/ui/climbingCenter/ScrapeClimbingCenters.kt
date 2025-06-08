package ui.climbingCenter
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dao.InnerClimbingCenterDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import webScraper.InnerClimbingCenter.InnerClimbingCenter
import webScraper.InnerClimbingCenter.KspScraper

@Composable
fun ScrapeClimbingCenter(
    dao: InnerClimbingCenterDao,
    onAddCenters: (List<InnerClimbingCenter>) -> Unit
) {
    var centers by remember { mutableStateOf<List<InnerClimbingCenter>>(emptyList()) }
    var filteredCenters by remember { mutableStateOf<List<InnerClimbingCenter>>(emptyList()) }
    var filterText by remember { mutableStateOf("") }
    var selectedCenter by remember { mutableStateOf<InnerClimbingCenter?>(null) }
    var isDialogOpen by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var filterByMoonboard by remember { mutableStateOf(false) }
    var filterByBoulders by remember { mutableStateOf(false) }
    var filterByRoutes by remember { mutableStateOf(false) }
    var filterBySprayWall by remember { mutableStateOf(false) }
    var filterByKilter by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isLoading = true
        centers = withContext(Dispatchers.IO) {
            KspScraper().getData1()
        }
        filteredCenters = centers
        isLoading = false
    }

    LaunchedEffect(filterText) {
        filteredCenters = centers.filter {
            it.name.contains(filterText, ignoreCase = true) ||
                    it.latitude.toString().contains(filterText, ignoreCase = true) ||
                    it.longitude.toString().contains(filterText, ignoreCase = true)
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = filterText,
                    onValueChange = { filterText = it },
                    label = { Text("Filter") },
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                )

                Box(
                    modifier = Modifier
                        .clickable {
                            val successfullyAdded = centers.filter { dao.insert(it) }
                            onAddCenters(successfullyAdded)
                        }
                        .background(Color(0xFF0288D1), shape = MaterialTheme.shapes.medium)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Add All Centers",
                        color = Color.White
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = filterByMoonboard,
                        onCheckedChange = { filterByMoonboard = it }
                    )
                    Text("Moonboard")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = filterByBoulders,
                        onCheckedChange = { filterByBoulders = it }
                    )
                    Text("Boulders")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = filterByRoutes,
                        onCheckedChange = { filterByRoutes = it }
                    )
                    Text("Routes")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = filterBySprayWall,
                        onCheckedChange = { filterBySprayWall = it }
                    )
                    Text("Spray Wall")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = filterByKilter,
                        onCheckedChange = { filterByKilter = it }
                    )
                    Text("Kilter")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            GridClimbingCenters(
                climbingCenters = filteredCenters.filter {
                    (!filterByMoonboard || it.hasMoonboard) &&
                            (!filterByBoulders || it.hasBoulders) &&
                            (!filterByRoutes || it.hasRoutes) &&
                            (!filterBySprayWall || it.hasSprayWall) &&
                            (!filterByKilter || it.hasKilter) &&
                            (it.name.contains(filterText, ignoreCase = true) ||
                                    it.latitude.toString().contains(filterText, ignoreCase = true) ||
                                    it.longitude.toString().contains(filterText, ignoreCase = true))
                },
                onCardClick = { center ->
                    selectedCenter = center
                    isDialogOpen = true
                },
                onDeleteClick = { centerToDelete ->
                    centers = centers.filter { it != centerToDelete }
                    filteredCenters = filteredCenters.filter { it != centerToDelete }
                }
            )
        }

        if (isDialogOpen && selectedCenter != null) {
            EditClimbingCenterDialog(
                center = selectedCenter!!,
                onDismiss = { isDialogOpen = false },
                onSave = { updatedCenter ->
                    centers = centers.map {
                        if (it == selectedCenter) updatedCenter else it
                    }
                    filteredCenters = filteredCenters.map {
                        if (it == selectedCenter) updatedCenter else it
                    }
                    isDialogOpen = false
                }
            )
        }
    }
}
