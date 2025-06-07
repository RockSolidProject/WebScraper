package db

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File

object DbUtil {
    var client: OkHttpClient? = null
    var backendPath: String? = null
    var climbingCentersPath: String? = null
    var usersPath: String? = null
    var userLoginPath: String? = null
    var climbingSpotPath: String? = null
    private var username: String? = null
    private var password: String? = null
    var jwt: String? = null

    fun prepareDb() : Boolean{
        try {
            val config = JSONObject(File("src/main/kotlin/db/config.json").readText())
            client = OkHttpClient()
            backendPath = config.getString("backendApi")
            climbingCentersPath = backendPath + config.getString("climbingCenters")
            usersPath = backendPath + config.getString("users")
            userLoginPath = usersPath + config.getString("login")
            climbingSpotPath = backendPath + config.getString("climbingAreas")
            val envConfig = JSONObject(File("src/main/kotlin/db/envConfig.json").readText())
            username=envConfig.getString("username")
            password=envConfig.getString("password")
            jwt = login()
            if (jwt == null) {
                println("Login failed")
                return false
            }
            return true
        }
        catch (e : Exception) {
            println("Preparing DB failed")
            return false
        }
    }
    private fun login(): String?{
        try {
            val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
            val payload = JSONObject()
                .put("username", username)
                .put("password", password)
                .toString()
            //println("here ok")
            val body = payload.toRequestBody(mediaType)
            //println("here ok $userLoginPath")
            val request = Request.Builder()
                .url(userLoginPath!!)
                .post(body)
                .build()
            //println("here ok")

            val response = client?.newCall(request)?.execute()
            response.use { res ->
                if (res != null && res.isSuccessful) {
                    val responseBody = res.body?.string()
                    val json = JSONObject(responseBody)
                    return json.getString("token")
                } else {
                    println("Login failed with code: ${res?.code}")
                }
            }
        } catch (e: Exception) {
            println("Login request failed: ${e.message}")
        }
        return null
    }
}