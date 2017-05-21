package cn.songguohulian.saygo.service;

/**
 *
 * @author Ziv
 * @data 2017/5/13
 * @time 20:11
 */
public class Tools {

    public static long lastClickTime;
    public static final String KEY_PROVINCE = "PROVINCE";
    public static final String KEY_CITY = "CITY";
    public synchronized static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
