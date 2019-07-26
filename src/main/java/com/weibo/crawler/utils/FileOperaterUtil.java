package com.weibo.crawler.utils;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class FileOperaterUtil {
    public  static  String readFromFile(String filePath,String charset) throws IOException {
        File fileObj = new File(filePath);
        FileInputStream fis = new FileInputStream(fileObj);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder stringBuilder = new StringBuilder();
        String temp = null;
        int lineCounter = 0;
        while ((temp = br.readLine()) != null) {
            if(lineCounter > 0){
                stringBuilder.append("\n");
            }
            lineCounter++;
            stringBuilder.append(temp);
        }
        br.close();
        return stringBuilder.toString();
    }

    public  static HashMap<String,Object> getSearchInputFromFile(String filePath) throws IOException {
        File fileObj = new File(filePath);
        FileInputStream fis = new FileInputStream(fileObj);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        HashMap<String,Object> map = new HashMap<String, Object>();
        String temp = null;
        while ((temp = br.readLine()) != null) {
            temp = temp.trim();
            if(temp.length() > 0){
                String[] keyAndValue = temp.split("\\t");
                if(keyAndValue[0].equals("keywords")){
                    map.put(keyAndValue[0],keyAndValue[1]);
                }  else{
                    map.put(keyAndValue[0],keyAndValue[1]);
                }
            }
        }
        br.close();
        return  map;
    }
    public  static void main(String args[]) throws IOException {
        System.out.println(getSearchInputFromFile("search_input.txt"));
    }
}
