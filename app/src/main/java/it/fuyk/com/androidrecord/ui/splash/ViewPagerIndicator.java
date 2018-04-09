package it.fuyk.com.androidrecord.ui.splash;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

/**
 * author: senseLo
 * date: 2018/4/9
 */

public class ViewPagerIndicator extends FrameLayout {

    private CustomViewPager mViewPager;
    private ViewIndicator mViewIndicator;
    private int mBootmMargin = 40; //指示点距离底部的距离，单位px
    private int mLeftMargin = 20; //指示点之间的距离，单位px

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    private void addViewPager() {
        mViewPager = new CustomViewPager(this.getContext());
        FrameLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(mViewPager, params);
    }

    private void addIndicatorView() {
        mViewIndicator = new ViewIndicator(this.getContext());
        FrameLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM | Gravity.CENTER;
        params.bottomMargin = mBootmMargin;
        this.addView(mViewIndicator, params);
    }

    public void setBottomMargin(int bottomMargin) {
        this.mBootmMargin = bottomMargin;
    }

    public void setLeftMargin(int leftMargin) {
        this.mLeftMargin = leftMargin;
    }

    public void addAll(List<View>views) {
        mViewPager.addAll(views);
        mViewIndicator.setMarginLeft(mLeftMargin);
        mViewPager.setIndicator(mViewIndicator);
    }

    public void removeItem(int pos) {
        mViewPager.remove(pos);
    }

    public void startAutoPlay() {
        mViewPager.startAutoSwitch();
    }
}
