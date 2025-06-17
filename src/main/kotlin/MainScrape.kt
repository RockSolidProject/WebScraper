import webScraper.InnerClimbingCenter.KspScraper
import webScraper.OutdorSpotsRoutes.BoulderClimbingScraper
import webScraper.OutdorSpotsRoutes.LeadClimbingScraper
import java.io.File

fun main() {
    var output = ""
    KspScraper().getData1().forEach {
        output += "$it\n"
        println(it)
    }
    File("output_test_inside.txt").writeText(output)

    var output1 = ""
    LeadClimbingScraper().scrapeAllClimbingSpots(4).forEach {
        output1 += "$it\n"
    }
    File("output_test_lead.txt").writeText(output1)
    //println(output)

    var output2 = ""
    BoulderClimbingScraper().scrapeAllClimbingSpots(4).forEach {
        output2 += "$it\n"
    }
    File("output_test_boulder.txt").writeText(output2)
}
