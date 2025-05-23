package ui.climbingCenter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import webScraper.InnerClimbingCenter.InnerClimbingCenter
import webScraper.InnerClimbingCenter.KspScraper

@Composable
fun ScrapeClimbingCenter(
    onAddCenters: (List<InnerClimbingCenter>) -> Unit
) {
    var centers by remember { mutableStateOf<List<InnerClimbingCenter>>(emptyList()) }
    var selectedCenter by remember { mutableStateOf<InnerClimbingCenter?>(null) }
    var isDialogOpen by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        isLoading = true
        centers = withContext(Dispatchers.IO) {
            KspScraper().getData1()
        }
        isLoading = false
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
           Box(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(bottom = 8.dp)
                    .clickable{
                        val newCenter = InnerClimbingCenter("New Center", 0.0, 0.0)
                        centers = centers + newCenter
                        onAddCenters(centers)
                    }
            ) {
                Text("Add")
            }

            GridClimbingCenters(
                climbingCenters = centers,
                onCardClick = { center ->
                    selectedCenter = center
                    isDialogOpen = true
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
                    isDialogOpen = false
                    onAddCenters(centers)
                }
            )
        }
    }
}
