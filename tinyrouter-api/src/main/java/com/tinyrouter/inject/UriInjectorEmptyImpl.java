package com.tinyrouter.inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Type;


public class UriInjectorEmptyImpl implements IUriInjector {

    @Nullable
    @Override
    public String getParam(@NonNull String name) {
        return null;
    }

    @Nullable
    @Override
    public <T> T getObjectParam(@NonNull String name, @NonNull Type type) {
        return null;
    }
}