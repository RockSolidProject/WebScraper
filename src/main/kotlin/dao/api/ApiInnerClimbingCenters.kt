package dao.api

import db.DbUtil
import okhttp3.Request
import org.json.JSONArray
import webScraper.InnerClimbingCenter.InnerClimbingCenter
import java.io.IOException

class ApiInnerClimbingCenters {
    fun list() : List<InnerClimbingCenter>?{
        val outputCenters = mutableListOf<InnerClimbingCenter>()

        try {
            val request = Request.Builder()
                .url(DbUtil.climbingCentersPath!!)
                .get()
                .build()

            val response = DbUtil.client!!.newCall(request).execute()
            response.use { res ->
                if (res.isSuccessful) {
                    val json = res.body?.string()
                    if (json == null) {
                        println("Unexpected server response.")
                        return null
                    }
                    val jsonArray = JSONArray(json)
                    for (i in 0..<jsonArray.length()) {
                        try {
                            val element = jsonArray.getJSONObject(i)
                            //println(element)
                            val name = element.getString("name")
                            val latitude = element.getDouble("latitude")
                            val longitude = element.getDouble("longitude")
                            val hasBoulders = element.getBoolean("hasBoulders")
                            val hasRoutes = element.getBoolean("hasRoutes")
                            val hasMoonboard = element.getBoolean("hasMoonboard")
                            val hasSprayWall = element.getBoolean("hasSprayWall")
                            val hasKilter = element.getBoolean("hasKilter")
                            val owner = element.getJSONObject("owner").getString("_id")
                            outputCenters.add(
                                InnerClimbingCenter(
                                    name, latitude, longitude, owner = owner, hasBoulders = hasBoulders,
                                    hasRoutes = hasRoutes, hasMoonboard = hasMoonboard,
                                    hasSprayWall = hasSprayWall, hasKilter = hasKilter
                                )
                            )
                        } catch (e: Exception) {
                            println("Found invalid object continuing.")
                            continue
                        }
                    }
                    return outputCenters
                } else {
                    println("Api call failed response is null")
                    return null
                }
            }
        }
        catch (e: IOException) {
            println("Network error: ${e.message}")
            return null
        }
        catch (e: Exception) {
            println("DB not initialized or unexpected error.")
            return null
        }
    }
}