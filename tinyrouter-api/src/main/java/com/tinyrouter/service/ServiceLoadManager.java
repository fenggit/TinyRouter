package com.tinyrouter.service;


import com.tinyrouter.api.RouterLog;
import com.tinyrouter.api.TinyRouter;
import com.tinyrouter.exception.RouterException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ServiceLoadManager {

    private final static ServiceLoadManager instance = new ServiceLoadManager();

    public static ServiceLoadManager getInstance() {
        return instance;
    }

    private final Map<Class<?>, ServiceConfigItem> cache;

    private ServiceLoadManager() {
        cache = new ConcurrentHashMap<>();
    }

    public synchronized <T> T getService(Class<T> parent) {
        ServiceConfigItem<T> item = cache.get(parent);
        if (item != null) { // 缓存
            if (item.single && item.instance != null) {
                return item.instance;
            }
            return item.newInstance();
        }

        try {
            item = TinyRouter.getInstance().getRouterManager().getServiceConfig(parent);
            if (item != null) {
                cache.put(parent, item);
                return item.newInstance();
            }
        } catch (Exception e) {
            throw new RouterException("get service " + parent + " fail", e);
        }
        RouterLog.e("cant find server " + parent);
        return null;
        //throw new ModularException("cant find server " + parent);
    }
}