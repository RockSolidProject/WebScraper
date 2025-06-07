package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dao.api.ApiInnerClimbingCenters
import ui.climbingArea.ClimbingAreas
import ui.climbingCenter.ClimbingCenter
import webScraper.InnerClimbingCenter.InnerClimbingCenter

enum class MenuState { CLIMBING_AREAS, CLIMBING_CENTERS }

@Preview
@Composable
fun App() {
    var climbingCenters by remember { mutableStateOf(listOf<InnerClimbingCenter>()) }
    var menuState by remember { mutableStateOf(MenuState.CLIMBING_AREAS) }
    val dao = ApiInnerClimbingCenters()

    LaunchedEffect(Unit) {
        climbingCenters = listOf(
            InnerClimbingCenter("Slovenska bistrica", 46.0569, 14.5058),
            InnerClimbingCenter("Center 2", 46.1628, 14.6042),
            InnerClimbingCenter("Center 2", 46.1628, 14.6042),
            InnerClimbingCenter("Center 2", 46.1628, 14.6042),
            InnerClimbingCenter("Center 2", 46.1628, 14.6042),
            InnerClimbingCenter("Center 1", 46.0569, 14.5058),
            InnerClimbingCenter("Center 2", 46.1628, 14.6042),
            InnerClimbingCenter("Center 2", 46.1628, 14.6042),
            InnerClimbingCenter("Center 2", 46.1628, 14.6042),
            InnerClimbingCenter("Center 2", 46.1628, 14.6042),
        )
    }

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(Color(0xFF0288D1)),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(Color(0xFF0288D1)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable { menuState = MenuState.CLIMBING_AREAS },
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(painter = painterResource("icons/climbing_area.svg"), contentDescription = "Climbing Areas", tint = Color.White, modifier = Modifier.size(50.dp))
                        Text("Climbing Areas", color = Color.White, style = MaterialTheme.typography.h6)
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable { menuState = MenuState.CLIMBING_CENTERS },
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(painter = painterResource("icons/climbing_center.svg"), contentDescription = "Climbing Centers", tint = Color.White, modifier = Modifier.size(35.dp))
                        Text("Climbing Centers", color = Color.White, style = MaterialTheme.typography.h6)
                    }
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                when (menuState) {
                    MenuState.CLIMBING_AREAS -> ClimbingAreas()
                    MenuState.CLIMBING_CENTERS -> ClimbingCenter(
                        climbingCenters = climbingCenters,
                        onUpdateCenters = { climbingCenters = it },
                        dao = dao
                    )
                }
            }
            Box(
                modifier = Modifier
                    .height(30.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = when (menuState) {
                        MenuState.CLIMBING_AREAS -> "Currently on: Climbing Areas tab"
                        MenuState.CLIMBING_CENTERS -> "Currently on: Climbing Centers tab"
                    },
                    color = Color.White
                )
            }

        }
    }
}