package com.tinyrouter.api;

import android.content.Context;


public class TinyRouter {
    private volatile static TinyRouter instance = null;
    private TinyRouterManager mTinyRouterManager;

    public synchronized void init(Context context) {
        mTinyRouterManager = new TinyRouterManager(context.getApplicationContext());
    }

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

    public TinyRouterManager getRouterManager() {
        return mTinyRouterManager;
    }

    public void navigation(String router) {

    }
}
