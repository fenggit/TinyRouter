package com.tinyrouter.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.tinyrouter.exception.RouterException;
import com.tinyrouter.inter.IRouterCallback;
import com.tinyrouter.inter.IRouterInterceptor;
import com.tinyrouter.inter.IRouterProcessor;

import java.util.ArrayList;
import java.util.List;


/**
 * Author: HEFENG
 * Date: 2021-1-18 10:16
 * Description: 请求路由数据封装
 */
public class RouterRequest {
    public Context context;

    public Uri uri;

    public int flag;

    public int req;

    public IRouterCallback callback;

    @NonNull
    public final List<IRouterInterceptor> interceptors = new ArrayList<>();

    void startActivity(@NonNull Class<? extends Activity> clazz) {
        Intent intent = new Intent(context, clazz);
        intent.setData(uri);
        intent.setFlags(flag);

        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (req > 0) {
                activity.startActivityForResult(intent, req);
            } else {
                activity.startActivity(intent);
            }
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    void startProcess(@NonNull Class<? extends IRouterProcessor> process) throws Exception {
        IRouterProcessor processor = process.newInstance();
        if (processor != null) {
            processor.process(context, uri);
        } else {
            throw new RouterException("cant new instance process " + process);
        }
    }

    @Override
    public String toString() {
        return "RouterRequest{" +
                "context=" + context +
                ", uri=" + uri +
                ", flag=" + flag +
                ", req=" + req +
                ", callback=" + callback +
                ", interceptors=" + interceptors +
                '}';
    }
}
