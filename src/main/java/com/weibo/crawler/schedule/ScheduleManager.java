package com.weibo.crawler.schedule;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleManager {
//    public static  String startTime = "2018-7-19";
//    public static  String endTime = "2018-7-30";
    private static  Date startDate = null;
    private static Date endDate = null;
    private static int interval = 2;
    public ScheduleManager(int interval){
        this.interval = interval;
    }
    public  List<String> getFineTimeSlice(String startTime,String endTime){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        List<String> fineTimeSlices = new ArrayList<String>();
        try {
            startDate=simpleDateFormat.parse(startTime);
            endDate = simpleDateFormat.parse(endTime);
            long diff = endDate.getTime() - startDate.getTime();
            long diffDays  = diff / (24 * 60 * 60 * 1000);
            for(int i=0;i<Math.ceil(diffDays/(interval + 0.0) );i++){
                if(startDate.getTime() + (i+1)*interval*24*60*60*1000 > endDate.getTime()){
                    fineTimeSlices.add(
                            simpleDateFormat.format(startDate.getTime()+i*interval*24*60*60*1000)+","
                            + endTime);
                } else {
                    fineTimeSlices.add(
                            simpleDateFormat.format(startDate.getTime()+i*interval*24*60*60*1000)+","
                                    + simpleDateFormat.format(startDate.getTime()+ (i+1)*interval*24*60*60*1000));
                }
//
            }
        } catch(ParseException px) {
            px.printStackTrace();
        }
        return fineTimeSlices;
    }
    public static void main(String[] args) {
        ScheduleManager scheduleManager = new ScheduleManager(2);
        System.out.println(scheduleManager.getFineTimeSlice("2018-7-19","2018-7-30"));
    }
}

