package com.weibo.crawler.selenium;

import com.weibo.crawler.parser.ParseData;
import com.weibo.crawler.utils.propertiesUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
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

    public static void clickComment(WebDriver driver ) throws InterruptedException {
        List<WebElement> elements = driver.findElements(By.cssSelector("a[action-type='feed_list_comment']"));
        for(WebElement element : elements){
            try {
                element.click();
            } catch (org.openqa.selenium.StaleElementReferenceException e){
                logger.info("微博页面评论按键发生错误");
            }
            Thread.sleep(500);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        propertiesUtil.readProperties();
        WebDriver driver =  ManuallyLoginWeibo("D:\\chrome\\chromedriver_win32\\chromedriver.exe");
        Thread.sleep(1000);
        driver.get("https://s.weibo.com/weibo?q=%E8%8C%83%E5%86%B0%E5%86%B0%20%E5%81%B7%E7%A8%8E%20%E6%BC%8F%E7%A8%8E&typeall=1&suball=1&timescope=custom:2018-07-29:2018-7-30&Refer=g&page=2");
        Thread.sleep(5000);
        clickComment(driver);
        Thread.sleep(6000);
        String html = driver.getPageSource();
        Document page = Jsoup.parse(html);
//        Elements elements = page.select("div.card");
//        for (Element element : elements) {
//            Elements comments = element.select("div.list > div.card-review.s-ptb10");
//            for(Element comment:comments){
//                String commentUser = comment.select("div.txt > a.name").text();
//                String content = comment.select("div.txt").text().replace(commentUser+" ：","").trim();
//                String from = comment.select("div.fun > p.from").text();
//                System.out.println(commentUser + "\n" + content + "\n" + from);
//                System.out.println("\n");
//            }
//        }
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        System.out.println(tabs.size());
    }
}
