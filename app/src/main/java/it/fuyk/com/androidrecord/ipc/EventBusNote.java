package it.fuyk.com.androidrecord.ipc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.fuyk.com.androidrecord.R;

/**
 * author: senseLo
 * date: 2018/3/23
 */

public class EventBusNote extends AppCompatActivity {
    /*
    * EventBus：一个Android事件发布/订阅轻量级框架
    * 实现：简化了应用程序内各组件间，组件与后台线程间的通信。
    * github地址：https://github.com/greenrobot/EventBus
    * 官方文档：http://greenrobot.org/eventbus/documentation
    *
    * EventBus三要素：
    *   Event：事件--任意类型的对象
    *   Subscriber：事件订阅者--事件处理方法只需要添加 注解@Subscriber
    *   Publisher：事件发布者--直接调用post(Object)方法，一般直接使用EventBus.getDefault().
    *   ThreadMode：定义函数在何种线程中执行。
    *               MAIN：UI主线程
    *               BACKGROUND：后台线程
    *               POSTING：和发布者属于同一个线程
    *               ASYNC：异步线程
    *   priority：订阅事件的优先级，优先级越高先获得消息
    *
    * 使用：
    *     1：添加依赖 compile 'org.greenrobot:eventbus:3.0.0'
    *     2：定义一个事件类型
    *     3：订阅 EventBus.getDefault().register(this); 解除订阅 EventBus.getDefault().unregister(this);
    *     4: 发布事件 EventBus.getDefault().post(new Event());
    *     5: 订阅事件的处理。
    *
    *     @onSubscribe(threadMode = ThreadMode.MAIN，priority = 100)
    *     public void onData(Event event) {
    *     }
    * */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        * 注册事件
        * */
        EventBus.getDefault().register(this);
    }

    /*
    * 发送事件
    * */
    public void post() {
        EventBus.getDefault().post(new MessageEvent("发送事件"));
    }

    /*
    * 处理事件
    * */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dealEvent(MessageEvent msg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*
        * 解除注册
        * */
        EventBus.getDefault().unregister(this);
    }

    /*
    * 自定义一个事件类
    * */
    public class MessageEvent {
        String message;

        public MessageEvent(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
