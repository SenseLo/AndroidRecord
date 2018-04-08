package it.fuyk.com.androidrecord.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * author: senseLo
 * date: 2018/4/8
 */

public class SharePreferencesUtils {
    public static final String FILE_NAME = "share_data";

    /*
    * 保存数据
    * */
    public static void put(Context context, String key, Object value) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (value instanceof String) {
            editor.putString(key, (String) value);
        }else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        }else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        }else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        }else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        }else {
            editor.putString(key, value.toString());
        }

        editor.apply();
    }

    /*
    * 获取数据
    * */
    public static Object get(Context context, String key, Object defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (defaultValue instanceof String) {
            return sp.getString(key, (String) defaultValue);
        }else if (defaultValue instanceof Integer) {
            return sp.getInt(key, (Integer) defaultValue);
        }else if (defaultValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultValue);
        }else if (defaultValue instanceof Float) {
            return sp.getFloat(key, (Float) defaultValue);
        }else  if (defaultValue instanceof Long) {
            return sp.getLong(key, (Long) defaultValue);
        }
        return null;
    }

    /*
    * 移除某个key对应的值
    * */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

    /*
    * 清除所有数据
    * */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    /*
    * 查询某个key是否存在
    * */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /*
    * 返回所有的键值对
    * */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getAll();
    }
}
