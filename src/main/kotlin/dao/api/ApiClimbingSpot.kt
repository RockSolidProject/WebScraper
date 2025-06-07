package dao.api

import dao.ClimbingSpotDao
import db.DbUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import webScraper.OutdorSpotsRoutes.ClimbingRoute
import webScraper.OutdorSpotsRoutes.ClimbingSpot
import webScraper.OutdorSpotsRoutes.RouteType
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ApiClimbingSpot : ClimbingSpotDao {
    val formatter = DateTimeFormatter.ISO_DATE_TIME
    override fun getAll(): List<ClimbingSpot>? {
        val outputSpots = mutableListOf<ClimbingSpot>()

        try {
            val request = Request.Builder()
                .url(DbUtil.climbingSpotPath!!)
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
                            val id = element.getString("_id")
                            val postedBy = element.getJSONObject("postedBy").getString("_id")

                            val spot = ClimbingSpot(name,Pair(latitude,longitude), postedBy=postedBy, _id = id)

                            val dateTime = element.getString("dateTime")
                            try {
                                spot.dateTime = LocalDateTime.parse(dateTime,formatter)
                            }
                            catch (e: Exception) {
                                println("Failed to parse dateTime: ${e.message}")
                            }
                            val routesJson = element.getJSONArray("routes")
                            for (j in 0 ..< routesJson.length()){
                                try {
                                    val routeJson = routesJson.getJSONObject(j)
                                    val routeName = routeJson.getString("name")
                                    val routeLength = routeJson.getDouble("length")
                                    val typeString = routeJson.getString("type")
                                    val routeType = when (typeString){
                                        "boulder" -> RouteType.Boulder
                                        "lead" -> RouteType.Lead
                                        "urban" -> RouteType.Urban
                                        else -> continue
                                    }
                                    val routeDateTimeActual: LocalDateTime;
                                    val routeDateTime = routeJson.getString("dateTime")
                                    try {
                                        routeDateTimeActual = LocalDateTime.parse(routeDateTime,formatter)
                                    }
                                    catch (e: Exception) {
                                        println("Failed to parse routes dateTime: ${e.message}")
                                        continue
                                    }
                                    val routeId = routeJson.getString("_id")
                                    val routePostedBy = routeJson.getJSONObject("postedBy").getString("_id")
                                    val route = ClimbingRoute(
                                        routeName,routeLength,
                                        routeType,dateTime=routeDateTimeActual,
                                        _id = routeId, postedBy = routePostedBy
                                    )
                                    spot.routes.add(route)
                                }
                                catch (e: Exception) {
                                    println("Invalid route skipped: ${e.message}")
                                }
                            }

                            outputSpots.add(spot)
                        } catch (e: Exception) {
                            println("Found invalid object continuing: ${e.message}")
                            continue
                        }
                    }
                    return outputSpots
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

    override fun insert(obj: ClimbingSpot): Boolean {
        try {
            val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
            val body = obj.toJSON().toRequestBody(mediaType)
            val request = Request.Builder()
                .url(DbUtil.climbingSpotPath ?: return false)
                .post(body)
                .addHeader("Authorization", "Bearer ${DbUtil.jwt ?: return false}")
                .addHeader("Content-Type", "application/json")
                .build()
            val response = DbUtil.client?.newCall(request)?.execute()
            response.use { res ->
                if (res != null && res.isSuccessful) {
                    println("Climbing spot created.")
                    return true
                } else {
                    println("Failed to create climbing spot. Code: ${res?.code}")
                }
            }
        }
        catch (e: Exception) {
            println("Request failed: ${e.message}")
        }
        return false
    }//TODO add conected routes

    override fun update(obj: ClimbingSpot): Boolean {
        val id = obj._id ?: return false
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val body = obj.toJSON().toRequestBody(mediaType)

        val request = Request.Builder()
            .url(DbUtil.climbingSpotPath + "/${id}")
            .put(body)
            .addHeader("Authorization", "Bearer ${DbUtil.jwt ?: return false}")
            .addHeader("Content-Type", "application/json")
            .build()
        try {
            val response = DbUtil.client?.newCall(request)?.execute()
            response.use { res ->
                if (res != null && res.isSuccessful) {
                    println("Climbing area updated.")
                    return true
                } else {
                    println("Failed to update climbing area. Code: ${res?.code}")
                }
            }
        }
        catch (e: Exception) {
            println("Update request failed: ${e.message}")
        }
        return false
    } //TODO update routes

    override fun delete(obj: ClimbingSpot): Boolean {
        val id = obj._id ?: return false
        val request = Request.Builder()
            .url(DbUtil.climbingSpotPath + "/$id")
            .delete()
            .addHeader("Authorization", "Bearer ${DbUtil.jwt ?: return false}")
            .build()
        return try {
            val response = DbUtil.client?.newCall(request)?.execute()
            response.use { res ->
                if (res != null && res.isSuccessful) {
                    println("Climbing spot deleted.")
                    true
                } else {
                    println("Failed to delete climbing spot. Code: ${res?.code}")
                    false
                }
            }
        } catch (e: Exception) {
            println("Delete request failed: ${e.message}")
            false
        }
    }

}