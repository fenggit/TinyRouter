# TinyRouter
极简，轻量级的路由库

# 目的
- 熟悉路由框架的整个流程
- 熟悉APT注解处理器
- 掌握代码自动生成

# 支持的功能

1. 支持模块化路由跳转
2. 支持设置拦截器
3. 支持模块化通信

# 涉及到的技术

- APT：Annotation Processing Tool，注解处理器
- 注解
- 反射

# 使用教程


参考：
> https://juejin.cn/post/6844903923606618126
> https://www.jianshu.com/p/0d6860def79d
> https://www.shangmayuan.com/a/6b7ff9f99fe14fe1bb9c19eb.html
> https://blog.csdn.net/qq_19431333/article/details/89431041
>

## 需要解决的问题
1. 支持模块化
2. 拦截器
3. 登录参数

## Debug
> https://www.cnblogs.com/tony-yang-flutter/p/12251645.html

```
./gradlew --no-daemon -Dorg.gradle.debug=true :app:clean :app:compileDebugJavaWithJavac
```

> 无论如何也不会自动生成 META-INF，导致编译时无法识别 Processor，最后只能手动添加：
  在 src/main 目录下新建 /resources/META-INF/services/javax.annotation.processing.Processor 目录和文件

```
public class RouterLoader$test implements RouterLoader {
  @Override
  public void loadRouter(Set<Route> routerSet) {
    routerSet.add(RouterBuilder.buildRouter(/home/1, MainActivity.class, false));
    routerSet.add(RouterBuilder.buildRouter(/home/3, MyProcess.class, false));
    routerSet.add(RouterBuilder.buildRouter(/home/2, SecondActivity.class, false));
  }
}
```

## 注意

1. myrouter-compiler ： 模块必须是Java library

2. kotlin 引用
``` 
plugins {
    id 'kotlin-kapt'
}
kapt project(':myrouter-compiler')
```

生成路径
> build/generated/source/kapt/debug/packagename/生成的类名
exp
> build/generated/source/kapt/debug/com/simple/modular/compiler/RouterLoader$CodeGenerator.java


java 方式
``` 
annotationProcessor project(':myrouter-compiler')
```

生成路径
> build/generated/ap_generated_sources/debug/out/packagename/生成的类名
exp
> build/generated/source/kapt/debug/com/simple/modular/compiler/RouterLoader$CodeGenerator.java


