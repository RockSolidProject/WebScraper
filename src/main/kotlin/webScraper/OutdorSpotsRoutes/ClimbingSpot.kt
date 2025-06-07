package webScraper.OutdorSpotsRoutes

import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ClimbingSpot(
    val name: String,
    var coordinates: Pair<Double, Double> = Pair(0.0,0.0),
    var routes: MutableList<ClimbingRoute> = mutableListOf(),
    var dateTime: LocalDateTime = LocalDateTime.now(),
    var postedBy: String = "000000000000000000000000",
    var _id: String? = null
) {
    val formatter = DateTimeFormatter.ISO_DATE_TIME
    override fun toString(): String {
        var out = "Climbing centre $name\n"
        out +="Lat: ${coordinates.first}, Lon: ${coordinates.second}\n"
        out += "ROUTES:\n"
        routes.forEach { route ->
            out += "\t$route\n"
        }
        return out + "\n"
    }
    fun toJSON(): String {
        val payload = JSONObject()
            .put("name",name)
            .put("latitude", coordinates.first)
            .put("longitude", coordinates.second)
            .put("postedBy", postedBy)
            .put("dateTime", dateTime.format(formatter))


        return payload.toString()
    }
}