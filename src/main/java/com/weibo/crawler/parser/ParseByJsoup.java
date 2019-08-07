package com.weibo.crawler.parser;

import com.sun.deploy.util.StringUtils;
import com.weibo.crawler.download.DownloadHtmlSource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

public class ParseByJsoup extends  Thread implements ParseData  {
    private static Logger logger = (Logger) Logger.getLogger(String.valueOf(ParseData.class));
    private   WebDriver driver;

    public static LinkedBlockingQueue<org.bson.Document> havaParsedData = new LinkedBlockingQueue<org.bson.Document>();
    public ParseByJsoup(WebDriver driver){
        this.driver = driver;
    }
    @Override
    public void run(){
        try {
            parse(driver);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void parse(WebDriver driver) throws InterruptedException {
        logger.info("开始解析");
        Thread.sleep(3000);
        while (true) {
            if (DownloadHtmlSource.htmlSourceQueue.isEmpty()) {
                logger.info("当前队里中网页解析无任务，休眠5s");
                Thread.sleep(5000);
            } else {
                String html = DownloadHtmlSource.htmlSourceQueue.poll();
                Document page = Jsoup.parse(html);
                Elements elements = page.select("div.card");
                for (Element element : elements) {
                    String nickName = element.select("div.card-feed > div.content > div.info > div >a.name").text();
                    String text = element.select("div.card-feed > div.content > p.txt").text();
                    String from = element.select("div.card-feed > div.content > p.from").text();
                    //转发评论信息等
                    Elements cardActs = element.select("div.card-act > ul > li");
                    String repostNum = null;
                    String commentNum = null;
                    String awesome = null;
                    Elements comments = element.select("div.list > div.card-review.s-ptb10");
                    List<org.bson.Document> commentList = new ArrayList<org.bson.Document>();
                    String commentUser = null;
                    String commentContent = null;
                    String commentFrom = null;
                    for(Element comment:comments){
                        org.bson.Document bsonDoc = new org.bson.Document();
                        commentUser = comment.select("div.txt > a.name").text();
                        commentContent = comment.select("div.txt").text().replace(commentUser+" ：","").trim();
                        commentFrom = comment.select("div.fun > p.from").text();
                        bsonDoc.append("评论用户名",commentUser);
                        bsonDoc.append("评论内容",commentContent);
                        bsonDoc.append("发布来源",commentFrom);
                        commentList.add(bsonDoc);
                    }
                    if (cardActs.size() == 4) {
                        repostNum = cardActs.get(1).text().replace(" ","").equals("转发")
                                ?"0":cardActs.get(1).text().replace(" ","").replace("转发","");
                        commentNum = cardActs.get(2).text().replace(" ","").equals("评论")
                                ?"0":cardActs.get(2).text().replace(" ","").replace("评论","");
                        awesome = cardActs.get(3).text().replace(" ","").equals("")
                                ?"0":cardActs.get(3).text().replace(" ","");
                    }
                    if( !text.replace(" ","").equals("")){
                        org.bson.Document document = new org.bson.Document("昵称",nickName)
                                .append("微博正文",text)
                                .append("来源",from)
                                .append("评论",commentList)
                                .append("转发数",repostNum)
                                .append("评论数",commentNum)
                                .append("点赞数",awesome);
                        havaParsedData.add(document);
                        logger.info(document.toString());
                    }
                }
            }

        }

    }
        public static void main (String[]args){
//        logger.info("王宇航");
        }
    }

