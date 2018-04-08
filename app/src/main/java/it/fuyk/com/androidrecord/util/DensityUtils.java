package it.fuyk.com.androidrecord.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * author: senseLo
 * date: 2018/4/8
 */

public class DensityUtils {
    private DensityUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /*
    * dp转px
    * */
    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    /*
    * sp转px
    * */
    public static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }

    /*
    * px转dp
    * */
    public static float px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return pxValue/scale;
    }

    /*
    * px转sp
    * */
    public static float px2sp(Context context, float pxValue) {
        return pxValue/context.getResources().getDisplayMetrics().scaledDensity;
    }
}
