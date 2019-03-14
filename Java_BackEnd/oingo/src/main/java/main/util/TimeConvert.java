package main.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TimeConvert {

    public static String getTimeNow(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sdf.format(new Date());
        return now;
    }

    public static Date convertToDate(String time) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.parse(time);
    }

    public static String convertToString(Date time) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(time);
    }
}
