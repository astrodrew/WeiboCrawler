# WeiboCrawler

## 简介

WeiboCrawler 是基于微博搜索功能用来采集微博数据的爬虫，利用 selenium 模拟微博登录，获取微博页面，然后使用Jsoup进行页面解析，获取微博的六个字段，包括【作者（昵称），来源，微博正文，转发数，点赞数，评论数】。

## 准备

	1.	chrome driver，需要与本机chrome 浏览器版本相匹配
 	2.	