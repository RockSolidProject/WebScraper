import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.material3.Text

enum class MenuState { CLIMBING_AREAS, CLIMBING_CENTERS }
enum class ClimbingAreaState { ADD_AREAS, AREAS, AREAS_SCRAPER, AREAS_GENERATOR }
enum class ClimbingCenterState { ADD_CENTER, CENTERS, CENTERS_SCRAPER, CENTERS_GENERATOR }

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
                .background(Color(0xFF05DFCD)),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
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
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Invoices")
                        Text("Climbing areas")
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
                        horizontalArrangement = Arrangement.spacedBy(4.dp)

                    ) {
                        Icon(Icons.Default.Info, contentDescription = "About")
                        Text("Climbing centers")
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
                    "You're viewing the \"" +
                            when (menuState) {
                                MenuState.CLIMBING_AREAS -> "Invoices"
                                MenuState.CLIMBING_CENTERS -> "About app"
                            } + "\" tab."
                )
            }
        }
    }
}

@Composable
fun ClimbingAreas() {
    var menuState by remember { mutableStateOf(ClimbingAreaState.AREAS) }
    Row(Modifier.fillMaxSize()) {
        //levi meni
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
                    AddClimbingArea()
                }

                ClimbingAreaState.AREAS -> {
                    ListClimbingArea()
                }

                ClimbingAreaState.AREAS_SCRAPER -> {
                    ScrapeClimbingArea()
                }

                ClimbingAreaState.AREAS_GENERATOR -> {
                    GenerateClimbingArea()
                }
            }
        }
    }
}

@Composable
fun AddClimbingArea() {
    Text("Add Area", modifier = Modifier.padding(8.dp))
}

@Composable
fun ListClimbingArea() {
    Text("List", modifier = Modifier.padding(8.dp))
}

@Composable
fun ScrapeClimbingArea() {
    Text("Scrape", modifier = Modifier.padding(8.dp))

}

@Composable
fun GenerateClimbingArea() {
    Text("Generate", modifier = Modifier.padding(8.dp))
}


@Composable
fun ClimbingCenter() {
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
                    .clickable { menuState = ClimbingCenterState.ADD_CENTER}
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    "Add Center",
                    modifier = Modifier.padding(8.dp).fillMaxWidth())

            }
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

@Composable
fun AddClimbingCenter(){

}

@Composable
fun ListClimbingCenter(){

}
@Composable
fun ScrapeClimbingCenter(){

}
@Composable
fun GenerateClimbingCenter(){

}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
