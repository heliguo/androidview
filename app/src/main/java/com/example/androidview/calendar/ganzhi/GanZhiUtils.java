package com.example.androidview.calendar.ganzhi;

import java.util.Calendar;

/**
 * @author lgh on 2021/1/18 10:45
 * @description 当前年月日获取干支
 */
public class GanZhiUtils {
    /**
     * 对于年月日的天干地支
     */
    private static int year_ganZhi = -1;
    private static int month_ganZhi = -1;
    private static int day_ganZhi = -1;

    private static int currentYear, currentMonth, currentDay;

    /**
     * 关于阴历的相关信息
     */
    private static final int[] lunar_info = {0x04bd8, 0x04ae0, 0x0a570, 0x054d5,
            0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2, 0x04ae0,
            0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2,
            0x095b0, 0x14977, 0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40,
            0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970, 0x06566, 0x0d4a0,
            0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7,
            0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0,
            0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0, 0x0b550, 0x15355,
            0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0,
            0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263,
            0x0d950, 0x05b57, 0x056a0, 0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0,
            0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6, 0x095b0,
            0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46,
            0x0ab60, 0x09570, 0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50,
            0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0, 0x0c960, 0x0d954,
            0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0,
            0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0,
            0x0a5b0, 0x15176, 0x052b0, 0x0a930, 0x07954, 0x06aa0, 0x0ad50,
            0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530,
            0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6,
            0x0d250, 0x0d520, 0x0dd45, 0x0b5a0, 0x056d0, 0x055b2, 0x049b0,
            0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0};
    /**
     * 记录天干的信息
     */
    //若言戊癸何方发，甲寅之上好追求。5、10
    private final static String[] gan_info = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛",
            "壬", "癸"};
    //甲己之年丙作首1、6
    private final static String[] gan_info1 = {"丙", "丁", "戊", "己", "庚", "辛",
            "壬", "癸", "甲", "乙"};

    //乙庚之岁戊为头2、7
    private final static String[] gan_info2 = {"戊", "己", "庚", "辛",
            "壬", "癸", "甲", "乙", "丙", "丁"};

    //丙辛必定寻庚起3、8
    private final static String[] gan_info3 = {"庚", "辛",
            "壬", "癸", "甲", "乙", "丙", "丁", "戊", "己"};

    //丁壬壬位顺行流4、9
    private final static String[] gan_info4 = {
            "壬", "癸", "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛"};

    private final static String[] zhi_info = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未",
            "申", "酉", "戌", "亥"};

    private final static String[] month_zhi_info = {"寅", "卯", "辰", "巳", "午", "未",
            "申", "酉", "戌", "亥", "子", "丑"};
    private static int sSZhi = -1;
    private static int currentSolarDay = -1;


    /**
     * 获取农历某年的总天数
     *
     * @param year
     * @return
     */
    private static int daysOfYear(int year) {
        int sum = 348;
        for (int i = 0x8000; i > 0x8; i >>= 1) {
            sum += (lunar_info[year - 1900] & i) == 0 ? 0 : 1;
        }
        //获取闰月的天数
        int daysOfLeapMonth;
        if ((lunar_info[year - 1900] & 0xf) != 0) {
            daysOfLeapMonth = (lunar_info[year - 1900] & 0x10000) == 0 ? 29 : 30;
        } else {
            daysOfLeapMonth = 0;
        }
        return sum + daysOfLeapMonth;
    }

    /**
     * 初始化年月日对应的天干地支
     *
     * @param year
     * @param month
     * @param day
     */
    private static void initGanZhi(int year, int month, int day) {
        if (currentYear == year && currentMonth == month && currentDay == day) {
            return;
        }
        currentYear = year;
        currentMonth = month;
        currentDay = day;
        //获取现在的时间
        Calendar calendar_now = Calendar.getInstance();
        calendar_now.set(year, month - 1, day);
        long date_now = calendar_now.getTime().getTime();
        //获取1900-01-31的时间
        Calendar calendar_ago = Calendar.getInstance();
        calendar_ago.set(1900, 0, 31);
        long date_ago = calendar_ago.getTime().getTime();
        //86400000 = 24 * 60 * 60 * 1000
        long days_distance = (date_now - date_ago) / 86400000L;
        float remainder = (date_now - date_ago) % 86400000L;
        //余数大于0算一天
        if (remainder > 0) {
            days_distance += 1;
        }
        //都是从甲子开始算起以1900-01-31为起点
        //1899-12-21是农历1899年腊月甲子日  40：相差1900-01-31有40天
        day_ganZhi = (int) days_distance + 40;
        //1898-10-01是农历甲子月  14：相差1900-01-31有14个月
        month_ganZhi = 14;
        int daysOfYear = 0;
        int i;
        for (i = 1900; i < 2050 && days_distance > 0; i++) {
            daysOfYear = daysOfYear(i);
            days_distance -= daysOfYear;
            month_ganZhi += 12;
        }
        if (days_distance < 0) {
            days_distance += daysOfYear;
            i--;
            month_ganZhi -= 12;
        }
        //农历年份
        int myYear = i;
        //1864年是甲子年
        year_ganZhi = myYear - 1864;
        //哪个月是闰月
        int leap = lunar_info[myYear - 1900] & 0xf;
        boolean isLeap = false;
        int daysOfLeapMonth = 0;
        for (i = 1; i < 13 && days_distance > 0; i++) {
            //闰月
            if (leap > 0 && i == (leap + 1) && !isLeap) {
                isLeap = true;
                if ((lunar_info[myYear - 1900] & 0xf) != 0) {
                    daysOfLeapMonth = (lunar_info[myYear - 1900] & 0x10000) == 0 ? 29 : 30;
                } else {
                    daysOfLeapMonth = 0;
                }
                --i;
            } else {
                daysOfLeapMonth = (lunar_info[myYear - 1900] & (0x10000 >> i)) == 0 ? 29 : 30;
            }
            //设置非闰月
            if (isLeap && i == (leap + 1)) {
                isLeap = false;
            }
            days_distance -= daysOfLeapMonth;
            if (!isLeap) {
                month_ganZhi++;
            }
        }
        if (days_distance == 0 && leap > 0 && i == leap + 1 && !isLeap) {
            --month_ganZhi;
        }
        if (days_distance < 0) {
            --month_ganZhi;
        }
    }

    /**
     * 将年月日转化为天干地支的显示方法
     *
     * @param index
     * @return
     */
    private static String ganZhi(int index) {
        return gan_info[index % 10] + zhi_info[index % 12];
    }

    /**
     * 获取天干地支
     *
     * @return
     */
    public static String getGanZhi(int year, int month, int day) {
        initGanZhi(year, month, day);
        return ganZhi(year_ganZhi) + "年 " + ganZhi(month_ganZhi) + "月 " + ganZhi(day_ganZhi) + "日";
    }

    /**
     * 年 干支
     */
    public static String getGanZhiYear(int year, int month, int day) {
        initGanZhi(year, month, day);
        return ganZhi(year_ganZhi);
    }

    /**
     * 月 干支
     */
    public static String getGanZhiMonth(int year, int month, int day) {
        initGanZhi(year, month, day);
        //                return ganZhi(month_ganZhi);
        return getGanMonth(year, month, day) + getZhiMonth(year, month, day);
    }

    /**
     * 日 干支
     */
    public static String getGanZhiDay(int year, int month, int day) {
        initGanZhi(year, month, day);
        return ganZhi(day_ganZhi);
    }

    /**
     * 年 干
     */
    public static String getGanYear(int year, int month, int day) {
        initGanZhi(year, month, day);
        return gan_info[year_ganZhi % 10];
    }

    /**
     * 年 支
     */
    public static String getZhiYear(int year, int month, int day) {
        initGanZhi(year, month, day);
        return zhi_info[year_ganZhi % 12];
    }

    /**
     * 月 干
     */
    public static String getGanMonth(int year, int month, int day) {
        int actualMaximum = getDays(year, month);
        initGanZhi(year, month, actualMaximum);//当前月最后一天是什么年
        int monthG = month_ganZhi;//最后一天的月
        int yearG = year_ganZhi;//最后一天的年
        initGanZhi(year, month, day);
        for (int i = 1; i <= actualMaximum; i++) {
            String date;
            if (month < 10) {
                date = "0" + month + i;
            } else {
                date = String.valueOf(month) + i;
            }
            String solatName = SolarTermUtils.getSolatName(year, date);
            if (solatName != null && !solatName.equals("")) {
                sSZhi = SolarTermUtils.getSolar(solatName);//0-11;
                currentSolarDay = i;
                break;
            }
        }
        if (sSZhi == -1 || currentSolarDay == -1) {
            return null;
        }
        int yearGan;
        if (day < currentSolarDay) {
            yearGan = year_ganZhi % 10 + 1;
        } else {
            if (monthG != month_ganZhi) {
                yearGan = year_ganZhi % 10 + 1;
            } else
                yearGan = yearG % 10 + 1;
        }
        if (yearGan == 4 || yearGan == 9) {
            if (day < currentSolarDay) {
                if (sSZhi == 0) {
                    sSZhi = 9;
                    if (monthG != month_ganZhi) {
                        return gan_info4[sSZhi % 10];
                    }
                    return gan_info[sSZhi % 10];
                } else {
                    sSZhi--;
                }
            }
            return gan_info4[sSZhi % 10];
        } else if (yearGan == 1 || yearGan == 6) {
            if (day < currentSolarDay) {
                if (sSZhi == 0) {
                    sSZhi = 9;
                    if (monthG != month_ganZhi) {
                        return gan_info1[sSZhi % 10];
                    }
                    return gan_info2[sSZhi % 10];
                } else {
                    sSZhi--;
                }
            }
            return gan_info1[sSZhi % 10];
        } else if (yearGan == 2 || yearGan == 7) {
            if (day < currentSolarDay) {
                if (sSZhi == 0) {
                    sSZhi = 9;
                    if (monthG != month_ganZhi) {
                        return gan_info2[sSZhi % 10];
                    }
                    return gan_info3[sSZhi % 10];
                } else {
                    sSZhi--;
                }
            }
            return gan_info2[sSZhi % 10];
        } else if (yearGan == 3 || yearGan == 8) {
            if (day < currentSolarDay) {
                if (sSZhi == 0) {
                    sSZhi = 9;
                    if (monthG != month_ganZhi) {
                        return gan_info3[sSZhi % 10];
                    }
                    return gan_info4[sSZhi % 10];
                } else {
                    sSZhi--;
                }
            }
            return gan_info3[sSZhi % 10];
        } else if (yearGan == 5 || yearGan == 10) {
            if (day < currentSolarDay) {
                if (sSZhi == 0) {
                    sSZhi = 9;
                    if (monthG != month_ganZhi) {
                        return gan_info[sSZhi % 10];
                    }
                    return gan_info1[sSZhi % 10];
                } else {
                    sSZhi--;
                }
            }
            return gan_info[sSZhi % 10];
        }
        return null;
    }

    /**
     * 月 支
     */
    public static String getZhiMonth(int year, int month, int day) {
        initGanZhi(year, month, day);
        int actualMaximum = getDays(year, month);
        int zhi = -1;
        for (int i = 1; i <= actualMaximum; i++) {
            String date;
            if (month < 10) {
                date = "0" + month + i;
            } else {
                date = String.valueOf(month) + i;
            }
            String solatName = SolarTermUtils.getSolatName(year, date);
            if (solatName != null && !solatName.equals("")) {
                zhi = SolarTermUtils.getSolar(solatName);
                if (zhi == -1) {
                    return null;
                }
                if (day < i) {
                    if (zhi == 0) {
                        zhi = 11;
                    } else {
                        zhi--;
                    }
                }
                return month_zhi_info[zhi];
            }
        }

        if (zhi == -1) {
            return null;
        }
        return month_zhi_info[zhi];
    }

    /**
     * 日 干
     */
    public static String getGanDay(int year, int month, int day) {
        initGanZhi(year, month, day);
        return gan_info[day_ganZhi % 10];
    }

    /**
     * 日 支
     */
    public static String getZhiDay(int year, int month, int day) {
        initGanZhi(year, month, day);
        return zhi_info[day_ganZhi % 12];
    }

    //获取当前年月有多少天
    private static int getDays(int year, int month) {

        int days = 0;
        if (month != 2) {
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    days = 31;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    days = 30;
            }
        } else {
            // 闰年
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
                days = 29;
            else
                days = 28;
        }
        return days;
    }

}
