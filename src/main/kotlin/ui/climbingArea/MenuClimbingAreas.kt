package ui.climbingArea

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.climbingCenter.ClimbingCenterState

enum class ClimbingAreaState { ADD_AREAS, AREAS, AREAS_SCRAPER, AREAS_GENERATOR }
@Composable
fun ClimbingAreas() {
    Row(Modifier.fillMaxSize()) {
        //levi meni
        var menuState by remember {mutableStateOf(ClimbingAreaState.AREAS)}
        Column(
            modifier = Modifier
                .width(200.dp)
                .fillMaxHeight()
                .background(Color(0xFFEEEEEE)),
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (menuState == ClimbingAreaState.AREAS) Color(0xFFD6D6D6) else Color.Transparent)
                    .clickable { menuState = ClimbingAreaState.AREAS }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    "Areas",
                    modifier = Modifier.padding(8.dp).fillMaxWidth())

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (menuState == ClimbingAreaState.ADD_AREAS) Color(0xFFD6D6D6) else Color.Transparent)
                    .clickable { menuState = ClimbingAreaState.ADD_AREAS }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    "Add Area",
                    modifier = Modifier.padding(8.dp).fillMaxWidth())

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (menuState == ClimbingAreaState.AREAS_SCRAPER) Color(0xFFD6D6D6) else Color.Transparent)
                    .clickable { menuState = ClimbingAreaState.AREAS_SCRAPER }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    "Scraper",
                    modifier = Modifier.padding(8.dp).fillMaxWidth())

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (menuState == ClimbingAreaState.AREAS_GENERATOR) Color(0xFFD6D6D6) else Color.Transparent)
                    .clickable { menuState = ClimbingAreaState.AREAS_GENERATOR }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    "Generator",
                    modifier = Modifier.padding(8.dp).fillMaxWidth())

            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            when (menuState) {
                ClimbingAreaState.ADD_AREAS -> {
                    AddClimbingArea(
                        onNavigateToDefault = { menuState = ClimbingAreaState.AREAS }
                    )
                }

                ClimbingAreaState.AREAS -> {
                    ListClimbingAreas()
                }

                ClimbingAreaState.AREAS_SCRAPER -> {
                    ScrapeClimbingArea(
                        onAddAreas = {println("onAddAreas")},
                    )
                }

                ClimbingAreaState.AREAS_GENERATOR -> {
                    GenerateClimbingArea()
                }
            }
        }
    }
}

