package dao.mongoDB

import dao.InnerClimbingCenterDao
import webScraper.InnerClimbingCenter.InnerClimbingCenter

import org.json.JSONObject
import java.io.File
import com.mongodb.client.MongoClients
import db.DbUtil

class MongoInnerClimbingCenter : InnerClimbingCenterDao {
    override fun insert(obj: InnerClimbingCenter) : Boolean {
        try {
            val document = obj.toDocument()
            DbUtil.centreCollection!!.insertOne(document)
        }
        catch (e : Exception) {
            println("ERROR: Inserting failed for:  $obj. $e")
            return false
        }
        return true
    }
}