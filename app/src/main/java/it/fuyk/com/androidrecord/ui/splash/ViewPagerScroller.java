package it.fuyk.com.androidrecord.ui.splash;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * author: senseLo
 * date: 2018/4/9
 */

/*
* 使用：
*     ViewPagerScroller pagerScroller = new ViewPagerScroller(getActivity());
        pagerScroller.setScrollDuration(1000);//设置时间，时间越长，速度越慢
        pagerScroller.initViewPagerScroll(mViewPager);
* */

public class ViewPagerScroller extends Scroller {
    private int DEFAULT_DURATION = 900;
    public ViewPagerScroller(Context context) {
        this(context, null);
    }

    public ViewPagerScroller(Context context, Interpolator interpolator) {
        this(context, interpolator, false);
    }

    public ViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, DEFAULT_DURATION);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, DEFAULT_DURATION);
    }

    /*
    * 设置切换一个页面的时长，时间越长，切换速度越慢
    * */
    public void setDuration(int duration) {
        this.DEFAULT_DURATION = duration;
    }

    /*
    * 该方法在ViewPager初始化的时候调用即可
    * */
    public void initViewPagerScroller(ViewPager viewPager) {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroller.set(viewPager, this);
        }catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
