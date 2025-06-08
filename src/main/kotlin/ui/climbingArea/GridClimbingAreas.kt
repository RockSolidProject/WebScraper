package ui.climbin


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import webScraper.OutdorSpotsRoutes.ClimbingRoute
import webScraper.OutdorSpotsRoutes.ClimbingSpot
import webScraper.OutdorSpotsRoutes.RouteType
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed


@Composable
fun GridClimbingAreas(
    modifier: Modifier = Modifier,
    climbingAreas: List<ClimbingSpot>,
    onCardClick: (ClimbingSpot) -> Unit = { println("onCardClick: $it") },
    onDeleteClick: (ClimbingSpot) -> Unit = { println("onDeleteClick: $it") },
) {

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 250.dp),
        modifier = Modifier
            .fillMaxSize()
            .height(2000.dp)
            .padding(16.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        items(climbingAreas) { area ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clickable { onCardClick(area) },
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = area.name ?: "unknown",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFF01579B),
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.Red,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable { onDeleteClick(area) }
                        )
                    }
                }
            }

        }
    }

}


@Composable
fun EditClimbingAreaDialog(
    area: ClimbingSpot,
    onDismiss: () -> Unit = {},
    onSave: (ClimbingSpot) -> Unit = {}
) {
    var mutableArea by remember { mutableStateOf(area) }
    var name by remember { mutableStateOf(area.name) }
    var latitude by remember { mutableStateOf(area.coordinates.first) }
    var longitude by remember { mutableStateOf(area.coordinates.second) }
    var routes by remember { mutableStateOf(area.routes.toMutableList()) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Edit Climbing Areas") },
        containerColor = Color(0xFFE1F5FE),
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = latitude.toString(),
                    onValueChange = {
                        val parsed = it.toDoubleOrNull()
                        if (parsed != null) {
                            latitude = parsed
                        }
                    },
                    label = { Text("Latitude") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = longitude.toString(),
                    onValueChange = {
                        val parsed = it.toDoubleOrNull()
                        if (parsed != null) {
                            longitude = parsed
                        }
                    },
                    label = { Text("Longitude") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp) // omeji višino, da omogoči scrollanje, lahko nastaviš poljubno
                ) {
                    itemsIndexed(routes) { index, route ->
                        EditableRouteRow(
                            route = route,
                            onUpdate = { updatedRoute ->
                                routes = routes.toMutableList().also { it[index] = updatedRoute }
                            },
                            onDelete = {
                                routes = routes.toMutableList().also { it.removeAt(index) }
                            }
                        )
                    }
                }
                Button(
                    onClick = {
                        routes = ((routes + ClimbingRoute(
                            name = "",
                            length = null,
                            type = RouteType.Lead
                        )).toMutableList())
                    },
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0288D1))
                ) {
                    Text("Add Route")
                }


            }
        },

        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(Color(0xFF01579B)),
                onClick = {
                    mutableArea.name = name
                    area.coordinates = Pair(latitude, longitude)
                    area.routes = routes
                    onSave(mutableArea)
                },
                modifier = Modifier,
            ) {
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

@Composable
fun EditableRouteRow(
    route: ClimbingRoute,
    onUpdate: (ClimbingRoute) -> Unit,
    onDelete: (() -> Unit)? = null
) {
    var name by remember { mutableStateOf(route.name ?: "") }
    var length by remember { mutableStateOf(route.length) }
    var type by remember { mutableStateOf(route.type) }

    // Refresh state if route changes externally
    LaunchedEffect(route) {
        name = route.name ?: ""
        length = route.length
        type = route.type
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                onUpdate(route.copy(name = name))
            },
            label = { Text("Name") },
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        OutlinedTextField(
            value = length?.toString() ?: "",
            onValueChange = {
                val len = it.toDoubleOrNull()
                length = len
                onUpdate(route.copy(length = length))
            },
            label = { Text("Length") },
            modifier = Modifier.width(100.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        DropdownMenuRouteType(
            selected = type,
            onTypeSelected = {
                type = it
                onUpdate(route.copy(type = type))
            }
        )

        if (onDelete != null) {
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
            }
        }
    }
}


@Composable
fun DropdownMenuRouteType(
    selected: RouteType,
    onTypeSelected: (RouteType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text(selected.name)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            RouteType.entries.forEach { type ->
                DropdownMenuItem(
                    text = { Text(type.name) },
                    onClick = {
                        onTypeSelected(type)
                        expanded = false
                    }
                )
            }
        }
    }
}
