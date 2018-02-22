package com.yaya.merchant.net;

import android.util.Log;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by 魏新智 on 2016/7/27.
 */
public class LogInterceptor implements Interceptor {

    private static final String TAG = "OkHttp Log";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //the request url
        String url = request.url().toString();
        //the request method
        String method = request.method();
        long t1 = System.nanoTime();
        //打印请求方法，地址
        Log.d(TAG, String.format(Locale.getDefault(),"Sending %s request [url = %s]",method,url));
        //打印请求头
//        Log.d(TAG, "headers:" + request.headers().toString());
        //the request body
        RequestBody requestBody = request.body();
        if(requestBody!= null) {
            StringBuilder sb = new StringBuilder("Request Body [");
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            if(isPlaintext(buffer)){
                sb.append(buffer.readString(charset));
                sb.append(" (Content-Type = ").append(contentType.toString()).append(",")
                        .append(requestBody.contentLength()).append("-byte body)");
            }else {
                sb.append(" (Content-Type = ").append(contentType.toString())
                        .append(",binary ").append(requestBody.contentLength()).append("-byte body omitted)");
            }
            sb.append("]");
            Log.d(TAG, String.format(Locale.getDefault(), "%s %s", method, sb.toString()));
        }
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        //请求地址，请求所用时间
        Log.d(TAG, String.format(Locale.getDefault(),"Received response for [url = %s] in %.1fms",url, (t2-t1)/1e6d));

        //返回的状态是否成功，状态码
        Log.d(TAG, String.format(Locale.CHINA,"Received response is %s ,message[%s],code[%d]",response.isSuccessful()?"success":"fail",response.message(),response.code()));

        //返回的内容
        ResponseBody body = response.body();

        BufferedSource source = body.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = Charset.defaultCharset();
        MediaType contentType = body.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        String bodyString = buffer.clone().readString(charset);
        Log.d(TAG, String.format("Received response json string [%s]",bodyString));

        return response;
    }

    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }
}
