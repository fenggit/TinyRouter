package com.tinyrouter.inject;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.tinyrouter.api.RouterLog;

import java.lang.reflect.Type;


public class UriInjectorImpl implements IUriInjector {

    @NonNull
    private final Uri uri;
    private Gson gson = new Gson();

    public UriInjectorImpl(@NonNull Uri uri) {
        this.uri = uri;
    }

    @Override
    @Nullable
    public String getParam(@NonNull String name) {
        return uri.getQueryParameter(name);
    }

    @Override
    @Nullable
    public <T> T getObjectParam(@NonNull String name, @NonNull Type type) {
        String s = getParam(name);
        try {
            return gson.fromJson(s, type);
        } catch (Exception e) {
            RouterLog.e("getObjectParam error e = " + e.getMessage());
        }
        return null;
    }

    @Override
    public String toString() {
        return "UriInjectorImpl{" +
                "uri=" + uri +
                '}';
    }
}