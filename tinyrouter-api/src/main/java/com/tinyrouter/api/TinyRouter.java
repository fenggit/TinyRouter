package com.tinyrouter.api;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tinyrouter.inter.IRouterCallback;
import com.tinyrouter.inter.IRouterInterceptor;
import com.tinyrouter.service.ServiceLoadManager;


public class TinyRouter {
    private volatile static TinyRouter instance = null;
    private TinyRouterManager mTinyRouterManager;

    public static TinyRouter getInstance() {
        if (instance == null) {
            synchronized (TinyRouter.class) {
                if (instance == null) {
                    instance = new TinyRouter();
                }
            }
        }
        return instance;
    }

    public synchronized void init(Context context) {
        mTinyRouterManager = new TinyRouterManager(context.getApplicationContext());
    }

    public TinyRouterManager getRouterManager() {
        return mTinyRouterManager;
    }

    public static void navigator(Context context, String router) {
        wrapper(router).navigator(context);
    }

    public static void navigator(Context context, String router, IRouterCallback callback) {
        wrapper(router).callback(callback).navigator(context);
    }

    private static RouterWrapper wrapper(String router) {
        return new RouterWrapper(router);
    }

    /**
     * 全局回调
     */
    public static void setGlobalNavigatorCallback(IRouterCallback globalNavigatorCallback) {
        TinyRouterDispatch.setGlobalNavigatorCallback(globalNavigatorCallback);
    }

    /**
     * 添加全局拦截器
     */
    public static void addGlobalNavigatorInterceptor(IRouterInterceptor interceptor) {
        TinyRouterDispatch.addGlobalNavigatorInterceptor(interceptor);
    }

    /**
     * 删除全局拦截器
     */
    public static void removeGlobalNavigatorInterceptor(IRouterInterceptor interceptor) {
        TinyRouterDispatch.removeGlobalNavigatorInterceptor(interceptor);
    }

    public static <T> T get(@NonNull Class<T> parent) {
        RouterLog.d("get: parent " + parent);
        T service = ServiceLoadManager.getInstance().getService(parent);
        RouterLog.d(parent + " --> " + service);
        return service;
    }

}
