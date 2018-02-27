package com.yaya.merchant.net.callback;

import android.view.View;

import com.google.gson.Gson;
import com.toroke.okhttp.JsonResponse;

import org.json.JSONObject;

import java.io.Serializable;

public abstract class GsonCallback<K extends Serializable> extends BaseCallback<K>{

    private Class<K> clazz;
//    private Type type;
    private Gson gson;

    public GsonCallback(Class<K> clazz){
        this.clazz = clazz;
//        type = new TypeToken<K>(){}.getType();
        gson = new Gson();
    }

    public GsonCallback(Class<K> clazz, View view){
        super(view);
        this.clazz = clazz;
        gson = new Gson();
    }
    @Override
    public K parseItem(JSONObject jsonObject) {
        String json = jsonObject.toString();
        return gson.fromJson(jsonObject.toString(), clazz);
    }

    @Override
    public abstract void onSucceed(JsonResponse<K> response);

    @Override
    public void onFailed(JsonResponse<K> response) {
        /*ToastHelper.show(response.getMsg());*/
    }
}
