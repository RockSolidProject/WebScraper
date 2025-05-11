package webScraper

import org.jsoup.Jsoup

class PlezanjeScraper  {
    fun getData1(): String? {
        val html = ScraperUtils.getContent("https://plezanje.net/plezalisca/slovenija;tip=sportne", delay = 3000)
        //println(html)
        if (html == null){
            println("Fetching html failed")
            return null
        }
        val document = Jsoup.parse(html) //YOU WANT TO DO JSOUP HTML PARSING
        return document.select("h1").first()?.text()
    }

    //TODO get data should return a list of ClimbingSpot which is a class you should make

    /*fun getData() : MutableList<ClimbingSpot>{
        val climbingSpots : MutableList<ClimbingSpot> = mutableListOf()

        val climbingSpot = ClimbingSpot(name="Armeško", region="Dolenjska", koordinate = Pair(46.01433,15.49110))

        val climbingRoute = ClimbingRoute(name="dada",difficulty="dasdada", length="4m")

        climbingSpot.routes.add(climbingRoute)

        climbingSpots.add(climbingSpot)

        return climbingSpots
    }*/
}