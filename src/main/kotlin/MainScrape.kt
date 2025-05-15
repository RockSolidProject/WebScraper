import webScraper.InnerClimbingCenter.KspScraper
import webScraper.OutdorSpotsRoutes.LeadClimbingScraper
//import java.io.File

fun main() {
    KspScraper().getData1().forEach {
        println(it)
    }
    var output = ""
    LeadClimbingScraper().scrapeAllClimbingSpots(4).forEach {
        output += "$it\n"
    }
    //File("output_test.txt").writeText(output)
    println(output)
}
