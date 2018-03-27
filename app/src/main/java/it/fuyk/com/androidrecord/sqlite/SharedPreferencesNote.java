package it.fuyk.com.androidrecord.sqlite;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import it.fuyk.com.androidrecord.R;

/**
 * author: senseLo
 * date: 2018/3/26
 */

public class SharedPreferencesNote extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
    * sharedPreferences: android平台上的一个轻量级存储类，用来保存应用的一些常用配置。
    * */

    /*
    * 写入数据
    * */
    public void writeData() {

        /*
        * 获取SharedPreferences对象
        * */
        SharedPreferences sharedPreferences = getSharedPreferences("test", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        /*
        * 存储数据
        * */
        editor.putString("USERNAME", "xiaofu");
        editor.putString("USERSEX", "MAN");
        editor.apply();
    }

    /*
    * 读取数据
    * */
    public void readData() {
        SharedPreferences sharedPreferences = getSharedPreferences("test", Activity.MODE_PRIVATE);
        String useName = sharedPreferences.getString("USERNAME", "");
        String userSex = sharedPreferences.getString("USERSEX", "");
    }


}
