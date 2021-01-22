# TinyRouter
Android 极简，轻量级的路由库

# 目的
- 熟悉路由框架的整个流程
- 熟悉APT注解处理器
- 掌握代码自动生成
- 掌握跨模块通信

# 支持的功能
1. 支持模块化路由跳转
2. 支持设置拦截器
3. 支持模块化通信
4. 支持自动注册路由

# 涉及到的技术
- APT：Annotation Processing Tool，注解处理器
- 注解
- 反射

# 模块
## 业务测试

- module-base：业务基础
- module-login：登录模块
- module-home：首页模块

## 库

- tinyrouter-annotation：注解信息
- tinyrouter-api：对外提供api
- tinyrouter-compiler：自动处理注解信息,模块必须是Java library

# 使用教程

1. 初始化框架
```kotlin
public class App : Application() {

    override fun onCreate() {
        super.onCreate()
        TinyRouter.getInstance().init(this)
    }
}
```

2. 使用路由
```kotlin
@Router(paths = ["myrouter://login"], desc = "登录模块")
class LoginActivity : AppCompatActivity() {

    @RouterParam("fromwhere")
    var fromwhere: String? = null
     override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            // 用于获取传递过来的参数
            TinyRouter.inject(this)
    }
}
```

跳转路由
```kotlin
 TinyRouter.navigator(this, "myrouter://login?fromwhere=app")
```

3. 模块化通信
在module-base：业务基础，定义接口，接口下沉到base
```java
public interface ILoginManager {
    public String getUserName();
}

```

在module-login登录模块实现
```java
@Service(parent = ILoginManager.class)
public class LoginManager implements ILoginManager {
    @Override
    public String getUserName() {
        return "zhangsan";
    }
}
```

在module-home：首页模块获取用户名
```java
 String name = TinyRouter.get(ILoginManager.class).getUserName();
```

# 技术相关

## 注解处理器 Debug模式
> https://www.cnblogs.com/tony-yang-flutter/p/12251645.html

## 注意
> 无论如何也不会自动生成 META-INF，导致编译时无法识别 Processor，最后只能手动添加：
  在 src/main 目录下新建 /resources/META-INF/services/javax.annotation.processing.Processor 目录和文件


### kotlin 方式
``` 
plugins {
    id 'kotlin-kapt'
}
kapt project(':tinyrouter-compiler')
```

生成类路径
> build/generated/source/kapt/debug/packagename/生成的类名
exp
> build/generated/source/kapt/debug/com/simple/modular/compiler/RouterLoader$CodeGenerator.java

### java 方式
``` 
annotationProcessor project(':tinyrouter-compiler')
```

生成类路径
> build/generated/ap_generated_sources/debug/out/packagename/生成的类名
exp
> build/generated/source/kapt/debug/com/simple/modular/compiler/RouterLoader$CodeGenerator.java

# 参考资料：
> https://juejin.cn/post/6844903923606618126
> https://www.jianshu.com/p/0d6860def79d
> https://www.shangmayuan.com/a/6b7ff9f99fe14fe1bb9c19eb.html
> https://blog.csdn.net/qq_19431333/article/details/89431041

