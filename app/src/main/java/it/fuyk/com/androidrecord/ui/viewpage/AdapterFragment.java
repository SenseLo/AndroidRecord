package it.fuyk.com.androidrecord.ui.viewpage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * author: senseLo
 * date: 2018/4/8
 */
/*
* FragmentPagerAdapter:适用于页面较少的情况；
* FragmentStatePagerAdapter：适用于页面较多的情况；
* */

    /*
    * 简单封装
    * */
public class AdapterFragment<T extends Fragment> extends FragmentStatePagerAdapter {
    private List<T> mFragments;
    private String[] mStrings;

    public AdapterFragment(FragmentManager fm, List<T> mFragments, String[] titles) {
        super(fm);
        this.mFragments = mFragments;
        this.mStrings = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mStrings == null ? super.getPageTitle(position) : mStrings[position];
    }
}

/*public class AdapterFragment extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;

    public AdapterFragment(FragmentManager fm, List<Fragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getClass().getSimpleName();
    }
}*/
