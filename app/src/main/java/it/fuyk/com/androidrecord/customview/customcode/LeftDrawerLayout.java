package it.fuyk.com.androidrecord.customview.customcode;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * author: senseLo
 * date: 2018/3/30
 */

public class LeftDrawerLayout extends ViewGroup {

    /*
    * 布局：content 和 menu
    * */
    private View mContentView;
    private View mMenuView;

    private static final int MIN_DRAWER_MARGIN = 64;

    /*
    * drawer离父容器右边的最小外边距
    * */
    private int mMinDrawerMargin;
    private ViewDragHelper viewDragHelper;

    /*
    * drawer显示出来的占自身的百分比
    * */
    private float mLeftMenuOnScreen;

    public LeftDrawerLayout(Context context) {
        this(context, null);
    }

    public LeftDrawerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeftDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        float density = getResources().getDisplayMetrics().density;
        float minVel = MIN_DRAWER_MARGIN*density;

        mMinDrawerMargin = (int) (MIN_DRAWER_MARGIN*density + 0.5f);

        viewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == mMenuView;  //捕获当前View
            }

            //手指松手时回调
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                int childWidth = releasedChild.getWidth();
                float offset = (childWidth + releasedChild.getLeft())*1.0f / childWidth;
                viewDragHelper.settleCapturedViewAt(xvel > 0 || xvel == 0 && offset > 0.5f ? 0 : -childWidth, releasedChild.getTop());
                invalidate();
            }

            //对child移动的边界进行控制
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                int newLeft = Math.max(-child.getWidth(), Math.min(left, 0));
                return newLeft;
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                viewDragHelper.captureChildView(mMenuView, pointerId);
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                int childWidth = changedView.getWidth();
                float offset = (float) (childWidth + left)*1.0f/childWidth;
                mLeftMenuOnScreen = offset;
                mMenuView.setVisibility(offset == 0?View.INVISIBLE : View.VISIBLE);
                invalidate();

            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return mMenuView == child ? child.getWidth() : 0;
            }
        });

        viewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        viewDragHelper.setMinVelocity(minVel);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(widthSize, heightSize);

        mMenuView = getChildAt(1);
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) mMenuView.getLayoutParams();

        int menuWidtSpec = getChildMeasureSpec(widthMeasureSpec, mMinDrawerMargin + marginLayoutParams.rightMargin + marginLayoutParams.leftMargin, marginLayoutParams.width);
        int menuHeightSpec = getChildMeasureSpec(heightMeasureSpec, marginLayoutParams.bottomMargin + marginLayoutParams.topMargin, marginLayoutParams.height);
        mMenuView.measure(menuWidtSpec, menuHeightSpec);

        mContentView = getChildAt(0);
        MarginLayoutParams contentLp = (MarginLayoutParams) mContentView.getLayoutParams();
        int contentWidthSpec = MeasureSpec.makeMeasureSpec(widthSize - contentLp.leftMargin - contentLp.rightMargin, MeasureSpec.EXACTLY);
        int contentHeightSpec = MeasureSpec.makeMeasureSpec(heightSize - contentLp.topMargin - contentLp.bottomMargin, MeasureSpec.EXACTLY);
        mContentView.measure(contentWidthSpec, contentHeightSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        MarginLayoutParams lp = (MarginLayoutParams) mContentView.getLayoutParams();
        mContentView.layout(lp.leftMargin, lp.topMargin,
                lp.leftMargin + mContentView.getMeasuredWidth(),
                lp.topMargin + mContentView.getMeasuredHeight());

        lp = (MarginLayoutParams) mMenuView.getLayoutParams();

        final int menuWidth = mMenuView.getMeasuredWidth();
        int childLeft = -menuWidth + (int) (menuWidth * mLeftMenuOnScreen);
        mMenuView.layout(childLeft, lp.topMargin, childLeft + menuWidth,
                lp.topMargin + mMenuView.getMeasuredHeight());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    public void closeDrawer() {
        mLeftMenuOnScreen = 0.f;
        viewDragHelper.smoothSlideViewTo(mMenuView, -mMenuView.getWidth(), mMenuView.getTop());
    }

    public void openDrawer() {
        mLeftMenuOnScreen = 1.0f;
        viewDragHelper.smoothSlideViewTo(mMenuView, 0, mMenuView.getTop());
    }


    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new MarginLayoutParams(p);
    }
}
