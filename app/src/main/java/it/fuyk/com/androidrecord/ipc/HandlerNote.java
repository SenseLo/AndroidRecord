package it.fuyk.com.androidrecord.ipc;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

/**
 * author: senseLo
 * date: 2018/3/26
 *
 * Handler消息机制 -- 实现线程之间的通讯
 * 子线程执行耗时操作，主线程执行更新UI的操作
 * 流程：
 *      1：在主线程创建一个Handler对象，并且重写了handleMessage（Message msg）方法
 *      2：子线程进行更新UI的操作，在子线程中创建Message对象，通过Handler对象将msg发送出去（sendMessage（msg））
 *      3：Message被加入到MessageQueue队列中等待被处理
 *      4：通过Looper对象会通过loop（）从MessageQueue中取出Message
 *      5：取出的Message通过dispatchMessage方法分发给Handler在hadleMessage（）中进行处理
 */

public class HandlerNote {

    /*
    * 角色：
    *       1：Message -- 消息ID、消息处理对象、处理的数据等
    *       2：Handler -- 处理者，负责Message的发送和处理，要实现handleMessage（Message msg）方法对特定的msg进行处理。
    *       3：MessageQueue -- 消息队列，存放handler发送的Message，先进先出FIFO；
    *       4：Loop -- 消息泵，不断地从MessageQueue中抽取Message执行
    *       5：Thread -- 线程，负责调度整个消息循环，即消息循环的执行场所。
    *
    *
    * */

    public void HandlerDemo() {
        /*
        * 主线程定义Handler
        * */
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        //拿到数据完成主界面的更新
                        String s = (String) msg.obj;
                        //textView.setText(s);
                        break;
                    default:
                        break;
                }
            }
        };

        /*
        * 在子线程发送消息
        * */

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 0;
                msg.obj = "";
                //handler.sendMessage(msg);

                //runOnUiThread(new Runnable() {
                //  @override
                //  public void run() {
                //   数据更新
                //  }
                // })；
            }
        }).start();

        /*
        * 使用AsyncTask替代Thread
        * */

    }

    public class MyTask extends AsyncTask {

        /*
        * 后台线程执行时
        * */
        @Override
        protected Object doInBackground(Object[] params) {
            String data = "";
            return data;
        }

        /*
        * 线程执行前
        * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /*
        * 线程执行后
        * */
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            /*
            * o为doInBackground返回的结果
            * */
        }
    }
}
