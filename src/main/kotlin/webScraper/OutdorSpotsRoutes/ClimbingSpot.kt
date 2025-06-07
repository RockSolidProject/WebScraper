package webScraper.OutdorSpotsRoutes

import java.time.LocalDateTime

class ClimbingSpot(
    val name: String,
    var coordinates: Pair<Double, Double>? = null,
    var routes: MutableList<ClimbingRoute> = mutableListOf(),
    var dateTime: LocalDateTime = LocalDateTime.now(),
    var postedBy: String = "000000000000000000000000",
    var _id: String? = null
) {
    override fun toString(): String {
        var out = "Climbing centre $name\n"
        out += if (coordinates == null) {
            "Location unknown."
        } else
            "Lat: ${coordinates!!.first}, Lon: ${coordinates!!.second}\n"
        out += "ROUTES:\n"
        routes.forEach { route ->
            out += "\t$route\n"
        }
        return out + "\n"
    }
}