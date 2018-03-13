package com.yaya.merchant.util.imageloader;

import android.os.Looper;

import com.bumptech.glide.Glide;
import com.yaya.merchant.util.AppManager;

/**
 * Created by 魏新智 on 2017/11/2.
 */
public class GlideCacheUtil {

    public static void clearCache(){
        clearCacheDisk();
        clearCacheMemory();
    }

    // 清除图片磁盘缓存，调用Glide自带方法
    public static boolean clearCacheDisk() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(AppManager.getAppManager().getCurrentActivity()).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(AppManager.getAppManager().getCurrentActivity()).clearDiskCache();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 清除Glide内存缓存
    public static boolean clearCacheMemory() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(AppManager.getAppManager().getCurrentActivity()).clearMemory();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
