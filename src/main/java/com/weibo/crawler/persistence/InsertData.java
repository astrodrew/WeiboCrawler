package com.weibo.crawler.persistence;

import com.mongodb.BulkWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Collation;
import com.weibo.crawler.parser.ParseByJsoup;
import org.bson.Document;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

public class InsertData extends Thread{
    public static  Logger logger = (Logger) Logger.getLogger(String.valueOf(InsertData.class));
    public static List<Document> toPushDocuments = new ArrayList<Document>();
    public MongoCollection<Document> collection = MongoHelper.mongoCollection;
    public static List<Document> removeDuplicate(List<Document> list) {
        HashSet<Document> h = new HashSet<Document>(list);
        list.clear();
        list.addAll(h);
        return list;
    }
    @Override
    public void run(){
        if(collection.count() == 0){
            collection.createIndex(new org.bson.Document()
                    .append("微博正文",1));
        }
        while (true){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(ParseByJsoup.havaParsedData.size() > 0){
                Document document = ParseByJsoup.havaParsedData.poll();
                toPushDocuments.add(document);
                toPushDocuments = removeDuplicate(toPushDocuments);
                if(toPushDocuments.size() >= 50){
                    try{
                        collection.insertMany(toPushDocuments);}
                    catch( com.mongodb.MongoBulkWriteException e){
                        logger.info("写入错误，有可能重复");
                    }
                    toPushDocuments.clear();
                }
            } else {
                if(toPushDocuments.size()>0){
                    try{
                        collection.insertMany(toPushDocuments);}
                    catch( com.mongodb.MongoBulkWriteException e){
                        logger.info("写入错误，有可能重复");
                    }
                    toPushDocuments.clear();
                }
            }
        }
    }
}
