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
import ui.climbingArea.ClimbingAreas
import ui.climbingCenter.ClimbingCenter

enum class MenuState { CLIMBING_AREAS, CLIMBING_CENTERS }

@Preview
@Composable
fun App() {
    var menuState by remember { mutableStateOf(MenuState.CLIMBING_AREAS) }

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
                    MenuState.CLIMBING_CENTERS -> ClimbingCenter()
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