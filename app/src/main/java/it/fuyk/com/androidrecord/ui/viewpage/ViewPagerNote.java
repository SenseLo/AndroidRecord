package it.fuyk.com.androidrecord.ui.viewpage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import it.fuyk.com.androidrecord.R;

/**
 * author: senseLo
 * date: 2018/4/8
 */

public class ViewPagerNote extends AppCompatActivity {
    private ViewPager vp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpagernote);
        
        initView();
        initData();
    }

    private void initView() {
        vp = (ViewPager) findViewById(R.id.vp_viewpagenote);
    }

    private void initData() {

    }
}
