package com.weibo.crawler.parser;

import org.openqa.selenium.WebDriver;

import java.util.HashMap;

public interface ParseData  {
    void   parse(WebDriver driver) throws InterruptedException;
}
