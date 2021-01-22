package com.tinyrouter.inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Type;

public interface IUriInjector {

    /**
     * 获取url中的参数
     */
    @Nullable
    String getParam(@NonNull String name);

    /**
     * 获取对象参数， 使用gson进行序列化
     */
    @Nullable
    <T> T getObjectParam(@NonNull String name, @NonNull Type type);
}