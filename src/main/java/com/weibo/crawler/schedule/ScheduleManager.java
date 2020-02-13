package com.weibo.crawler.schedule;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
//            System.out.println(diffDays);
            for(int i=0;i<Math.ceil(diffDays/(interval + 0.0) );i++){
                if(startDate.getTime() + (i+1)*interval*24*60*60*1000 > endDate.getTime()){
                    fineTimeSlices.add(simpleDateFormat.format(addDay(startDate,interval*i))
                            +","
                            + endTime);
                } else {
                    fineTimeSlices.add(
                            simpleDateFormat.format(addDay(startDate,interval*i))+","
                                    +  simpleDateFormat.format(addDay(startDate,interval*(i+1))));
                }
//
            }
        } catch(ParseException px) {
            px.printStackTrace();
        }
        return fineTimeSlices;
    }

    public  Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DATE, num);
        return startDT.getTime();
    }

    public static void main(String[] args) throws ParseException {
        ScheduleManager scheduleManager = new ScheduleManager(2);
        System.out.println(scheduleManager.getFineTimeSlice("2019-12-01","2020-02-10"));


//        Date a = addDay(startDate, 80);//调取方法  天数+1
//
//        SimpleDateFormat forDate = new SimpleDateFormat("yyyy-MM-dd");
//        String dateString = forDate.format(a);//格式化
//        System.out.println(dateString);

    }

}

