package com.yu.reggie.function;

import java.util.Calendar;
import java.util.List;

public class Methods {
    public static void checkSqlLineSize(List<String> line, int expectSize) throws SqlLineSizeExpectation {
        if (line.size() != expectSize) {
            throw new SqlLineSizeExpectation(line);
        }
    }

    //String 转化为 Calendar
    public static Calendar changeString2Calendar(String time) {
        Calendar calendar = Calendar.getInstance();
        int year = Integer.parseInt(time.substring(0, 4));
        int month = Integer.parseInt(time.substring(5, 7));
        int day = Integer.parseInt(time.substring(8, 10));
        int hour = Integer.parseInt(time.substring(11, 13));
        int minute = Integer.parseInt(time.substring(14, 16));
        int second = Integer.parseInt(time.substring(17, 19));
        calendar.set(year, month - 1, day, hour, minute, second);
        return calendar;
    }

    //Calendar 转化为 String
    public static String showSimpleDatetime(Calendar c) {
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        int sec = c.get(Calendar.SECOND);
        return String.format("%s-%s-%s %s:%s:%s", year, month, day, hour, min, sec);
    }


}