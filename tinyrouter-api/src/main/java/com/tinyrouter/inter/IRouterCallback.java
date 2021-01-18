package com.tinyrouter.inter;

import android.net.Uri;

import androidx.annotation.NonNull;

/**
 * Author: HEFENG
 * Date: 2021-1-18 10:16
 * Description: 回调函数
 */
public interface IRouterCallback {
    void onFail(@NonNull Uri uri, @NonNull Exception e);

    void onSuccess(@NonNull Uri uri);
}
