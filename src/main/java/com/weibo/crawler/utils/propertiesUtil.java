package com.weibo.crawler.utils;


import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.*;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class propertiesUtil {
    public static String userName =null;
    public static String password =null;
    public static String driverPath = null;
    public static String keywords = null;
    public static String startTime = null;
    public static String endTime = null;
    public static int interval = 0;
    public static Boolean isInsert = true;
    public static void readProperties(){
        Properties pros = new Properties();
        try {
            pros.load(new InputStreamReader(propertiesUtil.class.getClassLoader().getResourceAsStream("crawler.properties"), "UTF-8"));
            userName = pros.get("user_name").toString();
            password =pros.get("password").toString();
            driverPath = pros.get("driverPath").toString();
            keywords = pros.get("keywords").toString();
            startTime = pros.get("startTime").toString();
            endTime = pros.get("endTime").toString();
            interval = Integer.valueOf(pros.get("interval").toString());
            isInsert =  Boolean.valueOf(pros.get("is_Intert").toString());
//            System.out.println(pros.get("is_Intert").toString());
        } catch (IOException e) {

        }

    }

    public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
        readProperties();
        System.out.println(isInsert);
    }
}
