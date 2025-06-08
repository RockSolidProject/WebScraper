package webScraper.OutdorSpotsRoutes

import org.json.JSONObject
import java.time.LocalDateTime

data class ClimbingRoute(
    val name: String,
    var length: Double?,
    val type : RouteType,
    val postedBy : String = "000000000000000000000000",
    var dateTime: LocalDateTime = LocalDateTime.now(),
    var _id: String? = null
){
    override fun toString(): String {
        var out = "\"$name\","
        out += when(type) {
            RouteType.Lead -> "Športna pot, "
            RouteType.Boulder -> "Balvanska pot"
            RouteType.Urban -> "Urbana pot, "
        }
        out += if (type == RouteType.Boulder) {
            ""
        }
        else if (length == null) {
            "Length unknown"
        }
        else {
            "${length}m"
        }
        out+= "\n\t\tPostedBy: $postedBy, data time: $dateTime, ID: $_id"
        return out
    }
    fun toJSON(spotId : String) : String{
        val type = when(type) {
            RouteType.Lead -> "lead"
            RouteType.Boulder -> "boulder"
            RouteType.Urban -> "urban"
        }
        val payload = JSONObject()
            .put("name",name)
            .put("length", length)
            .put("type", type)
            .put("climbingArea", spotId)
            .put("postedBy", postedBy)


        return payload.toString()
    }
    fun toJsonID(spotId : String) : String{
        val type = when(type) {
            RouteType.Lead -> "lead"
            RouteType.Boulder -> "boulder"
            RouteType.Urban -> "urban"
        }
        val payload = JSONObject()
            .put("name",name)
            .put("length", length)
            .put("type", type)
            .put("climbingArea", spotId)
            .put("postedBy", postedBy)
        if (_id != null)
            payload.put("_id",_id)

        return payload.toString()
    }
}