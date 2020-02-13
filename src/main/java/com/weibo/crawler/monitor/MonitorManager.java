package com.weibo.crawler.monitor;

import com.weibo.crawler.download.DownloadHtmlSource;
import com.weibo.crawler.parser.ParseByJsoup;
import com.weibo.crawler.persistence.MongoHelper;
import com.weibo.crawler.utils.propertiesUtil;

import java.util.logging.Logger;

public class MonitorManager extends Thread {
    public  static Logger logger = (Logger)Logger.getLogger(String.valueOf(MonitorManager.class));
    @Override
    public void run(){
        while (true){
            logger.info("当前队列中网页个数为" + String.valueOf(DownloadHtmlSource.htmlSourceQueue.size()));
            logger.info("总共下载了" + DownloadHtmlSource.totalDownloadHtmlNum + "个页面");
            if(propertiesUtil.isInsert){
                logger.info("当前存了" + MongoHelper.getCount(MongoHelper.mongoCollection) + "条数据,"
                        + "还有" + ParseByJsoup.havaParsedData .size() + "条没存");
            }else{
               logger.info("解析了" + ParseByJsoup.havaParsedData .size() + "条数据");
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
