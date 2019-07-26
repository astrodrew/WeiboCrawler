package com.weibo.crawler.selenium;

import com.weibo.crawler.parser.ParseData;
import com.weibo.crawler.utils.propertiesUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.logging.Logger;

import static com.weibo.crawler.utils.propertiesUtil.readProperties;

public class SeleniumOperator4Weibo {
    public  static Logger logger = (Logger) Logger.getLogger(String.valueOf(SeleniumOperator4Weibo.class));
    public static  WebDriver initalWebDriver(String driverPath){
        System.setProperty("webdriver.chrome.driver", driverPath);// chromedriver服务地址
        WebDriver driver = new ChromeDriver(); // 新建一个WebDriver 的对象，但是new 的是谷歌的驱动
        return  driver;
    }

    public static WebDriver autoLoginWeibo(String username,String password) throws InterruptedException {
        WebDriver driver = initalWebDriver("D:\\chrome\\chromedriver_win32\\chromedriver.exe");
        String url = "https://passport.weibo.cn/signin/login";
        driver.get(url); // 打开指定的网站
        Thread.sleep(5000);
        driver.findElement(By.id("loginName")).sendKeys(username);
        driver.findElement(By.id("loginPassword")).sendKeys(password);
        Thread.sleep(2000);
        WebElement loginAction =  driver.findElement(By.id("loginAction"));
        loginAction.click();
        return driver;
    }

    public  static  WebDriver ManuallyLoginWeibo(String driverPath) throws InterruptedException {
        WebDriver driver = initalWebDriver(driverPath);
        String url = "https://weibo.com/";
        driver.get(url); // 打开指定的网站
        logger.info("请输入验证码完成登录！");
        Thread.sleep(10000);
        while (true){
            try {
                driver.findElement(By.className("UG_box"));
                break;
            } catch (Exception e){
                Thread.sleep(1000);
            }
        }
        driver.findElement(By.id("loginname")).sendKeys(propertiesUtil.userName);
//        driver.findElement(By.className("W_input")).click();
//        driver.findElement(By.name("password")).click();
//        Thread.sleep(1000);
        driver.findElement(By.name("password")).sendKeys(propertiesUtil.password);
        while (true){
            try {
                driver.findElement(By.className("UG_box"));
            } catch (Exception e){
                logger.info("登录成功,即将进入解析");
                break;
            }
            Thread.sleep(1000);
        }
        return driver;
    }

    public static void main(String[] args) throws InterruptedException {
        autoLoginWeibo("drewabc@163.com","80231234");
    }

}
