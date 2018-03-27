package it.fuyk.com.androidrecord.ipc;

import android.os.Handler;

import java.util.LinkedList;

/**
 * author: senseLo
 * date: 2018/3/26
 *
 * 管理监听总线
 */

public class DataSynManager {
    private static DataSynManager instance;
    private LinkedList<DataSynListener> listeners = new LinkedList<>();

    public DataSynManager() {}

    public static DataSynManager getInstance() {
        if (instance == null) {
            synchronized (DataSynManager.class) {
                if (instance == null) {
                    instance = new DataSynManager();
                }
            }
        }
        return instance;
    }

    /*
    * 注册同步监听
    * */
    public void registerDataSynListener(DataSynListener listener) {
        if (listeners == null) {
            listeners = new LinkedList<>();
        }
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
     }

    /*
    * 移除同步监听
    * */
    public void removeDataSynListener(DataSynListener listener) {
        if (listeners == null) {
            return;
        }
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    /*
    * 执行数据同步
    * */
    public void doDataSyn(final int count) {
        if (listeners == null) {
            listeners = new LinkedList<>();
        }

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                for (DataSynListener listener : listeners) {
                    listener.onDataSyn(count);
                }
            }
        });
    }

    /*
    * 清除所有监听者
    * */
    public void clear() {
        if (listeners != null) {
            listeners.clear();
            listeners = null;
        }
    }

    /*
    * 同步接口
    * */

    public interface DataSynListener {
        void onDataSyn(int count);
    }
}
