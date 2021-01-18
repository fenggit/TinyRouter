package com.tinyrouter.api;

import android.net.Uri;

import com.tinyrouter.inter.IRouterCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: HEFENG959
 * Date: 2021-1-18 10:54
 * Description:
 */
public class TinyRouterMultiCallback implements IRouterCallback {
    private List<IRouterCallback> list;

    public TinyRouterMultiCallback(IRouterCallback... callbacks) {
        list = new ArrayList<>(callbacks.length);
        list.addAll(Arrays.asList(callbacks));
    }

    @Override
    public void onFail(Uri uri, Exception e) {
        for (IRouterCallback callback : list) {
            if (callback != null) {
                callback.onFail(uri, e);
            }
        }
    }

    @Override
    public void onSuccess(Uri uri) {
        for (IRouterCallback callback : list) {
            if (callback != null) {
                callback.onSuccess(uri);
            }
        }
    }
}
