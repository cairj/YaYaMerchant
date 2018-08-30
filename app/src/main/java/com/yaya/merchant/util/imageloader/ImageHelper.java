package com.yaya.merchant.util.imageloader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

import com.yaya.merchant.util.AppManager;
import com.yaya.merchant.util.ToastUtil;
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

    private static final int SAVE_SUCCESS = 1000;
    private static final int SAVE_FAIL = 1001;

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
                        String fullFileName = PATH + File.separator + "image" + File.separator + fileName;
                        //这里就不要用openFileOutput了,那个是往手机内存中写数据的
                        FileOutputStream output = null;
                        try {
                            byte[] resource = response.body().bytes();
                            output = new FileOutputStream(fullFileName);
                            output.write(resource);//将bytes写入到输出流中
                            Context context = AppManager.getAppManager().getCurrentActivity();
                            File file = new File(fullFileName);
                            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                                    file.getAbsolutePath(), fileName, null);
                            //保存图片后发送广播通知更新数据库
                            Uri uri = Uri.fromFile(file);
                            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                            handler.sendEmptyMessage(SAVE_SUCCESS);
                        } catch (IOException e) {
                            e.printStackTrace();
                            handler.sendEmptyMessage(SAVE_FAIL);
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
                    String fullFileName = PATH + File.separator + "image" + File.separator + fileName;
                    FileOutputStream fileOutputStream = new FileOutputStream(fullFileName);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                    Context context = AppManager.getAppManager().getCurrentActivity();
                    File file = new File(fullFileName);
                    MediaStore.Images.Media.insertImage(context.getContentResolver(),
                            file.getAbsolutePath(), fileName, null);
                    //保存图片后发送广播通知更新数据库
                    Uri uri = Uri.fromFile(file);
                    context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                    handler.sendEmptyMessage(SAVE_SUCCESS);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(SAVE_FAIL);
                }
            }
        }).start();
    }

    private  static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SAVE_SUCCESS:
                    ToastUtil.toast("保存成功");
                    break;
                case SAVE_FAIL:
                    ToastUtil.toast("保存失败");
                    break;
            }
        }
    };

}
