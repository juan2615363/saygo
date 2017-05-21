package cn.songguohulian.saygo.utils;

import android.content.Context;
import android.widget.Toast;

/**
 *
 * @author Ziv
 * @data 2017/5/17
 * @time 18:06
 *
 * Toast工具类
 */

public class ToastUtil {


    /**
     * @param context 上下文
     * @param content 内容
     * 短Toast
     */
    public static void toastShort(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param context 上下文
     * @param content 内容
     * 长Toast
     */
    public static void toastLong(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_LONG).show();
    }
}
