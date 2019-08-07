package com.weibo.crawler.download;

import com.sun.deploy.util.StringUtils;
import com.weibo.crawler.selenium.SeleniumOperator4Weibo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

public class DownloadHtmlSource extends  Thread{
    public static int totalDownloadHtmlNum = 0;
    public  static LinkedBlockingQueue<String> htmlSourceQueue = new LinkedBlockingQueue<String>();
    private static Logger logger = (Logger)Logger.getLogger(String.valueOf(DownloadHtmlSource.class));
    private   WebDriver driver;
    private   String keywords;
    private  String startTime;
    private  String endTime;
    private int threadNum;
    public DownloadHtmlSource(WebDriver driver, String keywords, String startTime, String endTime,int threadNum){
        this.driver = driver;
        this.keywords = keywords;
        this.startTime = startTime;
        this.endTime = endTime;
        this.threadNum = threadNum;
    }
    private  void downloadBySelenium(WebDriver driver, String keywords, String startTime, String endTime) throws InterruptedException {
            System.out.println("线程测试");
            for(int pageNum = 1; pageNum <= 50 ; pageNum ++ ){
                synchronized (this.getClass()){
                    String url =  "https://s.weibo.com/weibo?q=" + StringUtils.join(Arrays.asList(keywords.split(",")),"%20")
                            + "&typeall=1&suball=1" +
                            "&timescope=custom:" +
                            startTime + ":" + endTime + "&Refer=g&page=" + String.valueOf(pageNum);
                    driver.get(url);
                    SeleniumOperator4Weibo.clickComment(driver);
                    String html = driver.getPageSource();
                    Document page = Jsoup.parse(html);
                    if (page.select("div.card.card-no-result.s-pt20b40").size() == 0){
                        htmlSourceQueue.add(html);
                        totalDownloadHtmlNum ++;
                    } else{
                        break;
                    }
                }
                }
        }
    @Override
    public void run(){
        this.setName("线程-" + String.valueOf(threadNum));
        logger.info("当前线程: " + this.getName());
        try {
            downloadBySelenium(driver,keywords,startTime,endTime);
        } catch (InterruptedException e) {
//            e.printStackTrace();
            logger.info("Thread.sleep 发生错误");
        }
    }
}
