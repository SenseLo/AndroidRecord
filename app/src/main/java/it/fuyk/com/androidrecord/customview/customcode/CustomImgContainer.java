package it.fuyk.com.androidrecord.customview.customcode;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * author: senseLo
 * date: 2018/3/29
 */

public class CustomImgContainer extends ViewGroup {

    public CustomImgContainer(Context context) {
        this(context, null);
    }

    public CustomImgContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImgContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*
    * 计算所有childview的宽度和高度，然后根据childview的宽高设置自己的宽高
    * */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        /*
        * 获得此ViewGroup上级容器为其推荐的宽高，以及计算模式
        * */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


        /*
        * 如果设置的宽高为wrap_content
        * */
        int width = 0;
        int height = 0;

        /*
        * 记录childView的属性
        * */
        int childWidth = 0;
        int childHeight = 0;
        MarginLayoutParams childParams = null;

        int lHeight = 0;  //左边两个childView的高度
        int rHeight = 0;  //右边两个childView的高度
        int tWidth = 0;
        int bWidth = 0;

       /*
        * 计算出所有childView的宽高
        * */
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            childWidth = childView.getMeasuredWidth();
            childHeight = childView.getMeasuredHeight();
            childParams = (MarginLayoutParams) childView.getLayoutParams();

            if (i == 0 || i == 1) {
                tWidth += childWidth + childParams.leftMargin + childParams.rightMargin;
            }

            if (i == 2 || i == 3) {
                bWidth += childWidth + childParams.leftMargin + childParams.rightMargin;
            }

            if (i == 0 || i == 2) {
                lHeight = childHeight + childParams.topMargin + childParams.bottomMargin;
            }

            if (i == 1 || i == 3) {
                rHeight = childHeight + childParams.topMargin + childParams.bottomMargin;
            }
        }

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        }else {
            width = Math.max(tWidth, bWidth);
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        }else {
            height = Math.max(lHeight, rHeight);
        }

        setMeasuredDimension(width, height);
    }

    /*
    * 对所有的childView进行定位
    * */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
                /*
        * 记录childView的属性
        * */
        int childWidth = 0;
        int childHeight = 0;
        MarginLayoutParams childParams = null;
        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            childWidth = childView.getMeasuredWidth();
            childHeight = childView.getMeasuredHeight();
            childParams = (MarginLayoutParams) childView.getLayoutParams();

            int childLeft = 0, childRight = 0, childTop = 0, childBottom = 0;
            switch (i) {
                case 0:
                    childLeft = childParams.leftMargin;
                    childTop = childParams.topMargin;
                    break;
                case 1:
                    childLeft = getWidth() - childWidth - childParams.rightMargin - childParams.leftMargin;
                    childTop = childParams.topMargin;
                    break;
                case 2:
                    childLeft = childParams.leftMargin;
                    childTop = getHeight() - childHeight - childParams.topMargin - childParams.bottomMargin;
                    break;
                case 3:
                    childLeft = getWidth() - childWidth - childParams.leftMargin - childParams.rightMargin;
                    childTop = getHeight() - childHeight - childParams.bottomMargin - childParams.topMargin;
                    break;
            }

            childRight = childLeft + childWidth;
            childBottom = childTop + childHeight;
            childView.layout(childLeft, childTop, childRight, childBottom);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }

    /*
    * 指定ViewGroup的LayoutParams为MarginLayoutsParams；
    * */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
