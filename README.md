# WeiboCrawler

## 简介

WeiboCrawler 是基于微博搜索功能用来采集微博数据的爬虫，利用 selenium 模拟微博登录，获取微博页面，然后使用Jsoup进行页面解析，获取微博的六个字段，包括【作者（昵称），来源，微博正文,评论 ,转发数，点赞数，评论数】。

## 准备

 1. chrome driver，需要与本机chrome 浏览器版本相匹配

    [下载地址]: http://npm.taobao.org/mirrors/chromedriver/

 2. mongoDB用于数据持久化存储

 3. 微博账号（最好为不常用的账号，防止被封号损失）

## 操作示例

```java
├─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─weibo
│  │  │          └─crawler
│  │  │              ├─download
│  │  │              │      DownloadHtmlSource.java
│  │  │              │      
│  │  │              ├─monitor
│  │  │              │      MonitorManager.java
│  │  │              │      
│  │  │              ├─parser
│  │  │              │      ParseByJsoup.java
│  │  │              │      ParseData.java
│  │  │              │      
│  │  │              ├─persistence
│  │  │              │      InsertData.java
│  │  │              │      MongoHelper.java
│  │  │              │      
│  │  │              ├─schedule
│  │  │              │      ScheduleManager.java
│  │  │              │      
│  │  │              ├─selenium
│  │  │              │      SeleniumOperator4Weibo.java
│  │  │              │      Test.java
│  │  │              │      
│  │  │              ├─ui
│  │  │              │      UIManager.java
│  │  │              │      
│  │  │              └─utils
│  │  │                      FileOperaterUtil.java
│  │  │                      propertiesUtil.java
│  │  │                      
│  │  └─resources
│  │          crawler.properties（需要自己添加）
│  │          log4j.properties
│  │          
│  └─test
│      └─java

```

1.在resources中添加crawler.properties文件。

```
    #chrome driver的路径
    driverPath = ######
    #微博的用户名和密码
    user_name = #######
    password = #####
    #输入的搜索信息，keywords逗号分隔
    keywords=term1,term2
    #起始时间，结束时间均为 yyyy-mm-dd格式
    startTime=2018-7-19
    endTime=2018-7-30
    #interval的单位是天，标志微博搜索的时间粒度，默认为2
    interval=2
    #是否存储
    is_intert=false
```

2.MongoDB安装，不需要存储可略过

3.运行UIManager，按照控制台输出的提示完成数据爬取

​	![效果图](https://github.com/astrodrew/WeiboCrawler/blob/master/console.png)

​	![效果图](https://github.com/astrodrew/WeiboCrawler/blob/master/db.png)

