package com.tinyrouter.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.WildcardTypeName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Modifier;

/**
 * <pre>
 * public interface IRouterLoader {
 *     Map<String, Class<? extends Activity>> getActivityMap();
 *
 *     Map<String, Class<? extends IRouterProcessor>> getProcessorMap();
 *
 *     Map<Class, ServiceConfigItem> getServiceMap();
 * }
 * </pre>
 */
public class RouterMethodSpec {

    public static List<MethodSpec.Builder> getAllSpec() {
        List<MethodSpec.Builder> list = new ArrayList<>();
        list.add(getActivitySpec());
        list.add(getProcessorSpec());
        list.add(getServiceSpec());
        return list;
    }

    /**
     * Map<String, Class<? extends Activity>> getActivityMap();
     *
     * @return
     */
    public static MethodSpec.Builder getActivitySpec() {
        return MethodSpec.methodBuilder("getActivityMap")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(
                        ParameterizedTypeName.get(
                                ClassName.get(Map.class),
                                ClassName.get(String.class),
                                ParameterizedTypeName.get(ClassName.get(Class.class), WildcardTypeName.subtypeOf(ClassName.get("android.app", "Activity"))))
                )
                .addStatement("Map<String,Class<? extends Activity>> list = new $T<>()", HashMap.class);
    }

    /**
     * Map<String, Class<? extends IRouterProcessor>> getProcessorMap();
     *
     * @return
     */
    public static MethodSpec.Builder getProcessorSpec() {
        return MethodSpec.methodBuilder("getProcessorMap")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(
                        ParameterizedTypeName.get(
                                ClassName.get(Map.class),
                                ClassName.get(String.class),
                                ParameterizedTypeName.get(
                                        ClassName.get(Class.class),
                                        WildcardTypeName.subtypeOf(ClassName.get("com.tinyrouter.inter", "IRouterProcessor"))
                                )
                        )
                )
                .addStatement("Map<String,Class<? extends IRouterProcessor>> list = new $T<>()", HashMap.class);
    }

    /**
     * Map<Class, ServiceConfigItem> getServiceMap();
     *
     * @return
     */
    public static MethodSpec.Builder getServiceSpec() {
        return MethodSpec.methodBuilder("getServiceMap")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(
                        ParameterizedTypeName.get(
                                ClassName.get(Map.class),
                                ClassName.get(Class.class),
                                ClassName.get("com.tinyrouter.service", "ServiceConfigItem"))
                )
                .addStatement("Map<Class, ServiceConfigItem> list = new $T<>()", HashMap.class);
    }
}
