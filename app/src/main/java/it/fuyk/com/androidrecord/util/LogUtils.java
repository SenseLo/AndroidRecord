package it.fuyk.com.androidrecord.util;

import android.util.Log;

/**
 * author: senseLo
 * date: 2018/4/8
 */

/*
* 日志工具类
* */
public class LogUtils {
    private LogUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }
    public static boolean isDebug = true;
    private static final String TAG = "SenseLo";

    public static void i(String msg) {
        if (isDebug) {
            Log.i(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (isDebug) {
            Log.d(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (isDebug) {
            Log.e(TAG, msg);
        }
    }

    public static void v(String msg) {
        if (isDebug) {
            Log.v(TAG, msg);
        }
    }
}
