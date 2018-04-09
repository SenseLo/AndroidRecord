package it.fuyk.com.androidrecord.ui.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * author: senseLo
 * date: 2018/4/9
 */

public class SlowScrollView extends ScrollView {
    private Scroller mScroller;

    public SlowScrollView(Context context) {
        this(context, null);
    }

    public SlowScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlowScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new Scroller(this.getContext());
    }

    /*
    * 调用此方法滚动到目标位置， duration滚动时间
    * */
    public void smoothScrollToSlow(int fx, int fy, int duration) {
        int dx = fx - getScrollX();
        int dy = fy - getScrollY();
        smoothScrollBySlow(dx, dy, duration);
    }

    /*
    * 设置滚动的相对偏移量
    * */
    public void smoothScrollBySlow(int dx, int dy, int duration) {
        mScroller.startScroll(getScrollX(), getScrollY(), dx, dy, duration);
        invalidate();
    }

    @Override
    public void computeScroll() {
        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {
            smoothScrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }
}
