package com.tinyrouter.inter;

import android.app.Activity;

import com.tinyrouter.service.ServiceConfigItem;

import java.util.Map;

/**
 * 用于apt代码生成
 */
public interface IRouterLoader {

    Map<String, Class<? extends Activity>> getActivityMap();

    Map<String, Class<? extends IRouterProcessor>> getProcessorMap();

    Map<Class, ServiceConfigItem> getServiceMap();
}
