import webScraper.KspScraper
import webScraper.OutdorSpotsRoutes.LeadClimbingScraper
import java.io.File

fun main() {
    var output = ""
    LeadClimbingScraper().scrapeAllClimbingSpots(4).forEach {
        output += "$it\n"
    }
    File("output_test.txt").writeText(output)
}
