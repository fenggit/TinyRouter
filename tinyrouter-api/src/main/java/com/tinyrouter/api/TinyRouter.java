package com.tinyrouter.api;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tinyrouter.inject.IUriInjector;
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

    /**
     * 路由跳转
     *
     * @param context
     * @param router
     */
    public static void navigator(Context context, String router) {
        wrapper(router).navigator(context);
    }

    /**
     * 路由跳转
     *
     * @param context
     * @param router
     * @param callback
     */
    public static void navigator(Context context, String router, IRouterCallback callback) {
        wrapper(router).callback(callback).navigator(context);
    }

    /**
     * 注册在页面，解析参数
     *
     * @param object
     */
    public static void inject(@NonNull Object object) {
        Injector.doInject(object, null);
    }

    /**
     * 注册在页面，解析参数
     *
     * @param object
     * @param uri
     */
    public static void inject(@NonNull Object object, @NonNull Uri uri) {
        Injector.doInject(object, uri);
    }

    public static IUriInjector uriInjector(@NonNull Activity activity) {
        return Injector.uriInjector(activity);
    }

    public static IUriInjector uriInjector(@NonNull Uri uri) {
        return Injector.uriInjector(uri);
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
