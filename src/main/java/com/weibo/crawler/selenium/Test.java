package com.weibo.crawler.selenium;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;



public class Test {

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver_win32\\chromedriver.exe");// chromedriver服务地址
        WebDriver driver = new ChromeDriver(); // 新建一个WebDriver 的对象，但是new 的是谷歌的驱动
        String url = "http://www.baidu.com";
        driver.get(url); // 打开指定的网站

        //driver.navigate().to(url); // 打开指定的网站

        /*
         *
         * driver.findElement(By.id("kw")).sendKeys(new String[] { "hello" });//
         * 找到kw元素的id，然后输入hello driver.findElement(By.id("su")).click(); // 点击按扭
         */
        try {
            /**
             * WebDriver自带了一个智能等待的方法。 dr.manage().timeouts().implicitlyWait(arg0, arg1）；
             * Arg0：等待的时间长度，int 类型 ； Arg1：等待时间的单位 TimeUnit.SECONDS 一般用秒作为单位。
             */
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //获取当前浏览器的信息
        System.out.println("Title:" + driver.getTitle());
        System.out.println("currentUrl:" + driver.getCurrentUrl());

        //执行js脚本
        String jString = "alert('122')";
        ((JavascriptExecutor) driver).executeScript(jString);

        //获取单个元素
        WebElement element = driver.findElement(By.id("wrapper"));
        System.out.println(element.getAttribute("class"));

        //获取多个元素
        List<WebElement> elList = driver.findElements(By.tagName("input"));
        for (WebElement e : elList) {
            System.out.println("获取多个元素:"+e.getAttribute("name"));
        }

        //定位层级元素
        WebElement e = driver.findElement(By.cssSelector("#qrcode-item qrcode-item-1"));
        List<WebElement> list = e.findElements(By.tagName("div"));
        for (WebElement e1 : list) {
            System.out.println("定位层级元素:"+e1.getAttribute("class"));
        }



        //获取当前的窗口
        String currentWindow = driver.getWindowHandle();

        //获取所有的窗口
        Set<String>  handles = driver.getWindowHandles();

        //遍历窗口
        Iterator<String> iterator = handles.iterator();
        while (iterator.hasNext()) {
            if (currentWindow == iterator.next())
                continue;
            //跳转到弹出的窗口
            WebDriver driver2 = driver.switchTo().window(iterator.next());
            driver2.getTitle();

        }


        //处理弹出框
        Alert alert = driver.switchTo().alert();
        alert.getText();
        alert.dismiss();//相当于点击取消


        Alert confirm = driver.switchTo().alert();
        confirm.getText();
        confirm.accept();//相当于点击确认


        Alert prompt = driver.switchTo().alert();
        prompt.getText();
        prompt.accept();
        prompt.sendKeys("测试1");//输入值

        //处理下拉列表
        Select select = new Select(driver.findElement(By.id("select")));
        select.selectByIndex(1);
        select.selectByValue("西安");
        select.selectByVisibleText("咸阳");
        //获取下拉框的全部选项
        List<WebElement> list2 = select.getOptions();
        for (WebElement webElement : list2) {
            webElement.click();//点击下拉框
        }

        //处理cookie
        //添加一个cookie
        Cookie cookie = new Cookie("COOKIE", "NAME","D://COOKIES");
        driver.manage().addCookie(cookie);

        //获取cookies
        Set<Cookie> set = driver.manage().getCookies();
        Iterator<Cookie> iterator2 = set.iterator();
        while (iterator2.hasNext()) {
            Cookie c = iterator2.next();
            c.getName();
            c.getValue();
            c.getPath();

        }

        driver.manage().deleteAllCookies();
        driver.manage().deleteCookie(cookie);
        driver.manage().deleteCookieNamed("COOKIE");


        //等待加载完页面
        try {
            driver.manage().timeouts().wait(1);
            driver.manage().timeouts().implicitlyWait(1,TimeUnit.SECONDS);//等待界面加载完
        } catch (InterruptedException e2) {

            e2.printStackTrace();
        }

        //模拟鼠标和键盘操作
        Actions action = new Actions(driver);
        action.click();
        action.dragAndDrop(element, e);
        action.sendKeys(element,"12222").perform();
        action.click(element);
        action.keyDown(currentWindow);




        // driver.quit();// 退出浏览器

        /**
         * dr.quit()和dr.close()都可以退出浏览器,简单的说一下两者的区别：第一个close，
         * 如果打开了多个页面是关不干净的，它只关闭当前的一个页面。第二个quit，
         * 是退出了所有Webdriver所有的窗口，退的非常干净，所以推荐使用quit最为一个case退出的方法。
         */

    }

}