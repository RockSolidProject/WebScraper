import webScraper.KspScraper
import webScraper.PlezanjeScraper
import java.io.File

fun main() {
    println(PlezanjeScraper().getData1())
    println(KspScraper().getData1())
    val climbingSpot = PlezanjeScraper().scrapeAllClimbingSpots()
    val output = PlezanjeScraper().climbingSpotsToString(climbingSpot)

    // Save the output to a file
    File("climbing_spots.txt").writeText(output)
    println("Climbing spots saved to climbing_spots.txt")


}
