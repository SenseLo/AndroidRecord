package it.fuyk.com.androidrecord.ui.splash;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * author: senseLo
 * date: 2018/4/9
 */
/*
* ViewPager动画切换效果的定制
* 使用：
*   viewpager.setPageTransformer(true, new DepthPageTransformer());
* */

public class DepthPageTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.75f;
    private static final float MIN_ALPHA = 0.5f;

    /*
    * 参数：
    *   page：当前正在执行动画的View
    *   position：view的表示位置
    *
    *   position的取值范围：
    *   (-∞, -1)：表示最左边不可见的page
    *   [-1, 0]：表示从A切换到B页面时，A页面的posotion变化
    *   (0, 1]：表示从A切换到B页面时，B页面的position变化
    *   (1, +∞)：表示最右边不可见的page
    * */


    @Override
    public void transformPage(View page, float position) {
        int pageWidth = page.getWidth();
        int pageHeight = page.getHeight();

        if (position < -1) {
            page.setAlpha(0);
        }else if (position <= 1) { //向右滑动
            float scaleFactor = Math.max(MIN_SCALE, 1-Math.abs(position));
            float vertMargin = pageHeight*(1-scaleFactor)/2;
            float horzMargin = pageWidth*(1-scaleFactor)/2;
            if (position < 0) {
                page.setTranslationX(horzMargin-vertMargin/2);
            }else {
                page.setTranslationX(-horzMargin+vertMargin/2);
            }

            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            page.setAlpha(MIN_SCALE + (scaleFactor - MIN_SCALE)/(1-MIN_SCALE)*(1-MIN_ALPHA));
        }else {
            page.setAlpha(0);
        }
    }
}
