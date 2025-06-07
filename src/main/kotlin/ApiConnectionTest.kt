import dao.api.ApiInnerClimbingCenters
import db.DbUtil

fun main() {
    DbUtil.prepareDb()
    val connection = ApiInnerClimbingCenters()
    connection.list()?.forEach {  climbingCenter ->
        println(climbingCenter)
        //println(climbingCenter.owner)
    }
}