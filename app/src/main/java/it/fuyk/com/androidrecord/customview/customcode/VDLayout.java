package it.fuyk.com.androidrecord.customview.customcode;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * author: senseLo
 * date: 2018/3/30
 */

public class VDLayout extends LinearLayout {
    private ViewDragHelper viewDragHelper; //帮助处理剁手指处理和加速度监测等操作

    private View mDragView;
    private View mAutoBackView;
    private View mEdgeTrackerView;

    private Point mAutoBackViewPos = new Point();


    public VDLayout(Context context) {
        this(context, null);
    }

    public VDLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VDLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        /*
        * 1:创建实例
        * */
        viewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
//                return true;   //返回true表示可以捕获当前View
                return child != mEdgeTrackerView;   //mEdgeTrackerView禁止直接移动
            }

            /*
            * 对child移动的边界进行控制
            * */
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return left;  //即将移动到的位置
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }

        /*
        * 手指释放的时候回调
        * */

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                //mAutoBackView手指释放时可以自动回去
                if (releasedChild == mAutoBackView) {
                    viewDragHelper.settleCapturedViewAt(mAutoBackViewPos.x, mAutoBackViewPos.y);
                    invalidate();
                }
            }

            /*
            * 在边界拖动时回调
            * */

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                viewDragHelper.captureChildView(mEdgeTrackerView, pointerId);
            }
        });
        viewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    /*
    * 是否拦截分发事件
    * */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    /*
    * 处理分发事件
    * */
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

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mAutoBackViewPos.x = mAutoBackView.getLeft();
        mAutoBackViewPos.y = mAutoBackView.getTop();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragView = getChildAt(0);
        mAutoBackView = getChildAt(1);
        mEdgeTrackerView = getChildAt(2);
    }
}
