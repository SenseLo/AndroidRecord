package it.fuyk.com.androidrecord.customview.customcode;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import it.fuyk.com.androidrecord.R;

/**
 * author: senseLo
 * date: 2018/3/22
 */

public class CustomTitleView extends View {

    private Rect mBound;
    private Paint mPaint;
    private String titleText;
    private int titleColor;
    private int titleSize;

    public CustomTitleView(Context context) {
        this(context,null);
    }

    public CustomTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /*
        * 获得自定义的样式属性
        * */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomTitleView_titleColor:
                    titleColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomTitleView_titleSize:
                    titleSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomTitleView_titleText:
                    titleText = a.getString(attr);
                    break;
            }
        }
        a.recycle();

        /*
        * 初始化画笔并设置文本宽高
        * */

        mPaint = new Paint();
        mPaint.setTextSize(titleSize);
        mBound = new Rect();
        mPaint.getTextBounds(titleText, 0, titleText.length(), mBound);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                titleText = "lala";
                postInvalidate();
            }
        });

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*
        * 我们设置明确的宽度和高度时，系统帮我们测量的结果就是我们设置的结果，
        * 当我们设置为WRAP_CONTENT,或者MATCH_PARENT系统帮我们测量的结果就是MATCH_PARENT的长度。
        *  当设置了WRAP_CONTENT时：需要自己测量，重写onMeasure()方法。
        *
        * */

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        }else {
            mPaint.setTextSize(titleSize);
            mPaint.getTextBounds(titleText, 0, titleText.length(), mBound);
            float textWidth = mBound.width();
            int desired = (int) (getPaddingRight() + textWidth + getPaddingLeft());
            width = desired;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        }else {
            mPaint.setTextSize(titleSize);
            mPaint.getTextBounds(titleText, 0, titleText.length(), mBound);
            float textHeight = mBound.height();
            int desire = (int) (getPaddingBottom() + textHeight + getPaddingTop());
            height = desire;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(),mPaint);

        mPaint.setColor(titleColor);
        canvas.drawText(titleText, getWidth()/2 - mBound.width()/2, getHeight()/2 + mBound.height()/2, mPaint);
    }
}
