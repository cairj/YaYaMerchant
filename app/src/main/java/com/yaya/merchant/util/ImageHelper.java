package com.yaya.merchant.util;

import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;

import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Response;

/**
 * Created by admin on 2018/3/17.
 */

public class ImageHelper {

    public static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/yayaMerchant/";

    public static void downloadImage(final String imgUrl, final String fileName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = OkHttpUtils.get().url(imgUrl).build().execute();
                    if (response.isSuccessful()) {
                        File dir1 = new File(PATH + File.separator + "image");
                        if (!dir1.exists()) {
                            dir1.mkdirs();
                        }
                        String filename = PATH + File.separator + "image" + File.separator + fileName;
                        //这里就不要用openFileOutput了,那个是往手机内存中写数据的
                        FileOutputStream output = null;
                        try {
                            byte[] resource = response.body().bytes();
                            output = new FileOutputStream(filename);
                            output.write(resource);//将bytes写入到输出流中
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (output != null) {
                                try {
                                    output.close();//关闭输出流
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void downloadImage(final Bitmap bitmap, final String fileName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File dir1 = new File(PATH + File.separator + "image");
                    if (!dir1.exists()) {
                        dir1.mkdirs();
                    }
                    String filename = PATH + File.separator + "image" + File.separator + fileName;
                    FileOutputStream fileOutputStream = new FileOutputStream(filename);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.toast("保存成功");
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.toast("保存失败");
                        }
                    });
                }
            }
        }).start();
    }

}
