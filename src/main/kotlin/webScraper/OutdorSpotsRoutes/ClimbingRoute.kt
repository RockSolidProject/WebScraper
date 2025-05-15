package webScraper.OutdorSpotsRoutes

class ClimbingRoute(
    val name: String,
    val difficulty: String,
    val length: Int?
){
    override fun toString(): String {
        var out = "\"$name, $difficulty,"
        out += if (length == null) {
            "Length unknown"
        }
        else {
            "${length}m"
        }
        return out
    }
}