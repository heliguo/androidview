package com.example.androidview.mpandroidchart;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author lgh on 2021/1/14 20:39
 * @description 日历工具 基准类java.util.Calendar
 */
@SuppressLint("SimpleDateFormat")
public class CalendarsUtils {

    /**
     * 获取年份
     */
    public static int getYear(Calendar calendar) {
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取月 可能需要+1
     */
    public static int getMonth(Calendar calendar) {
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日
     */
    public static int getDay(Calendar calendar) {
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获取是本月的第几天
     */
    public static int getDayOfMonth(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取是本周的第几天
     */
    public static int getDayOfWeek(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取是本年的第几天
     */
    public static int getDayOfYear(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 获取是本月的第几周
     */
    public static int getWeekOfMonth(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
    }

    /**
     * 获取是本年的第几周
     */
    public static int getWeekOfYear(Calendar calendar) {
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取今天是周几
     * 周日-周六：1-7
     */
    public static int getWeek(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取当前小时 12小时制
     */
    public static int getCurrentHour_Twelve(Calendar calendar) {
        return calendar.get(Calendar.HOUR);
    }

    /**
     * 获取当前小时 24小时制
     */
    public static int getCurrentHour_Twenty(Calendar calendar) {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 当前是不是上午
     */
    public static boolean getCurrentAM(Calendar calendar) {
        return calendar.get(Calendar.AM_PM) == Calendar.AM;
    }

    /**
     * 当前是不是下午
     */
    public static boolean getCurrentPM(Calendar calendar) {
        return calendar.get(Calendar.AM_PM) == Calendar.PM;
    }

    /**
     * 获取当前分钟
     */
    public static int getCurrentMin(Calendar calendar) {
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 获取当前秒
     */
    public static int getCurrentSecond(Calendar calendar) {
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 获取当前毫秒
     */
    public static int getCurrentMillSecond(Calendar calendar) {
        return calendar.get(Calendar.MILLISECOND);
    }

    /**
     * calendar to "2020-12-12"
     */
    public static String calendar2String(Calendar calendar, String format) {
        return millSecond2String(calendar.getTimeInMillis(), format);
    }

    /**
     * 毫秒转自定义格式string
     */
    public static String millSecond2String(long millSeconds, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(new Date(millSeconds));
    }

    /**
     * 字符串转毫秒时间戳
     */
    public static long string2millSecond(String time, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 时间字符串获取年
     * stringYear("2021-10-12", "yyyy-MM-dd")--->2021
     */
    public static int stringYear(String time, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(time).getYear() + 1900;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 时间字符串获取月
     * stringYear("2021-10-12", "yyyy-MM-dd")--->10
     */
    public static int stringMonth(String time, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(time).getMonth() + 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 时间字符串获取天
     * stringYear("2021-10-12", "yyyy-MM-dd")--->12
     */
    public static int stringDay(String time, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(time).getDay();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * "2021-10-12" to calendar
     */
    public static Calendar string2Calendar(String date, String format) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(string2millSecond(date, format));
        return instance;
    }

    public static String dateFormat(String date, String format1, String format2) {
        long millSecond = string2millSecond(date, format1);
        return millSecond2String(millSecond,format2);
    }

    /**
     * 毫秒时间戳单位转换（单位：unit）
     *
     * @param milliseconds 毫秒时间戳
     * @param unit         <ul>
     *                     <li>{@link TimeUnit#MILLISECONDS}: 毫秒</li>
     *                     <li>{@link TimeUnit#SECONDS }: 秒</li>
     *                     <li>{@link TimeUnit#MINUTES }: 分</li>
     *                     <li>{@link TimeUnit#HOURS}: 小时</li>
     *                     <li>{@link TimeUnit#DAYS }: 天</li>
     *                     </ul>
     * @return unit时间戳
     */
    private static long milliseconds2Unit(long milliseconds, TimeUnit unit) {
        switch (unit) {
            case MILLISECONDS:
                return milliseconds;
            case SECONDS:
                return milliseconds / 1000;
            case MINUTES:
                return milliseconds / 60000;
            case HOURS:
                return milliseconds / 3600000;
            case DAYS:
                return milliseconds / 86400000;
        }
        return -1;
    }

    /**
     * 获取两个时间差（单位：unit）
     * <p>time1和time2格式都为format</p>
     *
     * @param time0  时间字符串1
     * @param time1  时间字符串2
     * @param unit   <li>{@link TimeUnit#MILLISECONDS}: 毫秒</li>
     *               <li>{@link TimeUnit#SECONDS }: 秒</li>
     *               <li>{@link TimeUnit#MINUTES }: 分</li>
     *               <li>{@link TimeUnit#HOURS}: 小时</li>
     *               <li>{@link TimeUnit#DAYS }: 天</li>
     * @param format 时间格式
     * @return unit时间戳
     */
    public static long getIntervalTime(String time0, String time1, TimeUnit unit, String format) {
        return Math.abs(milliseconds2Unit(string2millSecond(time0, format)
                - string2millSecond(time1, format), unit));
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param unit   <li>{@link TimeUnit#MINUTES}: 毫秒</li>
     *               <li>{@link TimeUnit#SECONDS }:秒</li>
     *               <li>{@link TimeUnit#MINUTES }:分</li>
     *               <li>{@link TimeUnit#HOURS}:小时</li>
     *               <li>{@link TimeUnit#DAYS }:天</li>
     * @param format 时间格式
     * @return unit时间戳
     */
    public static long getIntervalByNow(String time, TimeUnit unit, String format) {
        return getIntervalTime(millSecond2String(System.currentTimeMillis(), format), time, unit, format);
    }


    /**
     * 增加几天
     */
    public static Calendar addDate(Calendar calendar, int dayNum) {
        calendar.add(Calendar.DAY_OF_YEAR, dayNum);
        return calendar;
    }

    /**
     * 增加几个月
     */
    public static Calendar addMonth(Calendar calendar, int monthNum) {
        calendar.add(Calendar.MONTH, monthNum);
        return calendar;
    }

    /**
     * 增加几年
     */
    public static Calendar addYear(Calendar calendar, int yearNum) {
        calendar.add(Calendar.YEAR, yearNum);
        return calendar;
    }

    /**
     * 增加年月日
     */
    public static Calendar addYear_Month_Day(Calendar calendar, int yearNum, int monthNum, int dayNum) {
        calendar.add(Calendar.DAY_OF_YEAR, dayNum);
        calendar.add(Calendar.MONTH, monthNum);
        calendar.add(Calendar.MONTH, monthNum);
        return calendar;
    }


    /**
     * 两个日期是否同月
     */
    public static boolean isEqualsMonth(Calendar date1, Calendar date2) {
        return date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) && date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH);
    }


    /**
     * 第一个是不是第二个的上一个月
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isLastMonth(Calendar date1, Calendar date2) {
        if (date2.get(Calendar.MONTH) == Calendar.JANUARY) {//一月
            return date1.get(Calendar.YEAR) + 1 == date2.get(Calendar.YEAR) && date1.get(Calendar.MONTH) == Calendar.DECEMBER;
        }
        return date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) &&
                date1.get(Calendar.MONTH) + 1 == date2.get(Calendar.MONTH);
    }


    /**
     * 第一个是不是第二个的下一个月
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isNextMonth(Calendar date1, Calendar date2) {
        if (date2.get(Calendar.MONTH) == Calendar.DECEMBER) {//一月
            return date1.get(Calendar.YEAR) - 1 == date2.get(Calendar.YEAR) && date1.get(Calendar.MONTH) == Calendar.JANUARY;
        }
        return date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) &&
                date1.get(Calendar.MONTH) - 1 == date2.get(Calendar.MONTH);
    }

    public static String getWeekByDate(String date) {//2021-10-21
        Calendar calendar = Calendar.getInstance();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            calendar.setTime(format.parse(date));
            int dayOfWeek = getWeek(calendar);
            Log.d("TAG---", dayOfWeek + "");
            return "星期" + weekChar(dayOfWeek);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String weekChar(int week) {
        if (week == 1) {
            return "日";
        } else if (week == 2) {
            return "一";
        } else if (week == 3) {
            return "二";
        } else if (week == 4) {
            return "三";
        } else if (week == 5) {
            return "四";
        } else if (week == 6) {
            return "五";
        } else if (week == 7) {
            return "六";
        }
        return null;
    }

}
