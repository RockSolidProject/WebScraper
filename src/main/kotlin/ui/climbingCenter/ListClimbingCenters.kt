package ui.climbingCenter
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import webScraper.InnerClimbingCenter.InnerClimbingCenter

@Preview
@Composable
fun ListClimbingCenter(
    climbingCenters: List<InnerClimbingCenter>,
    onUpdate: (List<InnerClimbingCenter>) -> Unit
) {
    var selectedCenter by remember { mutableStateOf<InnerClimbingCenter?>(null) }
    var isDialogOpen by remember { mutableStateOf(false) }



    GridClimbingCenters(climbingCenters, onCardClick = { center ->
        selectedCenter = center
        isDialogOpen = true
    })

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

@Composable
fun GridClimbingCenters(
    climbingCenters: List<InnerClimbingCenter>,
    onCardClick: (InnerClimbingCenter) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(climbingCenters) { center ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clickable { onCardClick(center) },
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE1F5FE)
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                border = BorderStroke(1.dp, Color(0xFF0288D1))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = center.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFF01579B)
                    )

                    Column {
                        Text(
                            text = "Lat: ${center.latitude}",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.DarkGray,
                            fontSize = 15.sp
                        )
                        Text(
                            text = "Lng: ${center.longitude}",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.DarkGray,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun EditClimbingCenterDialog(
    center: InnerClimbingCenter,
    onDismiss: () -> Unit,
    onSave: (InnerClimbingCenter) -> Unit
) {
    var name by remember { mutableStateOf(center.name) }
    var latitude by remember { mutableStateOf(center.latitude.toString()) }
    var longitude by remember { mutableStateOf(center.longitude.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Climbing Center") },
        containerColor = Color(0xFFE1F5FE),
        text = {
            Column {
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
            }
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(Color(0xFF01579B)),
                onClick = {
                val updatedCenter = InnerClimbingCenter(
                    name = name,
                    latitude = latitude.toDoubleOrNull() ?: center.latitude,
                    longitude = longitude.toDoubleOrNull() ?: center.longitude
                )
                onSave(updatedCenter)
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(Color(0xFF01579B)),
                ) {
                Text("Cancel")
            }
        }
    )
}