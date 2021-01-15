package com.tinyrouter.cls;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.tinyrouter.api.RouterLog;


public class PackageUtils {
    private static String NEW_VERSION_NAME;
    private static int NEW_VERSION_CODE;
    public static final String AROUTER_SP_CACHE_KEY = "SP_AROUTER_CACHE2";
    public static final String AROUTER_SP_KEY_MAP = "ROUTER_MAP2";

    public static final String LAST_VERSION_NAME = "LAST_VERSION_NAME2";
    public static final String LAST_VERSION_CODE = "LAST_VERSION_CODE2";

    public static final String ROUTE_ROOT_PACKAGE = "com.tinyrouter.compiler";

    public static boolean isNewVersion(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (null != packageInfo) {
            String versionName = packageInfo.versionName;
            int versionCode = packageInfo.versionCode;

            SharedPreferences sp = context.getSharedPreferences(AROUTER_SP_CACHE_KEY, Context.MODE_PRIVATE);
            if (!versionName.equals(sp.getString(LAST_VERSION_NAME, null)) || versionCode != sp.getInt(LAST_VERSION_CODE, -1)) {
                // new version
                NEW_VERSION_NAME = versionName;
                NEW_VERSION_CODE = versionCode;

                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static void updateVersion(Context context) {
        if (!android.text.TextUtils.isEmpty(NEW_VERSION_NAME) && NEW_VERSION_CODE != 0) {
            SharedPreferences sp = context.getSharedPreferences(AROUTER_SP_CACHE_KEY, Context.MODE_PRIVATE);
            sp.edit().putString(LAST_VERSION_NAME, NEW_VERSION_NAME).putInt(LAST_VERSION_CODE, NEW_VERSION_CODE).apply();
        }
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
        } catch (Exception ex) {
            RouterLog.e("Get package info error.");
        }

        return packageInfo;
    }
}
