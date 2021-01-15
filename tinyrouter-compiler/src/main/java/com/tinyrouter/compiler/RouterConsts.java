package com.tinyrouter.compiler;

public class RouterConsts {
    public static final String KEY_SERVICE_PACKAGE = "com.tinyrouter.service";
    public static final String KEY_SERVICE_NAME = "ServiceConfigItem";
    public static final String KEY_SERVICE_CLASS = KEY_SERVICE_PACKAGE + "." + KEY_SERVICE_NAME;

    public static final String KEY_ROUTER_LOADER_PACKAGE = "com.tinyrouter.inter";
    public static final String KEY_ROUTER_LOADER_NAME = "IRouterLoader";
    public static final String KEY_ACTIVITY = "android.app.Activity";
    public static final String KEY_PROCESSOR = "com.tinyrouter.inter.IRouterProcessor";

    public static final String KEY_MODULE_NAME = "TROUTER_MODULE_NAME";

    public static final String ROUTE_ROOT_PAKCAGE = "com.tinyrouter.compiler";

    public static final String NO_MODULE_NAME_TIPS = "These no module name, at 'build.gradle', like :\n" +
            "android {\n" +
            "    defaultConfig {\n" +
            "        ...\n" +
            "        javaCompileOptions {\n" +
            "            annotationProcessorOptions {\n" +
            "                arguments = [TROUTER_MODULE_NAME: project.getName()]\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}\n";
}
