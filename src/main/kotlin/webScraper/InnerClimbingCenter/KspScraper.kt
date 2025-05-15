package webScraper.InnerClimbingCenter

import org.jsoup.Jsoup
import webScraper.ScraperUtils

class KspScraper {
    fun getData1(): List<InnerClimbingCenter> {
        val html = ScraperUtils.getContent("https://ksp.pzs.si/plezalisca.php?tip=3", delay = 3000)
        //println(html)
        if (html == null) {
            println("Fetching html failed")
            return mutableListOf()
        }
        val document = Jsoup.parse(html) //YOU WANT TO DO JSOUP HTML PARSING
        document.select(".ClimbList")

        val climbList = mutableListOf<InnerClimbingCenter>()
        println(document.select(".ClimbList").attr("data-latitude"))
        val climbingElements = document.select(".climbList")
        for (element in climbingElements) {
            climbList.add(InnerClimbingCenter(
                element.attr("data-title"),
                element.attr("data-latitude").toDouble(),
                element.attr("data-longitude").toDouble()
            ))
        }
        return climbList
    }

    /*fun getData() : MutableList<ClimbingCentre>{
        val climbingCentres : MutableList<ClimbingCentre> = mutableListOf()

        val climbingCentre = ClimbingCentre(name = "", location = Pair(4.323232,2.312312))

        return climbingCentres
    }*/
}