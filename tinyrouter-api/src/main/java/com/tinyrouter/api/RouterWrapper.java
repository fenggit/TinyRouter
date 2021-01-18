package com.tinyrouter.api;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.tinyrouter.inter.IRouterCallback;
import com.tinyrouter.inter.IRouterInterceptor;

import java.util.ArrayList;
import java.util.List;


/**
 * Author: HEFENG
 * Date: 2021-1-18 10:16
 * Description: 请求路由数据封装
 */
public class RouterWrapper {
    private Uri.Builder uriBuilder;
    private IRouterCallback callback;
    private int flag;
    private int reqCode;
    private List<IRouterInterceptor> interceptors;
    private Gson gson = new Gson();

    RouterWrapper(String router) {
        if (TextUtils.isEmpty(router)) {
            throw new IllegalArgumentException("router is empty");
        }
        uriBuilder = Uri.parse(router).buildUpon();
        interceptors = new ArrayList<>();
    }

    /**
     * 以字符串进行传递
     */
    public RouterWrapper withParam(@NonNull String key, Object value) {
        if (value != null) {
            uriBuilder.appendQueryParameter(key, String.valueOf(value));
        } else {
            uriBuilder.appendQueryParameter(key, "");
        }
        return this;
    }

    /**
     * 以json进行传递
     */
    public RouterWrapper withJson(@NonNull String key, Object value) {
        if (value != null) {
            uriBuilder.appendQueryParameter(key, gson.toJson(value));
        } else {
            uriBuilder.appendQueryParameter(key, "");
        }
        return this;
    }

    public RouterWrapper callback(IRouterCallback callback) {
        this.callback = callback;
        return this;
    }

    public RouterWrapper flag(int flag) {
        this.flag = flag;
        return this;
    }

    public RouterWrapper reqCode(int reqCode) {
        this.reqCode = reqCode;
        return this;
    }

    public RouterWrapper addInterceptor(IRouterInterceptor interceptor) {
        interceptors.add(interceptor);
        return this;
    }

    public void navigator(Context context) {
        RouterRequest request = new RouterRequest();
        request.context = context;
        request.uri = uriBuilder.build();
        request.callback = callback;
        request.req = reqCode;
        request.flag = flag;
        request.interceptors.addAll(interceptors);
        TinyRouterDispatch.dispatch(request);
    }

    public Uri uri() {
        return uriBuilder.build();
    }
}
