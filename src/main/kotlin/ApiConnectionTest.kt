import dao.api.ApiInnerClimbingCenters
import db.DbUtil

fun main() {
    if (!DbUtil.prepareDb()) return
    val connection = ApiInnerClimbingCenters()
    connection.getAll()?.forEach {  climbingCenter ->
        println(climbingCenter)
        //println(climbingCenter.owner)
    }
}