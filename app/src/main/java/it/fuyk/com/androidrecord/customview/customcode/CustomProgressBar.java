package it.fuyk.com.androidrecord.customview.customcode;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import it.fuyk.com.androidrecord.R;

/**
 * author: senseLo
 * date: 2018/3/23
 */

public class CustomProgressBar extends View {
    private Paint mPaint;
    private int speed;
    private int secondColor;
    private int firstColor;
    private int circleWidth;

    /*
    * 当前进度
    * */
    private int progress;

    /*
    * 是否应该开始下一个,默认否
    * */
    private boolean isNext = false;

    public CustomProgressBar(Context context) {
        this(context, null);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        /*
        * 获取自定义样式和属性
        * */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomProgressBar, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomProgressBar_circleWidth:
                    circleWidth = a.getDimensionPixelSize(attr, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomProgressBar_firstColor:
                    firstColor = a.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.CustomProgressBar_secondColor:
                    secondColor = a.getColor(attr, Color.BLUE);
                    break;
                case R.styleable.CustomProgressBar_speed:
                    speed = a.getInt(attr, 20);
                    break;
            }
        }
        a.recycle();

        mPaint = new Paint();

        /*
        * 绘图线程
        * */
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    progress++;
                    if (progress == 360) {
                        progress = 0;
                        if (!isNext) {
                            isNext = true;
                        }else {
                            isNext = false;
                        }
                    }
                    postInvalidate();

                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        int bigCircleWidth = getWidth()/2;
        int radius = bigCircleWidth - circleWidth/2;

        /*
        * 设置圆环的宽度
        * */
        mPaint.setStrokeWidth(circleWidth);
        mPaint.setAntiAlias(true);//消除锯齿
        mPaint.setStyle(Paint.Style.STROKE);//设置空心

        RectF oval=  new RectF(bigCircleWidth - radius, bigCircleWidth - radius, bigCircleWidth + radius, bigCircleWidth + radius);

        if (isNext) {
            /*
            *第二圈的颜开跑
            * */
            mPaint.setColor(secondColor);
            canvas.drawCircle(bigCircleWidth, bigCircleWidth, radius, mPaint);
            mPaint.setColor(firstColor);
            canvas.drawArc(oval, -90, progress, false, mPaint);
        }else {
            mPaint.setColor(firstColor);
            canvas.drawCircle(bigCircleWidth, bigCircleWidth, radius, mPaint);
            mPaint.setColor(secondColor);
            canvas.drawArc(oval, -90, progress, false, mPaint);
        }
    }
}
