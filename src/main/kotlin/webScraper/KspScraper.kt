package webScraper

import org.jsoup.Jsoup

class KspScraper {
    fun getData1(): String? {
        val html = ScraperUtils.getContent("https://ksp.pzs.si/plezalisca.php?tip=3", delay = 3000)
        //println(html)
        if (html == null){
            println("Fetching html failed")
            return null
        }
        val document = Jsoup.parse(html) //YOU WANT TO DO JSOUP HTML PARSING
        return document.select("h1").first()?.text()
    }

    //TODO get data should return a list of ClimbingCentre which is a class you should make

    /*fun getData() : MutableList<ClimbingCentre>{
        val climbingCentres : MutableList<ClimbingCentre> = mutableListOf()

        val climbingCentre = ClimbingCentre(name = "", location = Pair(4.323232,2.312312))

        return climbingCentres
    }*/
}