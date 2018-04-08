package it.fuyk.com.androidrecord.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * author: senseLo
 * date: 2018/4/8
 */

public class AppUtils {

    private AppUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /*
    * 获取应用程序名称
    * */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    * 获取应用程序版本信息
    * */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        }catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
