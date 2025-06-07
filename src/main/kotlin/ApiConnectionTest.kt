import dao.api.ApiInnerClimbingCenters
import db.DbUtil
import webScraper.InnerClimbingCenter.InnerClimbingCenter

fun main() {
    if (!DbUtil.prepareDb()) return
    val connection = ApiInnerClimbingCenters()
    connection.insert(InnerClimbingCenter(
        "Loka",45.0,15.0,true
    ))
    connection.getAll()?.forEach {  climbingCenter ->
        println(climbingCenter)
        //println("ID: ${climbingCenter._id}")
        //println(climbingCenter.owner)
    }
}