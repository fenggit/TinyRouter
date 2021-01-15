package com.tinyrouter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 路由参数
 */
@Target(ElementType.FIELD)  //字段、枚举的常量
@Retention(RetentionPolicy.RUNTIME)
public @interface RouterParam {
    String value();
}
