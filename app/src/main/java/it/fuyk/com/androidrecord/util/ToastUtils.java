package it.fuyk.com.androidrecord.util;

import android.content.Context;
import android.widget.Toast;

/**
 * author: senseLo
 * date: 2018/4/8
 */

public class ToastUtils {
    private ToastUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;

    public static void shortToast(Context context, String msg) {
        if (isShow) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }
    }

    public static void longToas(Context context, String msg) {
        if (isShow) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG);
        }
    }
}
