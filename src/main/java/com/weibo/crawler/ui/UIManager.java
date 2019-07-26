package com.weibo.crawler.ui;


import com.weibo.crawler.download.DownloadHtmlSource;
import com.weibo.crawler.monitor.MonitorManager;
import com.weibo.crawler.parser.ParseByJsoup;
import com.weibo.crawler.parser.ParseData;
import com.weibo.crawler.persistence.InsertData;
import com.weibo.crawler.schedule.ScheduleManager;
import com.weibo.crawler.selenium.SeleniumOperator4Weibo;
import com.weibo.crawler.utils.FileOperaterUtil;
import com.weibo.crawler.utils.propertiesUtil;
import org.openqa.selenium.WebDriver;

import javax.management.monitor.Monitor;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    本爬虫针对搜索词以及时间段，进行微博细粒度的微博数据爬取
 */
public class UIManager {
    /*
    或许搜索的关键词
     */
    public static void main(String[] args) throws InterruptedException, IOException {
//        HashMap inputMap = FileOperaterUtil.getSearchInputFromFile("search_input.txt");
        //读取配置信息
        propertiesUtil.readProperties();
        // selenium 模拟浏览器登录
        WebDriver webDriver = SeleniumOperator4Weibo.ManuallyLoginWeibo();
        //监控日志
        MonitorManager monitorManager = new MonitorManager();
        monitorManager.start();
        //时间片调度，下载
        ScheduleManager scheduleManager = new ScheduleManager(propertiesUtil.interval);
        List<String> fineTimeSlices = scheduleManager.getFineTimeSlice(propertiesUtil.startTime,propertiesUtil.endTime);
        for (int i=0;i<fineTimeSlices.size();i++){
            String startTime = fineTimeSlices.get(i).split(",")[0];
            String endTime = fineTimeSlices.get(i).split(",")[1];
            DownloadHtmlSource downloadHtmlSource = new DownloadHtmlSource(webDriver,"范冰冰,偷税,漏税",startTime,endTime,i);
            downloadHtmlSource.start();
        }
        //解析
        ParseData parseData = new ParseByJsoup(webDriver);
        ((ParseByJsoup) parseData).start();
        //存储
        InsertData insertData = new InsertData();
        insertData.start();
    }
}
