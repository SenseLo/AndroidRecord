package it.fuyk.com.androidrecord.customview.customcode;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import it.fuyk.com.androidrecord.R;


/**
 * author: senseLo
 * date: 2018/3/23
 */

public class CustomImageView extends View {
    private Rect mTextBound;
    private Rect mRect;
    private Paint mPaint;
    private int mImageScaleType;
    private Bitmap mImage;
    private float mTitleSize;
    private int mTitleColor;
    private String mTitle;
    private int mWidth;
    private int mHeight;

    public CustomImageView(Context context) {
        this(context, null);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomImageView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomImageView_imageText:
                    mTitle = a.getString(attr);
                    break;
                case R.styleable.CustomImageView_imageTextColor:
                    mTitleColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomImageView_imageTextSize:
                    mTitleSize = a.getDimension(attr, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomImageView_iamge:
                    mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
                    break;
                case R.styleable.CustomImageView_iamgeScaleType:
                    mImageScaleType = a.getInt(attr, 0);
                    break;
            }

        }
        a.recycle();
        mPaint = new Paint();
        mRect = new Rect();
        mTextBound = new Rect();
        mPaint.setTextSize(mTitleSize);
        /*
        * 计算描绘字体需要的范围
        * */
        mPaint.getTextBounds(mTitle, 0, mTitle.length(), mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        /*
        * 设置宽度
        * */

        if (widthMode == MeasureSpec.EXACTLY) {  //match_parent accurate
            mWidth =widthSize;
        }else {
            /*
            * 由图片决定的宽
            * */
            int desireByImage = getPaddingLeft() + getPaddingRight() + mImage.getWidth();

            /*
            * 由字体决定的宽
            * */
            int desireByText = getPaddingLeft() + getPaddingRight() + mTextBound.width();
            if (widthMode == MeasureSpec.AT_MOST) {  //wrap_content
                int desire = Math.max(desireByImage, desireByText);
                mWidth = Math.min(desire, widthSize);
            }
        }

        /*
        * 设置高度
        * */

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }else {
            int desire = getPaddingBottom() + getPaddingTop() + mImage.getHeight() + mTextBound.height();
            if (heightMode == MeasureSpec.AT_MOST) {
                mHeight = Math.min(desire, heightSize);
            }
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        /*
        * 设置边框
        * */
        mPaint.setStrokeWidth(4); //设置边框宽度
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.CYAN);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        /*
        * 设置字体
        * */
        mPaint.setColor(mTitleColor);
        mPaint.setStyle(Paint.Style.FILL);

        if (mWidth < mTextBound.width()) {
            /*
            * 如果字体的宽度大于设置的宽度，将字体改为xxx。。。
            * */
            TextPaint txPaint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(mTitle, txPaint, (float)mWidth - getPaddingLeft() - getPaddingRight(), TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg, getPaddingLeft(), mHeight-getPaddingBottom(), mPaint);
        }else {
            /*
            * 正常情况下，字体居中
            * */
            canvas.drawText(mTitle, mWidth/2 - mTextBound.width()/2, mHeight - getPaddingBottom(), mPaint);
        }

        /*
        * 设置图片位置
        * */
        mRect.left = getPaddingLeft();
        mRect.right = mWidth - getPaddingRight();
        mRect.top = getPaddingTop();
        mRect.bottom = mHeight - getPaddingBottom();

        /*
        * 减掉字体占位
        * */
        mRect.bottom -= mTextBound.height();

        if (mImageScaleType == 0) {
            canvas.drawBitmap(mImage, null, mRect, mPaint);
        }else {
            /*
            * 居中
            * */
            mRect.left = mWidth/2 - mImage.getWidth()/2;
            mRect.right = mWidth/2 + mImage.getWidth()/2;
            mRect.top = (mHeight - mTextBound.height())/2 - mImage.getHeight()/2;
            mRect.bottom = (mHeight - mTextBound.height())/2 + mImage.getHeight()/2;

            canvas.drawBitmap(mImage, null, mRect, mPaint);
        }
    }
}
