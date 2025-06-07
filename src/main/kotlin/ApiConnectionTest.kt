import dao.api.ApiClimbingSpot
import dao.api.ApiInnerClimbingCenters
import db.DbUtil
import okhttp3.Route
import webScraper.InnerClimbingCenter.InnerClimbingCenter
import webScraper.OutdorSpotsRoutes.ClimbingRoute
import webScraper.OutdorSpotsRoutes.ClimbingSpot
import webScraper.OutdorSpotsRoutes.RouteType

fun main() {
    if (!DbUtil.prepareDb()) return

    val spotConnection = ApiClimbingSpot()
    /*spotConnection.update(
        ClimbingSpot(
            "P1--",
            coordinates = Pair(45.5,15.0),
            _id = "682e0b7af0706cbadc3dcda7"
        )
    )*/
    /*spotConnection.delete(
        ClimbingSpot(
            "P1--",
            coordinates = Pair(45.5,15.0),
            _id = "68449712e48260475b412a9f"
        )
    )*/
    /*spotConnection.insert(
        ClimbingSpot(
            "BolekInLolek2",
            Pair(45.5,15.0)
        )
    )*/
    /*val spot = ClimbingSpot(
            "Marko",
            Pair(45.5,15.0)
        )
    spot.routes.add(ClimbingRoute("marko1",3.0,RouteType.Boulder))
    spot.routes.add(ClimbingRoute("marko2",3.0,RouteType.Lead))

    spotConnection.insert(spot)*/

    /*spotConnection.getAll()?.forEach { climbingSpot ->
        println(climbingSpot)
        //println(climbingSpot.routes.forEach{ println(it.dateTime)})
    }*/



    //val connection = ApiInnerClimbingCenters()
    /*connection.insert(InnerClimbingCenter(
        "Loka",45.0,15.0,true
    ))*/
    /*connection.getAll()?.forEach {  climbingCenter ->
        println(climbingCenter)
        //println("ID: ${climbingCenter._id}")
        //println(climbingCenter.owner)
    }*/
    /*connection.update(
        InnerClimbingCenter(
        "PK Rogaška+",
            latitude = 46.24207340581074,
            longitude = 15.624189376831056,
            owner = "68260b6d85bc0a2b28d03ae8",
            hasSprayWall = true,
            hasRoutes = true,
            _id="6838bf0ddf149eb39dd66caa"
        )
    )*/
    /*connection.delete(
        InnerClimbingCenter(
            "l",
            1.0,
            2.0,
            _id = "6838329883067c96f307f14e"
        )
    )*/
}