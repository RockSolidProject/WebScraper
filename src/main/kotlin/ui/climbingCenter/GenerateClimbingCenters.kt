package ui.climbingCenter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dao.InnerClimbingCenterDao
import webScraper.InnerClimbingCenter.InnerClimbingCenter
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun GenerateClimbingCenters(
    dao: InnerClimbingCenterDao,
    onGenerate: (List<InnerClimbingCenter>) -> Unit
) {
    var itemCount by remember { mutableStateOf("") }
    var minLatitude by remember { mutableStateOf("") }
    var maxLatitude by remember { mutableStateOf("") }
    var minLongitude by remember { mutableStateOf("") }
    var maxLongitude by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = itemCount,
            onValueChange = { itemCount = it },
            label = { Text("Number of items to generate") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = minLatitude,
            onValueChange = { minLatitude = it },
            label = { Text("Min Latitude") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = maxLatitude,
            onValueChange = { maxLatitude = it },
            label = { Text("Max Latitude") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = minLongitude,
            onValueChange = { minLongitude = it },
            label = { Text("Min Longitude") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = maxLongitude,
            onValueChange = { maxLongitude = it },
            label = { Text("Max Longitude") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        Button(
            onClick = {
                println("Generating $itemCount items")
                val generated = generateWithParameters(
                    itemCount.toInt(),
                    minLatitude.toDoubleOrNull(),
                    maxLatitude.toDoubleOrNull(),
                    minLongitude.toDoubleOrNull(),
                    maxLongitude.toDoubleOrNull()
                )
                val successfullyAdded = generated.filter { dao.insert(it) }
                onGenerate(successfullyAdded)
            },
            colors = ButtonDefaults.buttonColors(Color(0xFF01579B)),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Generate")
        }
    }
}

fun generateWithParameters(
    number: Int,
    minLatitude: Double?,
    maxLatitude: Double?,
    minLongitude: Double?,
    maxLongitude: Double?
): List<InnerClimbingCenter> {

    val random = java.util.Random()

    val finalMinLat = minLatitude ?: 45.68893209292477 //default
    val finalMaxLat = maxLatitude ?: 46.34826314805122
    val finalMinLon = minLongitude ?: 13.979428685808415
    val finalMaxLon = maxLongitude ?: 15.650946721725376

    val namePool = listOf(
        "Plezalni center", "Osnovna šola", "Športni center", "Plezališče",
    )
    val cityPool = listOf(
        "Ljubljana",
        "Maribor",
        "Celje",
        "Kranj",
        "Velenje",
        "Koper",
        "Novo mesto",
        "Ptuj",
        "Trbovlje",
        "Jesenice",
        "Nova Gorica",
        "Murska Sobota",
        "Domžale",
        "Škofja Loka",
        "Slovenj Gradec",
        "Postojna",
        "Kamnik",
        "Izola",
        "Sežana",
        "Ravne na Koroškem"
    )

    val centers = mutableListOf<InnerClimbingCenter>()

    for (i in 1..number) {
        val lat = finalMinLat + (finalMaxLat - finalMinLat) * random.nextDouble()
        val lon = finalMinLon + (finalMaxLon - finalMinLon) * random.nextDouble()
        val name = namePool[random.nextInt(namePool.size)] + " "  + cityPool[random.nextInt(namePool.size)]

        centers.add(
            InnerClimbingCenter(
                name = name,
                latitude = BigDecimal(lat).setScale(4, RoundingMode.HALF_UP).toDouble(),
                longitude = BigDecimal(lon).setScale(4, RoundingMode.HALF_UP).toDouble(),
                hasKilter = random.nextBoolean(),
                hasRoutes = random.nextBoolean(),
                hasMoonboard = random.nextBoolean(),
                hasBoulders = random.nextBoolean(),
                hasSprayWall = random.nextBoolean(),
            )
        )
    }

    return centers
}


