package com.tinyrouter.api;

import android.util.Log;

public class RouterLog {
    public static final String TAG = "router";

    public static void i(String message) {
        Log.i(TAG, message);
    }

    public static void w(String message) {
        Log.i(TAG, message);
    }

    public static void e(String message) {
        Log.e(TAG, message);
    }

    public static void e(String message, Throwable e) {
        Log.e(TAG, message, e);
    }
}
