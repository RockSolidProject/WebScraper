package webScraper.InnerClimbingCenter

import org.bson.Document
import org.bson.types.ObjectId

class InnerClimbingCenter(
    var name: String,
    var latitude: Double,
    var longitude: Double,
    val hasBoulders: Boolean = false,
    val hasRoutes: Boolean = false,
    val hasMoonboard: Boolean = false,
    val hasSprayWall: Boolean = false,
    val hasKilter: Boolean = false,
    var owner: String = "000000000000000000000000"
){
    override fun toString(): String {
        return "$name ($longitude, $latitude)"
    }
    fun toDocument(): Document {
        return Document()
            .append("name", name)
            .append("longitude", longitude)
            .append("latitude", latitude)
            .append("owner", ObjectId(owner))
            .append("hasBoulders", hasBoulders)
            .append("hasRoutes", hasRoutes)
            .append("hasMoonboard", hasMoonboard)
            .append("hasSprayWall", hasSprayWall)
            .append("hasKilter", hasKilter)
            .append("rating", 0)
            .append("__v", 0)
    }
    fun toJSON(): String {
        return toDocument().toJson()
    }
}