package ui.climbingCenter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dao.InnerClimbingCenterDao
import webScraper.InnerClimbingCenter.InnerClimbingCenter
enum class ClimbingCenterState { ADD_CENTER, CENTERS, CENTERS_SCRAPER, CENTERS_GENERATOR }
@Composable
fun ClimbingCenter(
    climbingCenters: List<InnerClimbingCenter>,
    onUpdateCenters: (List<InnerClimbingCenter>) -> Unit,
    dao: InnerClimbingCenterDao
) {
    var menuState by remember { mutableStateOf(ClimbingCenterState.CENTERS) }
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
                    .background(if (menuState == ClimbingCenterState.CENTERS) Color(0xFFD6D6D6) else Color.Transparent)
                    .clickable { menuState = ClimbingCenterState.CENTERS }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("Centers", modifier = Modifier.padding(8.dp).fillMaxWidth())
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (menuState == ClimbingCenterState.ADD_CENTER) Color(0xFFD6D6D6) else Color.Transparent)
                    .clickable { menuState = ClimbingCenterState.ADD_CENTER }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("Add Center", modifier = Modifier.padding(8.dp).fillMaxWidth())
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (menuState == ClimbingCenterState.CENTERS_SCRAPER) Color(0xFFD6D6D6) else Color.Transparent)
                    .clickable { menuState = ClimbingCenterState.CENTERS_SCRAPER }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("Scraper", modifier = Modifier.padding(8.dp).fillMaxWidth())
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (menuState == ClimbingCenterState.CENTERS_GENERATOR) Color(0xFFD6D6D6) else Color.Transparent)
                    .clickable { menuState = ClimbingCenterState.CENTERS_GENERATOR }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("Generator", modifier = Modifier.padding(8.dp).fillMaxWidth())
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(16.dp)
        ) {
            when (menuState) {
                ClimbingCenterState.ADD_CENTER -> {
                    AddClimbingCenter(
                        dao = dao,
                        onAddClimbingCenter = { newCenter ->
                            onUpdateCenters(climbingCenters + newCenter)
                            menuState = ClimbingCenterState.CENTERS
                        },
                        onNavigateToDefault = { menuState = ClimbingCenterState.CENTERS }
                    )
                }

                ClimbingCenterState.CENTERS -> {
                    ListClimbingCenter(
                        dao = dao,
                    )
                }

                ClimbingCenterState.CENTERS_SCRAPER -> {
                    ScrapeClimbingCenter(
                        onAddCenters = {
                            onUpdateCenters(climbingCenters + it)
                            menuState = ClimbingCenterState.CENTERS
                        },
                        dao = dao
                    )
                }

                ClimbingCenterState.CENTERS_GENERATOR -> {
                    GenerateClimbingCenters(
                        onGenerate = {
                            onUpdateCenters(climbingCenters + it)
                            menuState = ClimbingCenterState.CENTERS
                        }
                    )
                }
            }
        }
    }
}
