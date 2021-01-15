package com.tinyrouter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 路由注解
 */
@Target(ElementType.TYPE) // 接口、类、枚举
@Retention(RetentionPolicy.RUNTIME) // 注解会在class字节码文件中存在，在运行时可以通过反射获取到
public @interface Router {
    /**
     * 路由路径
     *
     * @return
     */
    String[] paths();

    /**
     * 描述信息
     *
     * @return
     */
    String desc() default "";

    /**
     * 是否需要登录
     *
     * @return
     */
    boolean needLogin() default false;
}