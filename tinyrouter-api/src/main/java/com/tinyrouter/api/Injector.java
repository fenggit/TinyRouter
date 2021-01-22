package com.tinyrouter.api;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.tinyrouter.annotation.RouterParam;
import com.tinyrouter.inject.IUriInjector;
import com.tinyrouter.inject.UriInjectorEmptyImpl;
import com.tinyrouter.inject.UriInjectorImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Type;


public class Injector {

    public static void doInject(Object object, @Nullable Uri uri) {
        long now = System.currentTimeMillis();

        IUriInjector uriInjector = null;
        if (object instanceof Activity) {
            uriInjector = uriInjector((Activity) object);
        } else if (uri != null) {
            uriInjector = uriInjector(uri);
        }
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (uriInjector != null) {
                RouterParam param = field.getAnnotation(RouterParam.class);
                if (param != null) {
                    injectRouterParam(object, uriInjector, field, param);
                }
            }
            /*Autowired autowired = field.getAnnotation(Autowired.class);
            if (autowired != null) {
                injectAutowired(object, field, autowired);
            }*/
        }
        RouterLog.i(object.toString() + " inject time =  " + (System.currentTimeMillis() - now));
    }

    private static void injectRouterParam(Object object,
                                          IUriInjector injector,
                                          Field field,
                                          RouterParam param) {
        field.setAccessible(true);
        Type genericType = field.getGenericType();
        String value = injector.getParam(param.value());

        if (value != null) {
            try {
                if (genericType == String.class) {
                    field.set(object, value);
                }

                if (genericType.equals(boolean.class) || genericType.equals(Boolean.class)) {
                    field.set(object, Boolean.valueOf(value));
                } else if (genericType.equals(byte.class) || genericType.equals(Byte.class)) {
                    field.set(object, Byte.valueOf(value));
                } else if (genericType.equals(short.class) || genericType.equals(Short.class)) {
                    field.set(object, Short.valueOf(value));
                } else if (genericType.equals(int.class) || genericType.equals(Integer.class)) {
                    field.set(object, Integer.valueOf(value));
                } else if (genericType.equals(long.class) || genericType.equals(Long.class)) {
                    field.set(object, Long.valueOf(value));
                } else if (genericType.equals(float.class) || genericType.equals(Float.class)) {
                    field.set(object, Float.valueOf(value));
                } else if (genericType.equals(double.class) || genericType.equals(Double.class)) {
                    field.set(object, Double.valueOf(value));
                }
            } catch (Exception e) {
                String msg = "inject: can't inject field %s for %s, e = %s";
                RouterLog.w(String.format(msg, field.getName(), value, e.getMessage()));
            }
        } else {
            RouterLog.w("inject: cant find any param " + field.getName() + " for " + param.value() + " in uri");
        }
    }

    /*private static void injectAutowired(Object object,
                                        Field field,
                                        Autowired autowired) {
        field.setAccessible(true);
        Class<?> type = field.getType();
        try {
            Object value = ModularHelper.get(type);
            field.set(object, value);
        } catch (IllegalAccessException e) {
            String msg = "inject: can't inject field %s for %s, e = %s";
            RouterLog.w(String.format(msg, field.getName(), type, e.getMessage()));
        }
    }

    public static void unInject(Object o) {

    }*/


    public static IUriInjector uriInjector(Activity activity) {
        Intent intent = activity.getIntent();
        Uri data = intent.getData();
        if (data != null) {
            return uriInjector(data);
        }
        return new UriInjectorEmptyImpl();
    }


    public static IUriInjector uriInjector(Uri uri) {
        return new UriInjectorImpl(uri);
    }
}