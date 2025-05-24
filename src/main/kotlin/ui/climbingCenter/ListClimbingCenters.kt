package ui.climbingCenter
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import webScraper.InnerClimbingCenter.InnerClimbingCenter

@Preview
@Composable
fun ListClimbingCenter(
    climbingCenters: List<InnerClimbingCenter>,
    onUpdate: (List<InnerClimbingCenter>) -> Unit
) {
    var selectedCenter by remember { mutableStateOf<InnerClimbingCenter?>(null) }
    var isDialogOpen by remember { mutableStateOf(false) }
    var filteredClimbingCenters by remember { mutableStateOf(climbingCenters) }
    var filterText by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = filterText,
                onValueChange = {
                    filterText = it
                    filteredClimbingCenters = filterClimbingCenters(climbingCenters, filterText)
                },
                label = { Text("Filter") },
                modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .clickable {
                        // TODO("Shranjevanje v bazo")
                    }
                    .background(Color(0xFF0288D1), shape = MaterialTheme.shapes.medium)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Save to database",
                    color = Color.White
                )
            }
        }
        GridClimbingCenters(
            climbingCenters = filteredClimbingCenters,
            onCardClick = { center ->
                selectedCenter = center
                isDialogOpen = true
            },
            onDeleteClick = { centerToDelete ->
                val updatedCenters = climbingCenters.filter { it != centerToDelete }
                onUpdate(updatedCenters)
            }
        )
    }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = filterText,
                onValueChange = {
                    filterText = it
                    filteredClimbingCenters = filterClimbingCenters(climbingCenters, filterText)
                },
                label = { Text("Filter") },
                modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .clickable {
                        // TODO("Shranjevanje v bazo")
                    }
                    .background(Color(0xFF0288D1), shape = MaterialTheme.shapes.medium)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Save to database",
                    color = Color.White
                )
            }
        }
        GridClimbingCenters(
            climbingCenters = filteredClimbingCenters,
            onCardClick = { center ->
                selectedCenter = center
                isDialogOpen = true
            },
            onDeleteClick = { centerToDelete ->
                val updatedCenters = climbingCenters.filter { it != centerToDelete }
                onUpdate(updatedCenters)
                filteredClimbingCenters = filterClimbingCenters(updatedCenters, filterText)
            }
        )
    }

    if (isDialogOpen && selectedCenter != null) {
        EditClimbingCenterDialog(
            center = selectedCenter!!,
            onDismiss = { isDialogOpen = false },
            onSave = { updatedCenter ->
                val updatedList = climbingCenters.map {
                    if (it == selectedCenter) updatedCenter else it
                }
                onUpdate(updatedList)
                isDialogOpen = false
            }
        )
    }

    if (isDialogOpen && selectedCenter != null) {
        EditClimbingCenterDialog(
            center = selectedCenter!!,
            onDismiss = { isDialogOpen = false },
            onSave = { updatedCenter ->
                val updatedList = climbingCenters.map {
                    if (it == selectedCenter) updatedCenter else it
                }
                onUpdate(updatedList)
                isDialogOpen = false
            }
        )
    }
}

fun filterClimbingCenters(climbingCenters: List<InnerClimbingCenter>, filterText: String): List<InnerClimbingCenter>{
    if (filterText == "" || climbingCenters.isEmpty()) return climbingCenters
    return climbingCenters.filter {it.name.contains(filterText, true) ||
            it.latitude.toString().contains(filterText, true) ||
            it.longitude.toString().contains(filterText, true)
    }
}
