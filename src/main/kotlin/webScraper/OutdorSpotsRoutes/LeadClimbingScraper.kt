package webScraper.OutdorSpotsRoutes
import org.jsoup.Jsoup
import webScraper.ScraperUtils

class LeadClimbingScraper{

    fun scrapeAllClimbingSpots(limit : Int? = null): List<ClimbingSpot> {
        var spots = getClimbingSpots()

        if (limit != null){
            spots = spots.take(limit)
        }

        val climbingSpots = scrapeClimbingSpotsAndRoutes(spots)

        return climbingSpots
    }

    private fun getClimbingSpots(): List<Triple<String, String, ClimbingSpot>> {
        println("Getting lead climbing spots and routes")
        val html = ScraperUtils.getContent("https://plezanje.net/plezalisca/slovenija;tip=sportne", delay = 2000)
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
                    climbingSpot.coordinates = if (latitude == null || longitude == null) null
                        else Pair(latitude, longitude)
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

                        val lengthNum = length.split(" ")[0].toIntOrNull()

                        if (routeName.isNotEmpty()) {
                            ClimbingRoute(
                                name = routeName,
                                difficulty = difficulty,
                                length = lengthNum,
                                type = RouteType.Lead
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
}