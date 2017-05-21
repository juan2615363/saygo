package cn.songguohulian.saygo.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *
 * @author Ziv
 * @data 2017/5/17
 * @time 18:05
 *
 * 判断登录状态
 */

public class SpTools {
    /**
     * 得到登陆状态
     *
     * @param context 上下文
     * @param key
     * @return
     */
    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("saygo", Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    /**
     * 设置登陆状态
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("saygo", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }
}
