package com.example.teacher.atndapp.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by teacher on 2016/10/22.
 */
public class DateUtil {

    public static String dateToString(final Date date){
        if(date == null){
            return null;
        }
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy/MM/dd HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
        return simpleDateFormat.format(date);
    }

    public static String dateToString(final Date date,final String format){
        if(date == null){
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
        return simpleDateFormat.format(date);
    }

}
