package com.funny.dices.android.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by biaozhang on 2019/10/19 14:57
 * <p>
 * double check style singleton
 */
public class SPUtil {

    private static final String FILE_NAME = "sp_config";// 文件名称
    private volatile static SharedPreferences mSP = null;

    private SPUtil() {
    }

    private static synchronized SharedPreferences getInstance(Context context) {
        if (mSP == null) {
            synchronized ("lock") {
                if (mSP == null) {
                    mSP = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
                }
            }
        }
        return mSP;
    }

    public static void putBoolean(String key, boolean value, Context context) {
        SPUtil.getInstance(context).edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key, boolean defValue, Context context) {
        return SPUtil.getInstance(context).getBoolean(key, defValue);
    }

    public static void putString(String key, String value, Context context) {
        SPUtil.getInstance(context).edit().putString(key, value).apply();
    }

    public static String getString(String key, String defValue, Context context) {
        return SPUtil.getInstance(context).getString(key, defValue);
    }

    public static void putInt(String key, int value, Context context) {
        SPUtil.getInstance(context).edit().putInt(key, value).apply();
    }

    public static int getInt(String key, int defValue, Context context) {
        return SPUtil.getInstance(context).getInt(key, defValue);
    }

    /**
     * 移除某个key值已经对应的值
     */
    public static void remove(String key, Context context) {
        SPUtil.getInstance(context).edit().remove(key).apply();
    }

    /**
     * 清除所有内容
     */
    public static void clear(Context context) {
        SPUtil.getInstance(context).edit().clear().apply();
    }
}
