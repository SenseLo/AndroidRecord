package it.fuyk.com.androidrecord.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * author: senseLo
 * date: 2018/4/8
 */

public class ScreenUtils {
    private ScreenUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /*
    * 获取屏幕的高度
    * */
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    /*
    * 获取屏幕的宽度
    * */
    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /*
    * 获取状态栏的高度
    * */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try{
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /*
    * 获取当前屏幕截图，包含状态栏
    * */
    public static Bitmap snapShotWidthStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bitmap, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /*
    * 获取当前屏幕截图，不包含状态栏
    * */
    public static Bitmap snapShotWithOutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bitmap, 0, statusBarHeight, width, height-statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }
}
