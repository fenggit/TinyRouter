package com.tinyrouter.service;

import com.tinyrouter.api.RouterLog;
import com.tinyrouter.inter.IRouterProvider;

public class ServiceConfigItem<T> {
    public Class<T> parent;
    public Class<? extends T> impl;

    public boolean single;
    public T instance;

    public ServiceConfigItem() {
    }

    public ServiceConfigItem(Class<T> parent, Class<? extends T> impl, T instance, boolean single) {
        this.parent = parent;
        this.impl = impl;
        this.instance = instance;
        this.single = single;
    }

    public T newInstance() {
        RouterLog.i("ServiceConfigItem.newInstance.parent = " + parent);
        RouterLog.i("ServiceConfigItem.newInstance.impl = " + impl);
        RouterLog.i("ServiceConfigItem.newInstance.single = " + single);
        try {
            instance = impl.newInstance();
            if (instance instanceof IRouterProvider) {
                IRouterProvider callback = (IRouterProvider) instance;
                // TODO
                // callback.init();
            }
        } catch (Exception e) {
            RouterLog.e("ServiceConfigItem.newInstance fail", e);
        }

        RouterLog.i("ServiceConfigItem.newInstance.instance = " + instance);

        return instance;
    }
}