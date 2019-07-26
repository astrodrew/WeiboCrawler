package com.weibo.crawler.persistence;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Collation;
import com.weibo.crawler.parser.ParseByJsoup;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class InsertData extends Thread{

    public static List<Document> toPushDocument = new ArrayList<Document>();
    public MongoCollection<Document> collection = MongoHelper.mongoCollection;
    @Override
    public void run(){
        while (true){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(ParseByJsoup.havaParsedData.size() > 0){
                Document document = ParseByJsoup.havaParsedData.poll();
                toPushDocument.add(document);
                if(toPushDocument.size() >= 50){
                    collection.insertMany(toPushDocument);
                    toPushDocument.clear();
                }
            } else {
                if(toPushDocument.size()>0){
                    collection.insertMany(toPushDocument);
                    toPushDocument.clear();
                }
            }
        }
    }
}
