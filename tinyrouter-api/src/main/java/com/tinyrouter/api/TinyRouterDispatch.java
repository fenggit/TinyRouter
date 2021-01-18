package com.tinyrouter.api;

import android.app.Activity;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.tinyrouter.exception.RouterException;
import com.tinyrouter.inter.IRouterCallback;
import com.tinyrouter.inter.IRouterInterceptor;
import com.tinyrouter.inter.IRouterProcessor;

import java.util.ArrayList;
import java.util.List;

public class TinyRouterDispatch {

    private static IRouterCallback globalNavigatorCallback = null;

    private static List<IRouterInterceptor> globalNavigatorInterceptor = new ArrayList<>();

    public static void setGlobalNavigatorCallback(IRouterCallback callback) {
        TinyRouterDispatch.globalNavigatorCallback = callback;
    }

    public static void addGlobalNavigatorInterceptor(IRouterInterceptor interceptor) {
        globalNavigatorInterceptor.add(interceptor);
    }

    public static void removeGlobalNavigatorInterceptor(IRouterInterceptor interceptor) {
        globalNavigatorInterceptor.remove(interceptor);
    }

    public static void dispatch(RouterRequest request) {
        RouterLog.i("TinyRouterDispatch.dispatch = " + request);

        IRouterCallback callback = new TinyRouterMultiCallback(globalNavigatorCallback, request.callback);
        Uri uri = request.uri;
        try {
            String s = uri.buildUpon().clearQuery().build().toString();
            RouterLog.d(uri + " ---> " + s);

            boolean isNext = true;

            List<IRouterInterceptor> interceptors = new ArrayList<>(globalNavigatorInterceptor);
            interceptors.addAll(request.interceptors);

            if (!interceptors.isEmpty()) {
                for (IRouterInterceptor interceptor : interceptors) {
                    isNext = interceptor.next(uri);
                    RouterLog.d(interceptor.getClass().getSimpleName() + ".next --> " + isNext);
                    if (!isNext) {
                        break;
                    }
                }
            }

            if (isNext) {
                TinyRouterManager manager = TinyRouter.getInstance().getRouterManager();

                Class<? extends Activity> activity = manager.getRouterActivity(s);
                Class<? extends IRouterProcessor> process = manager.getRouterProcessor(s);

                if (activity != null) {
                    request.startActivity(activity);
                } else if (process != null) {
                    request.startProcess(process);
                } else {
                    RouterLog.e("cant find router [" + request.uri + "]");
                    callback.onFail(request.uri, new RouterException("cant find router [" + request.uri + "]"));
                    return;
                }
            }
            callback.onSuccess(uri);
        } catch (RouterException e) {
            callback.onFail(uri, e);
        } catch (Exception e) {
            callback.onFail(uri, new RouterException(e));
        }
    }
}
