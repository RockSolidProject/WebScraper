package ui.climbingCenter

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

enum class ClimbingCenterState { ADD_CENTER, CENTERS, CENTERS_SCRAPER, CENTERS_GENERATOR }

@Composable
fun ClimbingCenter() {
    var menuState by remember { mutableStateOf(ClimbingCenterState.ADD_CENTER) }
    Row(Modifier.fillMaxSize()) {
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
                    .clickable { menuState = ClimbingCenterState.CENTERS }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    "Centers",
                    modifier = Modifier.padding(8.dp).fillMaxWidth())

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { menuState = ClimbingCenterState.ADD_CENTER }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    "Add Center",
                    modifier = Modifier.padding(8.dp).fillMaxWidth())

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { menuState = ClimbingCenterState.CENTERS_SCRAPER }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    "Scraper",
                    modifier = Modifier.padding(8.dp).fillMaxWidth())

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { menuState = ClimbingCenterState.CENTERS_GENERATOR }
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
                ClimbingCenterState.ADD_CENTER -> {
                    AddClimbingCenter()
                }

                ClimbingCenterState.CENTERS -> {
                    ListClimbingCenter()
                }

                ClimbingCenterState.CENTERS_SCRAPER -> {
                    ScrapeClimbingCenter()
                }

                ClimbingCenterState.CENTERS_GENERATOR -> {
                    GenerateClimbingCenter()
                }
            }
        }
    }
}
