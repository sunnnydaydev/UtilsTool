package com.test.prophesy.master.utils;


import com.orhanobut.logger.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by biaozhang on 2019/11/27 19:30
 * <p>
 * 计算用户的  年龄、星座、生肖工具类
 */
public class CalculationUtils {
    public static String constellation(int month, int day) {
        Logger.d("星座计算month:" + month + "星座计算day:" + day);
        String constellation = "";
        if (month == 1 && day >= 20 || month == 2 && day <= 18) {
            constellation = "水瓶座";
        }
        if (month == 2 && day >= 19 || month == 3 && day <= 20) {
            constellation = "双鱼座";
        }
        if (month == 3 && day >= 21 || month == 4 && day <= 19) {
            constellation = "白羊座";
        }
        if (month == 4 && day >= 20 || month == 5 && day <= 20) {
            constellation = "金牛座";
        }
        if (month == 5 && day >= 21 || month == 6 && day <= 21) {
            constellation = "双子座";
        }
        if (month == 6 && day >= 22 || month == 7 && day <= 22) {
            constellation = "巨蟹座";
        }
        if (month == 7 && day >= 23 || month == 8 && day <= 22) {
            constellation = "狮子座";
        }
        if (month == 8 && day >= 23 || month == 9 && day <= 22) {
            constellation = "处女座";
        }
        if (month == 9 && day >= 23 || month == 10 && day <= 23) {
            constellation = "天秤座";
        }
        if (month == 10 && day >= 24 || month == 11 && day <= 22) {
            constellation = "天蝎座";
        }
        if (month == 11 && day >= 23 || month == 12 && day <= 21) {
            constellation = "射手座";
        }
        if (month == 12 && day >= 22 || month == 1 && day <= 19) {
            constellation = "摩羯座";
        }

        return constellation;
    }

    /**
     * 通过生日计算属相
     *
     * @param date
     * @return
     */
    public static String getZodiacSign(Date date) {
        DateFormat format = new SimpleDateFormat("yyyy", Locale.CHINA);
        int year = Integer.parseInt(format.format(date));
        if (year < 1900) {
            return "未知";
        }
        int start = 1900;
        String[] years = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊",
                "猴", "鸡", "狗", "猪"};
        return years[(year - start) % years.length];
    }

    /**
     * 获得用户年龄
     *
     * @param date 用户出生日期
     */
    public static int getAge(Date date) {
        DateFormat format = new SimpleDateFormat("yyyy", Locale.CHINA);
        format.format(date);
        return getCurrentYear() - Integer.parseInt(format.format(date));
    }


    /**
     * 获得当前年
     * 例如：2019-11-27 return 2019
     */
    private static int getCurrentYear() {
        Date DATE = new Date();
        DateFormat format = new SimpleDateFormat("yyyy", Locale.CHINA);
        Logger.d("当前年：" + format.format(DATE));
        return Integer.parseInt(format.format(DATE));
    }


}
