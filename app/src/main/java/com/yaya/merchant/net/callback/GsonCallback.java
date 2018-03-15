package com.yaya.merchant.net.callback;

import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.toroke.okhttp.JsonResponse;
import com.toroke.okhttp.BaseData;
import com.yaya.merchant.util.ToastUtil;

import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;

public abstract class GsonCallback<K extends Serializable> extends BaseCallback<K> {

    private Gson gson;
    private Class<K> clazz;
    private Type type;

    public GsonCallback(Class<K> clazz) {
        gson = new Gson();
        this.clazz = clazz;
    }

    public GsonCallback(Type type) {
        gson = new Gson();
        this.type = type;
    }

    public GsonCallback(Class<K> clazz, View view) {
        super(view);
        gson = new Gson();
        this.clazz = clazz;
    }

    @Override
    public K parseItem(JSONObject jsonObject) {
        if (clazz != null) {
            return gson.fromJson(jsonObject.toString(), clazz);
        } else {
            return gson.fromJson(jsonObject.toString(), type);
        }
    }

    @Override
    public abstract void onSucceed(JsonResponse<K> response);

    @Override
    public void onFailed(JsonResponse<K> response) {
        if (!TextUtils.isEmpty(response.getData().getError())) {
            ToastUtil.toast(response.getData().getError());
        }
    }
}
