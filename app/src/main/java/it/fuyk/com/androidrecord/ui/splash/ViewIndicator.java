package it.fuyk.com.androidrecord.ui.splash;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import it.fuyk.com.androidrecord.R;

/**
 * author: senseLo
 * date: 2018/4/9
 */


public class ViewIndicator extends LinearLayout {
    private int mMarginLeft = 20;
    private int mNum;
    private List<ImageView> imageViews = new ArrayList<>();

    public ViewIndicator(Context context) {
        this(context, null);
    }

    public ViewIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
    }

    /*
    * 设置指示点的间距
    * */
    public void setMarginLeft(int marginLeft) {
        this.mMarginLeft = marginLeft;
    }

    /*
    * 设置指示点的个数以及初始化
    * */
    public void setIndicatorViewNum(int num) {
        this.mNum = num;
        removeAllViews();
        imageViews.clear();
        for (int i = 0; i < num; i++) {
            ImageView im = new ImageView(this.getContext());
            LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i == 0) {
                im.setImageResource(R.mipmap.ic_launcher);
            }else {
                params.leftMargin = mMarginLeft;
                im.setImageResource(R.mipmap.ic_launcher);
            }
            imageViews.add(im);
            addView(im, params);
        }
    }

    /*
    * 设置当前index的位置
    * */
    public void setCurrentIndex(int index) {
        for (int i = 0; i < mNum; i++) {
            if (i == index) {
                imageViews.get(i).setImageResource(R.mipmap.ic_launcher);
            }else {
                imageViews.get(i).setImageResource(R.mipmap.ic_launcher);
            }
        }
    }
}
