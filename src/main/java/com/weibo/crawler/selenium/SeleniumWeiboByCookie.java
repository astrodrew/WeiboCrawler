package com.weibo.crawler.selenium;

import java.util.Set;

import com.weibo.crawler.utils.propertiesUtil;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;
public class SeleniumWeiboByCookie {
        /**
         * 获取新浪微博的cookie，这个方法针对weibo.cn有效，对weibo.com无效 weibo.cn以明文形式传输数据，请使用小号
         *
         * @param username 新浪微博用户名
         * @param password 新浪微博密码
         * @return
         * @throws Exception
         */
        public static String userName =null;
        public static String password =null;
        public static String getSinaCookie(String username, String password) throws Exception {
            StringBuilder sb = new StringBuilder();
            HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.INTERNET_EXPLORER);
            driver.setJavascriptEnabled(true);
            driver.get("https://passport.weibo.cn/signin/login");
            driver.executeScript("document.getElementById('loginWrapper').style.display = 'block'");
            WebElement mobile = driver.findElementByCssSelector("input#loginName");
            mobile.sendKeys(username);
            WebElement pass = driver.findElementByCssSelector("input#loginPassword");
            pass.sendKeys(password);
            WebElement submit = driver.findElementByCssSelector("a#loginAction");

            submit.click();
            String result = concatCookie(driver);
            System.out.println("Get Cookie: " + result);
            driver.close();
//            System.out.println("---测 试-------");
//            System.out.println(result);
//            System.exit(0);

            if (result.contains("SUB")) {
                return result;
            } else {
                throw new Exception("weibo login failed");
            }
        }

        public static String concatCookie(HtmlUnitDriver driver) {
            Set<Cookie> cookieSet = driver.manage().getCookies();
            StringBuilder sb = new StringBuilder();
            for (Cookie cookie : cookieSet) {
                sb.append(cookie.getName() + "=" + cookie.getValue() + ";");
            }
            String result = sb.toString();
            return result;
        }

    public static void main(String[] args) throws Exception {

            propertiesUtil.readProperties();
            System.out.println(propertiesUtil.userName);
            System.out.println(propertiesUtil.password);
            System.exit(0);
            String cookie = getSinaCookie(propertiesUtil.userName,propertiesUtil.password);

            System.out.println(cookie);
    }
}
