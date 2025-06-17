package webScraper.InnerClimbingCenter

import org.bson.Document
import org.bson.types.ObjectId
import org.json.JSONObject

class InnerClimbingCenter(
    var name: String,
    var latitude: Double,
    var longitude: Double,
    val hasBoulders: Boolean = false,
    val hasRoutes: Boolean = false,
    val hasMoonboard: Boolean = false,
    val hasSprayWall: Boolean = false,
    val hasKilter: Boolean = false,
    var owner: String = "000000000000000000000000",
    var _id: String? = null
){
    override fun toString(): String {
        return "$name ($longitude, $latitude)"
    }
    fun toJSON(): String {
        val payload = JSONObject()
            .put("name",name)
            .put("latitude", latitude)
            .put("longitude", longitude)
            .put("hasBoulders", hasBoulders)
            .put("hasRoutes", hasRoutes)
            .put("hasMoonboard", hasMoonboard)
            .put("hasSprayWall", hasSprayWall)
            .put("hasKilter", hasKilter)
            .put("owner", owner)

        return payload.toString()

    }
}