package ui.climbingCenter
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dao.InnerClimbingCenterDao
import webScraper.InnerClimbingCenter.InnerClimbingCenter

@Preview
@Composable
fun ListClimbingCenter(
    dao: InnerClimbingCenterDao
) {
    var climbingCenters by remember { mutableStateOf(dao.getAll() ?: emptyList()) }
    var selectedCenter by remember { mutableStateOf<InnerClimbingCenter?>(null) }
    var isDialogOpen by remember { mutableStateOf(false) }
    var filterText by remember { mutableStateOf("") }
    var filterByMoonboard by remember { mutableStateOf(false) }
    var filterByBoulders by remember { mutableStateOf(false) }
    var filterByRoutes by remember { mutableStateOf(false) }
    var filterBySprayWall by remember { mutableStateOf(false) }
    var filterByKilter by remember { mutableStateOf(false) }

    fun refreshCenters() {
        climbingCenters = dao.getAll() ?: emptyList()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = filterText,
            onValueChange = {
                filterText = it
            },
            label = { Text("Filter") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

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
            climbingCenters = climbingCenters.filter {
                (!filterByMoonboard || it.hasMoonboard) &&
                        (!filterByBoulders || it.hasBoulders) &&
                        (!filterByRoutes || it.hasRoutes) &&
                        (!filterBySprayWall || it.hasSprayWall) &&
                        (!filterByKilter || it.hasKilter) &&
                        (it.name.contains(filterText, true) ||
                                it.latitude.toString().contains(filterText, true) ||
                                it.longitude.toString().contains(filterText, true))
            },
            onCardClick = { center ->
                println("Card clicked: $center") // Debugging
                selectedCenter = center
                isDialogOpen = true
            },
            onDeleteClick = { centerToDelete ->
                dao.delete(centerToDelete)
                refreshCenters()
            }
        )
    }

    if (isDialogOpen && selectedCenter != null) {
        println("sdads")
        EditClimbingCenterDialog(
            center = selectedCenter!!,
            onDismiss = { isDialogOpen = false },
            onSave = { updatedCenter ->
                dao.update(updatedCenter)
                refreshCenters()
                isDialogOpen = false
            }
        )
    }
}
