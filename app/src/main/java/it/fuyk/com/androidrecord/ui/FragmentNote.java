package it.fuyk.com.androidrecord.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.fuyk.com.androidrecord.R;

/**
 * author: senseLo
 * date: 2018/4/3
 */

public class FragmentNote extends Fragment {
    /*
    * Fragment的生命周期：
    * */

    /*
    * Fragment与Activity发生关联时调用
    * */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    /*
    * 创建Fragment的视图
    * */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        return view;
    }

    /*
    * 当Activity的onCreate方法返回时调用
    * */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
    * Fragment的视图被移除时调用
    * */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /*
    * Activity与Fragment关联取消的时候调用
    * */
    @Override
    public void onDetach() {
        super.onDetach();
    }
}
