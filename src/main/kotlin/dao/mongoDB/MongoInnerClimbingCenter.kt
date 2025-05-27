package dao.mongoDB

import dao.InnerClimbingCenterDao
import webScraper.InnerClimbingCenter.InnerClimbingCenter

import org.json.JSONObject
import java.io.File
import com.mongodb.client.MongoClients

class MongoInnerClimbingCenter : InnerClimbingCenterDao {
    override fun insert(obj: InnerClimbingCenter) : Boolean {
        try {
            val config = JSONObject(File("src/main/kotlin/db/config.json").readText())
            val mongoClient = MongoClients.create(config.getString("mongoLink"))
            val database = mongoClient.getDatabase(config.getString("database"))
            val collection = database.getCollection(config.getString("centreCollection"))
            val document = obj.toDocument()
            collection.insertOne(document)
        }
        catch (e : Exception) {
            println("ERROR: Inserting failed for:  $obj. $e")
            return false
        }
        return true
    }
}