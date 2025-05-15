import webScraper.InnerClimbingCenter.KspScraper
import java.io.File

fun main() {
    KspScraper().getData1().forEach {
        println(it)
    }
}
