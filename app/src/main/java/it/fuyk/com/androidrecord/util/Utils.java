package it.fuyk.com.androidrecord.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * author: senseLo
 * date: 2018/3/26
 */

public class Utils {

    /*
    * 判断是否有网络连接
    * */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isConnected();
            }
        }
        return false;
    }

}
