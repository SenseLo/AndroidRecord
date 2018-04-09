package it.fuyk.com.androidrecord.ui.viewpage;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * author: senseLo
 * date: 2018/4/8
 */

/*
* 简单封装
* */
public class AdapterViewpager<T extends View> extends PagerAdapter {
    private List<T> mViewList;

    public AdapterViewpager(List<T> mViewList) {
        this.mViewList = mViewList;
    }

    //获取要滑动的控件的数量
    @Override
    public int getCount() {
        return mViewList.size();
    }

    //判断当前View是否和对应的key关联
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //默认情况下先调用三次，将三个即将使用view页面添加到ViewGroup中，当你滑动到第二页view时，viewpager会调用一次该方法将第四个view页面添加
    //到ViewGroup中
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

    //PagerAdapter只缓存三张要显示的view，如果滑动到第三个view时，就会调用此方法销毁第一个view
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }
}


/*public class AdapterViewpager extends PagerAdapter {
    private List<View> mViewList;

    public AdapterViewpager(List<View> mViewList) {
        this.mViewList = mViewList;
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }
}*/
