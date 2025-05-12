package webScraper
import org.jsoup.Jsoup
class ClimbingSpot(
    val name: String,
    var coordinates: Pair<Double, Double> = Pair(0.0, 0.0),
    var routes: MutableList<ClimbingRoute> = mutableListOf()
)

class ClimbingRoute(
    val name: String,
    val difficulty: String,
    val length: String,
)

class PlezanjeScraper{

    fun getData1(): String? {
        val html = ScraperUtils.getContent("https://plezanje.net/plezalisca/slovenija;tip=sportne", delay = 3000)
        if (html == null){
            println("Fetching html failed")
            return null
        }
        val document = Jsoup.parse(html) //YOU WANT TO DO JSOUP HTML PARSING
        return document.select("h1").first()?.text()
    }

    fun scrapeAllClimbingSpots(): List<ClimbingSpot> {
        val spots = getClimbingSpots()
        val climbingSpots = scrapeClimbingSpotsAndRoutes(spots)

        return climbingSpots
    }

    private fun getClimbingSpots(): List<Triple<String, String, ClimbingSpot>> {
        println("IN getClimbingSpots")
        val html = ScraperUtils.getContent("https://plezanje.net/plezalisca/slovenija;tip=sportne", delay = 1000)
        if (html == null) {
            println("Fetching html failed")
            return emptyList()
        }

        val document = Jsoup.parse(html, "https://plezanje.net/plezalisca")
        val home = document.selectFirst("div.card.mt-16.ng-star-inserted")
        if (home == null) {
            println("No home element found")
            return emptyList()
        }

        val rows = home.select("> div.row.ng-star-inserted")
        println("Found ${rows.size} rows")

        return rows.mapNotNull { row ->
            // Get the link element
            val linkElement = row.selectFirst("a[href^='/plezalisce/']")

            if (linkElement == null) {
                println("Skipping row – missing link: ${row.text()}")
                return@mapNotNull null
            }

            val name = linkElement.text().trim()
            val url = linkElement.absUrl("href").trim()

            // Get number of routes from the span inside the row
            val routeSpan = row.selectFirst("div.routes span")
            val numberOfRoutesText = routeSpan?.ownText()?.substringBefore(" ")?.trim()
            val numberOfRoutes = numberOfRoutesText?.toIntOrNull()

            if (name.isNotBlank() && url.isNotBlank() && numberOfRoutes != null) {
                println("Found: $name ($numberOfRoutes smeri) -> $url")
                Triple(name, url, ClimbingSpot(name))
            } else {
                println("Skipping row – missing data: ${row.text()}")
                null
            }
        }
    }
    private fun scrapeClimbingSpotsAndRoutes(spots: List<Triple<String, String, ClimbingSpot>>): List<ClimbingSpot> {
        return spots.map { (name, url, climbingSpot) ->
            println("Scraping details for: $name -> $url")

            val html = ScraperUtils.getContent(url, delay = 1000)
            if (html == null) {
                println("Failed to fetch details for $name")
                return@map climbingSpot
            }

            val document = Jsoup.parse(html, "https://plezanje.net/plezalisca")

            // Get coordinates
            val coordinateDiv = document.select("div.label").firstOrNull { it.text().trim() == "Koordinate" }
            if (coordinateDiv != null) {
                val coordinatesSpan = coordinateDiv.nextElementSibling() // The coordinates are always after div with tekst: "Koordinate"
                if (coordinatesSpan != null && coordinatesSpan.tagName() == "span" && coordinatesSpan.hasClass("text-right")) {
                    val coordinatesText = coordinatesSpan.text().trim()
                    val divided = coordinatesText.split(" ")
                    val latitude = divided.getOrNull(0)?.toDoubleOrNull()
                    val longitude = divided.getOrNull(1)?.toDoubleOrNull()
                    println("Coordinates: $latitude, $longitude")
                } else {
                    println("No valid coordinates span found")
                }
            } else {
                println("Coordinate div not found")
            }

            // Get routes
            val tables = document.select("table.shadow.mt-16.ng-star-inserted")
            if (tables.isNotEmpty()) {
                tables.forEach { table ->
                    val rows = table.select("tbody > tr")
                    val routes = rows.mapNotNull { row ->
                        val routeName = row.selectFirst("td.name a")?.text()?.trim().orEmpty()
                        val length = row.select("td.ng-star-inserted").getOrNull(1)?.text()?.trim().orEmpty()
                        val difficulty = row.selectFirst("td.ng-star-inserted app-grade.ng-star-inserted span.grade-name.ng-star-inserted")?.text()?.trim().orEmpty()

                        if (routeName.isNotEmpty()) {
                            ClimbingRoute(
                                name = routeName,
                                difficulty = difficulty,
                                length = length,
                            )
                        } else {
                            null
                        }
                    }
                    climbingSpot.routes.addAll(routes)
                }
            } else {
                println("No routes tables found for $name")
            }
            println("Finished scraping $name")
            climbingSpot
        }
    }

    fun climbingSpotsToString(routes: List<ClimbingSpot>): String {
        var result = ""
        routes.forEach {
            result += "Name: ${it.name}\n"
            result += "Coordinates: ${it.coordinates.first}, ${it.coordinates.second}\n"
            it.routes.forEach { route ->
                result += "\t${route.name}, Difficulty: ${route.difficulty}, Length: ${route.length}\n"
            }
            result += "\n"
        }
        return result
    }


    /*fun getData() : MutableList<ClimbingSpot>{
        val climbingSpots : MutableList<ClimbingSpot> = mutableListOf()

        val climbingSpot = ClimbingSpot(name="Armeško", region="Dolenjska", koordinate = Pair(46.01433,15.49110))

        val climbingRoute = ClimbingRoute(name="dada",difficulty="dasdada", length="4m")

        climbingSpot.routes.add(climbingRoute)

        climbingSpots.add(climbingSpot)

        return climbingSpots
    }*/
}