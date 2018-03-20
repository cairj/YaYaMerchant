package com.yaya.merchant.util.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.yaya.merchant.data.ImagePathEntity;
import com.yaya.merchant.net.Urls;
import com.yaya.merchant.util.AppManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by asus-pc on 2015/11/11.
 */
public class ImgLoadPayUntil extends AsyncTask<String, Void, String> {

    private static boolean networkconnect = true;
    private static final int TIME_OUT = 10 * 10000000; // 超时时间
    private static final String CHARSET = "utf-8"; // 设置编码
    public static final String SUCCESS = "1";
    public static final String FAILURE = "";
    private File mFile;
    private Bitmap mBitmap;
    private getStringListener mListener;
    public static String requestURL = Urls.UPLOAD_IMG_FILES;//上传图片地址

    public static String uploadFile(File file) {
        OkHttpClient client = new OkHttpClient();
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "head_image", fileBody)
                .build();
        Request request = new Request.Builder()
                .url(requestURL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            String jsonString = response.body().string();
            if (!response.isSuccessful()) {
            } else {
                ImagePathEntity pathEntity = new Gson().fromJson(jsonString, ImagePathEntity.class);
                return pathEntity.result.filename;
            }

        } catch (IOException e) {
        }
        return "";
    }

    public static String encodeBase64File(File file) {
        FileInputStream inputFile = null;
        try {
            inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            String encodeToString = Base64.encodeToString(buffer, Base64.DEFAULT);
            return encodeToString;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String encodeBase64Byte(byte[] buffer) {
        String encodeToString = Base64.encodeToString(buffer, Base64.DEFAULT);
        return encodeToString;
    }

    public static boolean is_Network_Available(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
            //如果仅仅是用来判断网络连接
            //则可以使用
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public ImgLoadPayUntil(File file, getStringListener mListener) {
        mFile = file;
        this.mListener = mListener;
    }

    @Override
    protected String doInBackground(String... params) {
        String picturepath = "";
        if (mFile != null) {
            picturepath = uploadFile(mFile);
        }
        return picturepath;
    }

    @Override
    protected void onPostExecute(String path) {
        super.onPostExecute(path);
        mListener.getString(path);
    }

    public interface getStringListener {
        public void getString(String str);
    }
}
