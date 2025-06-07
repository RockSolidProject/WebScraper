package dao.api

import dao.InnerClimbingCenterDao
import db.DbUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import webScraper.InnerClimbingCenter.InnerClimbingCenter
import java.io.IOException

class ApiInnerClimbingCenters : InnerClimbingCenterDao {
    override fun getAll() : List<InnerClimbingCenter>?{
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
                            val id = element.getString("_id")
                            val owner = element.getJSONObject("owner").getString("_id")
                            outputCenters.add(
                                InnerClimbingCenter(
                                    name, latitude, longitude, owner = owner, hasBoulders = hasBoulders,
                                    hasRoutes = hasRoutes, hasMoonboard = hasMoonboard,
                                    hasSprayWall = hasSprayWall, hasKilter = hasKilter,
                                    _id = id
                                )
                            )
                        } catch (e: Exception) {
                            println("Found invalid object continuing: ${e.message}")
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
    override fun insert(obj: InnerClimbingCenter) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val body = obj.toJSON().toRequestBody(mediaType)

        val request = Request.Builder()
            .url(DbUtil.climbingCentersPath ?: return false)
            .post(body)
            .addHeader("Authorization", "Bearer ${DbUtil.jwt ?: return false}")
            .addHeader("Content-Type", "application/json")
            .build()

        try {
            val response = DbUtil.client?.newCall(request)?.execute()
            response.use { res ->
                if (res != null && res.isSuccessful) {
                    println("Climbing center created.")
                    return true
                } else {
                    println("Failed to create climbing center. Code: ${res?.code}")
                }
            }
        } catch (e: Exception) {
            println("Request failed: ${e.message}")
        }
        return false
    }
    override fun update(obj: InnerClimbingCenter) : Boolean {
        val id = obj._id ?: return false
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val body = obj.toJSON().toRequestBody(mediaType)

        val request = Request.Builder()
            .url(DbUtil.climbingCentersPath + "/${id}")
            .put(body)
            .addHeader("Authorization", "Bearer ${DbUtil.jwt ?: return false}")
            .addHeader("Content-Type", "application/json")
            .build()

        try {
            val response = DbUtil.client?.newCall(request)?.execute()
            response.use { res ->
                if (res != null && res.isSuccessful) {
                    println("Climbing center updated.")
                    return true
                } else {
                    println("Failed to update climbing center. Code: ${res?.code}")
                }
            }
        } catch (e: Exception) {
            println("Update request failed: ${e.message}")
        }
        return false
    }
    override fun delete(obj: InnerClimbingCenter): Boolean {
        val id = obj._id ?: return false

        val request = Request.Builder()
            .url(DbUtil.climbingCentersPath + "/${id}")
            .delete()
            .addHeader("Authorization", "Bearer ${DbUtil.jwt ?: return false}")
            .build()

        try {
            val response = DbUtil.client?.newCall(request)?.execute()
            response.use { res ->
                if (res != null && res.isSuccessful) {
                    println("Climbing center deleted.")
                    return true
                } else {
                    println("Failed to delete climbing center. Code: ${res?.code}")
                }
            }
        } catch (e: Exception) {
            println("Delete request failed: ${e.message}")
        }
        return false
    }
}