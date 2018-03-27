package it.fuyk.com.androidrecord.ipc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import it.fuyk.com.androidrecord.R;

/**
 * author: senseLo
 * date: 2018/3/26
 * <p>
 * 组件之间传递消息之BroadcastReceiver
 *
 * 动态注册和静态注册的区别：
 *  1：静态注册<receiver>跟动态注册(registerReceiver())的使用方式
 *  2：静态注册常驻，不受任何组件的生命周期影响，缺点是耗电占内存
 *  3：动态注册非常驻，灵活，跟随组件的生命周期变化
 *  4：静态--使用场景--需要时刻监听广播；
 *  5：动态--使用场景--需要特定时刻监听广播；
 */

public class BroadcastNote extends AppCompatActivity {
    private MyBroadCastReceiver broadCastReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    /*
    * 动态注册
    * */

     /*
    * 1:注册广播
    * */
        broadCastReceiver = new MyBroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(CONNECTIVITY_SERVICE);
        registerReceiver(broadCastReceiver, filter);
    }

    /*
    * 1：发送广播
    * */

    public void send() {
        Intent intent = new Intent();
        intent.setAction("action"); //设置Action
        intent.putExtra("data", "data");
        intent.setPackage("设置包名广播只能被app内接收");
    }

    /*
    * 解除注册
    * */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadCastReceiver);
    }

    public class MyBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            /*
            * 接收到广播后自动调用该方法
            * */
        }
    }
}
