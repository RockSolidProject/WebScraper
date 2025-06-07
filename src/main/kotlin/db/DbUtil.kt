package db

import okhttp3.OkHttpClient
import org.json.JSONObject
import java.io.File

object DbUtil {
    var client: OkHttpClient? = null
    var backendPath: String? = null
    var climbingCentersPath: String? = null

    fun prepareDb() : Boolean{
        try {
            val config = JSONObject(File("src/main/kotlin/db/config.json").readText())
            client = OkHttpClient()
            backendPath = config.getString("backendApi")
            climbingCentersPath = backendPath + config.getString("climbingCenters")
            return true
        }
        catch (e : Exception) {
            println("Preparing DB failed")
            return false
        }
    }
}