package webScraper.OutdorSpotsRoutes

class ClimbingRoute(
    val name: String,
    val difficulty: String,
    val length: Int?,
    val type : RouteType
){
    override fun toString(): String {
        var out = "\"$name\", $difficulty,"
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
        return out
    }
}