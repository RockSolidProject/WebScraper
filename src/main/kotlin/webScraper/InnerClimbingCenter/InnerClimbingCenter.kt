package webScraper.InnerClimbingCenter

class InnerClimbingCenter(var name: String,
                          var longitude: Double,
                          var latitude: Double)
{
    override fun toString(): String {
        return "$name ($longitude, $latitude)"
    }
}
