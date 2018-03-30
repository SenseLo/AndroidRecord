package it.fuyk.com.androidrecord.customview.customcode;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import it.fuyk.com.androidrecord.R;

/**
 * author: senseLo
 * date: 2018/3/29
 */

public class CustomVolumControlBar extends View {

    private Rect mRect;
    private Paint mPaint;
    private Bitmap v_bg;
    private int v_firstColor;
    private int v_secondColor;
    private int v_circleWidth;
    private int v_spliteSize;
    private int v_dotCount;

    private int mCurrentCount = 3;

    public CustomVolumControlBar(Context context) {
        this(context, null);
    }

    public CustomVolumControlBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomVolumControlBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        /*
        * 获取自定义样式以及属性
        * */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomVolumControlBar, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomVolumControlBar_v_bg:
                    v_bg = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
                    break;
                case R.styleable.CustomVolumControlBar_v_circleWidth:
                    v_circleWidth = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomVolumControlBar_v_dotCount:
                    v_dotCount = a.getInt(attr, 20);
                    break;
                case R.styleable.CustomVolumControlBar_v_firstColor:
                    v_firstColor = a.getColor(attr, Color.BLUE);
                    break;
                case R.styleable.CustomVolumControlBar_v_secondColor:
                    v_secondColor = a.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.CustomVolumControlBar_v_spliteSize:
                    v_spliteSize = a.getInt(attr, 20);
                    break;
            }
        }

        a.recycle();

        /*
        * 初始化操作
        * */
        mPaint = new Paint();
        mRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setAntiAlias(true); //消除锯齿
        mPaint.setStrokeWidth(v_circleWidth); //设置圆环的宽度
        mPaint.setStrokeCap(Paint.Cap.ROUND); //设置线段断电形状为圆头
        mPaint.setStyle(Paint.Style.STROKE); //设置空心

        int center = getWidth()/2; //获取圆心的x坐标
        int radius = center - v_circleWidth/2; //半径

        drawOval(canvas, center, radius);

        int relRadius = radius - v_circleWidth/2; //内圆的半径

        mRect.left =(int) (relRadius - Math.sqrt(2)*1.0f/2 * relRadius) + v_circleWidth;
        mRect.top = (int) (relRadius - Math.sqrt(2)*1.0f/2 * relRadius) + v_circleWidth;
        mRect.bottom = (int)(mRect.top + Math.sqrt(2)*relRadius);
        mRect.right = (int)(mRect.left + Math.sqrt(2)*relRadius);

        /*
        * 如果图片比较小，那么根据图片的尺寸放置到正中心
        * */
        if (v_bg.getWidth() < Math.sqrt(2)*relRadius) {
            mRect.left = (int) (mRect.left + Math.sqrt(2)*relRadius*1.0f/2 - v_bg.getWidth()*1.0f/2);
            mRect.top = (int) (mRect.top + Math.sqrt(2)*relRadius*1.0f/2 - v_bg.getHeight()*1.0f/2);
            mRect.right = (int) (mRect.left + v_bg.getWidth());
            mRect.bottom = (int) (mRect.top + v_bg.getHeight());
        }

        /*
        * 绘图
        * */
        canvas.drawBitmap(v_bg, null, mRect, mPaint);
    }

    /*
    * 画小块
    * */
    private void drawOval(Canvas canvas, int center, int radius) {
        /*
        * 根据需要画的个数以及间隙计算每个块块所占比例
        * */
        float itemSize = (360*1.0f - v_dotCount*v_spliteSize)/v_dotCount;
        /*
        * 用于定义圆弧的形状和大小的界限
        * */
        RectF oval = new RectF(center-radius, center-radius, center+radius, center+radius);
        mPaint.setColor(v_firstColor);  //设置圆环的颜色
        for (int i = 0; i < v_dotCount; i++) {
            canvas.drawArc(oval, i*(itemSize + v_spliteSize), itemSize, false, mPaint); //根据进度画圆弧
        }
        
        mPaint.setColor(v_secondColor);
        for (int i = 0; i < mCurrentCount; i++) {
            canvas.drawArc(oval, i*(itemSize + v_spliteSize), itemSize, false, mPaint); //根据进度画圆弧
        }
    }

    /*
    * 添加触摸监听
    * */

    private int xDown, xUp;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                xUp = (int) event.getY();
                if (xDown > xUp) {
                    //上滑
                    up();
                }else {
                    down();
                }
                break;
        }
        return true;
    }

    private void down() {
        mCurrentCount--;
        postInvalidate();
    }

    private void up() {
        mCurrentCount++;
        postInvalidate();
    }
}
