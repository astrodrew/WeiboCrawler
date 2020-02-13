package com.weibo.crawler.persistence;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Collation;
import org.bson.Document;

public class MongoHelper {
    public static MongoDatabase mongoDatabase = DBConnection("wb");
    public static MongoCollection<Document> mongoCollection = getCollection("weiboData");
    public static MongoDatabase DBConnection(String DBName){
        //mongo 返回数据库
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(DBName);
        return mongoDatabase;
    }

    public static MongoCollection<Document> getCollection(MongoDatabase mongoDatabase,String collectionName){
        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
        return collection;
    }
    public static MongoCollection<Document> getCollection(String collectionName){
        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
        return collection;
    }

    public static int getCount(MongoCollection<Document> coll) {
        int count = (int) coll.count();
        return count;
    }

    public static void main(String[] args) {

    }
}
