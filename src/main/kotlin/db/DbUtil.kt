package db

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document
import org.json.JSONObject
import java.io.File

object DbUtil {

    private var mongoClient : MongoClient? = null
    private var database : MongoDatabase? = null
    var centreCollection : MongoCollection<Document>? = null

    fun prepareDb() : Boolean{
        try {
            val config = JSONObject(File("src/main/kotlin/db/config.json").readText())
            mongoClient = MongoClients.create(config.getString("mongoLink"))
            database = mongoClient!!.getDatabase(config.getString("database"))
            centreCollection = database!!.getCollection(config.getString("centreCollection"))
            return true
        }
        catch (e : Exception) {
            println("Preparing DB failed")
            return false
        }
    }

    fun closeDbConnection(){
        mongoClient?.close()
    }
}