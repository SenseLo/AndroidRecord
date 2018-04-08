package it.fuyk.com.androidrecord.util;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.RequiresApi;

import java.io.File;

/**
 * author: senseLo
 * date: 2018/4/8
 */

public class SDCardUtils {
    private SDCardUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /*
    * 判断SD卡是否可用
    * */
    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /*
    * 获取SD卡路径
    * */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }

    /*
    * 获取SD卡的剩余容量 单位byte
    * */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getSDCardAllSize() {
        if (isSDCardEnable()) {
            StatFs statFs = new StatFs(getSDCardPath());
            long availableBlocks = statFs.getAvailableBlocksLong() - 4;
            long freeBlocks = statFs.getFreeBlocksLong();
            return freeBlocks*availableBlocks;
        }
        return 0;
    }

    /*
    * 获取系统存储路径
    * */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }
}
