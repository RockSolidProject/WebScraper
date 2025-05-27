package webScraper.InnerClimbingCenter

import org.bson.Document
import org.bson.types.ObjectId

class InnerClimbingCenter(
    var name: String,
    var longitude: Double,
    var latitude: Double,
    private var owner: String = "000000000000000000000000"
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
            .append("hasBoulders", false)
            .append("hasRoutes", false)
            .append("hasMoonboard", false)
            .append("hasSprayWall", false)
            .append("rating", 0)
    }
}