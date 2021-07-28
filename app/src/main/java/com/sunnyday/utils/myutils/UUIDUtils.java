package com.sunnyday.listviewdemo;

import java.util.UUID;

/**
 * Created by sunnyDay on 2021/7/20 14:39
 */
class UUIDUtils {
    /**
     * 获取随机uuid（32位16进制字符组成）
     */
    public static String getRandomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
