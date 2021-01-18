package com.tinyrouter.inter;

import android.net.Uri;

/**
 * Author: HEFENG
 * Date: 2021-1-18 10:16
 * Description: 拦截器
 */
public interface IRouterInterceptor {
    /**
     * 是否拦截
     *
     * @param uri
     * @return
     */
    boolean next(Uri uri);
}
