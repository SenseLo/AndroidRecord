package it.fuyk.com.androidrecord.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import it.fuyk.com.androidrecord.R;

/**
 * author: senseLo
 * date: 2018/4/3
 */

public class ActivityFragmentNote extends AppCompatActivity implements View.OnClickListener {

    private Button bt1;
    private Button bt2;
    private FragmentNote fragmentNote;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();

        setDefaultFragment();
    }

    private void initView() {
        bt1 = (Button)findViewById(R.id.bt1);
        bt2 = (Button)findViewById(R.id.bt2);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
    }

    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        FragmentNote fn = new FragmentNote();
        ft.replace(R.id.id_content, fn);
        ft.commit();
    }


    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.bt1:
                if (fragmentNote == null) {
                    fragmentNote = new FragmentNote();
                }
                fragmentTransaction.replace(R.id.id_content, fragmentNote);
                break;
            case R.id.bt2:
                break;
        }
        fragmentTransaction.commit();
    }
}
