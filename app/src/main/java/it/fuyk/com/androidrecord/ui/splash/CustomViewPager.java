package it.fuyk.com.androidrecord.ui.splash;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * author: senseLo
 * date: 2018/4/9
 */

public class CustomViewPager extends ViewPager {
    private MyPagerAdapter mPagerAdapter;
    private int mTotal; //总数
    private int mCurrentPos; //当前位置
    public static final int AUTO_SWITCH = 0;
    public static final int MANUAL_SWITCH = 1;
    private int mCurrentSwitch = AUTO_SWITCH;
    private static final int DELAY_TIEM = 2000;
    private int mItem = -100;
    private ViewIndicator mViewIndicator;
    private Handler mHandler = new Handler(Looper.getMainLooper());


    public CustomViewPager(Context context) {
        this(context, null);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPagerAdapter = new MyPagerAdapter();
        this.setAdapter(mPagerAdapter);
        this.addOnPageChangeListener(mPagerChangeListener);
    }

    /*
    * 设置页面滑动监听
    * */
    private OnPageChangeListener mPagerChangeListener = new OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mCurrentPos = position;
            if (mCurrentSwitch == AUTO_SWITCH) {
             mHandler.postDelayed(mAutoRunnable, DELAY_TIEM);
            }
            mViewIndicator.setCurrentIndex(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case ViewPager.SCROLL_STATE_IDLE:
                    mItem = -100;
                    break;
                case ViewPager.SCROLL_STATE_DRAGGING:
                    mItem = CustomViewPager.this.getCurrentItem();
                    mHandler.removeCallbacks(mAutoRunnable);
                    break;
                case ViewPager.SCROLL_STATE_SETTLING:
                    int item = CustomViewPager.this.getCurrentItem();
                    if (Math.abs(mItem - item) == 1) {
                        mCurrentSwitch = MANUAL_SWITCH;
                    }else if (mCurrentSwitch == AUTO_SWITCH && mItem != -100) {
                        mHandler.postDelayed(mAutoRunnable, DELAY_TIEM);
                    }
                    break;
            }
        }
    };

    private Runnable mAutoRunnable = new Runnable() {
        @Override
        public void run() {
            if (mTotal > 1 && mCurrentSwitch == AUTO_SWITCH) {
                mCurrentPos++;
                int item = (mCurrentPos)%mTotal;
                if (item == 0) {
                    mCurrentSwitch = MANUAL_SWITCH;
                    return;
                }
                CustomViewPager.this.setCurrentItem(item, true);
            }
        }
    };

    /*
    * ViewPager自动轮播的实现
    * */
    public void startAutoSwitch() {
        if (mTotal > 1 && mCurrentSwitch == AUTO_SWITCH) {
            mHandler.postDelayed(mAutoRunnable, DELAY_TIEM);
        }
    }

    public void setIndicator(ViewIndicator viewIndicator) {
        mViewIndicator = viewIndicator;
        mViewIndicator.setIndicatorViewNum(mTotal);
    }

    /*
    * 设置切换模式
    * */
    public void setSwitchMode(int mode) {
        mCurrentSwitch = mode;
    }

    public void reset() {
        mCurrentSwitch = AUTO_SWITCH;
        mHandler.removeCallbacks(mAutoRunnable);
    }

    public void addAll(List<View> views) {
        mPagerAdapter.addItemAll(views);
        mTotal = mPagerAdapter.getCount();
    }

    public void add(View view) {
        mPagerAdapter.addItem(view);
        mTotal = mPagerAdapter.getCount();
    }

    public void add(View view, int pos) {
        mPagerAdapter.addItem(pos, view);
        mTotal = mPagerAdapter.getCount();
    }

    public void remove(View view) {
        mPagerAdapter.removeItem(view);
        mTotal = mPagerAdapter.getCount();
    }

    public void remove(int pos) {
        mPagerAdapter.removeItem(pos);
        mTotal = mPagerAdapter.getCount();
    }

    /*
    * 当ViewPager中只剩下一个View的时候，ViewPager不能滑动
    * */
    @Override
    public void scrollTo(int x, int y) {
        if (mTotal != 1) {
            super.scrollTo(x, y);
        }
    }

    /*
        * 适配器
        * */
    private class MyPagerAdapter extends PagerAdapter {
        private List<View> itemViews = new ArrayList<>();

        public void addItemAll(List<View> views) {
            itemViews.clear();
            itemViews.addAll(views);
            this.notifyDataSetChanged();
        }

        public void addItem(View view) {
            itemViews.add(view);
            this.notifyDataSetChanged();
        }

        public void removeItem(View view) {
            itemViews.remove(view);
            this.notifyDataSetChanged();
        }

        public void removeItem(int pos) {
            itemViews.remove(pos);
            this.notifyDataSetChanged();
        }

        public void addItem(int pos, View view) {
            itemViews.add(pos, view);
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return itemViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(itemViews.get(position));
            return itemViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(itemViews.get(position));
        }

        /*
        * POSITION_NONE：更新适配器时，对应位置的View会销毁
        * POSITION_UNCHANGED：更新适配器时，对应的位置不会销毁
        * */
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
