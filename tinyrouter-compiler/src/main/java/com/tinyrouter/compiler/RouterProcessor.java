package com.tinyrouter.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.tinyrouter.annotation.Router;
import com.tinyrouter.annotation.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;

@AutoService(Processor.class)
public class RouterProcessor extends BaseProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        RouterLog.i("========================= RouteProcessor start =========================");

        if (set.isEmpty()) {
            return false;
        }

        List<MethodSpec.Builder> spec = RouterMethodSpec.getAllSpec();

        Set<? extends Element> routerList = roundEnvironment.getElementsAnnotatedWith(Router.class);

        // router 遍历所有路由信息
        for (Element element : routerList) {
            if (element.getKind() != ElementKind.CLASS) {
                continue;
            }

            Router router = element.getAnnotation(Router.class);

            TypeMirror typeMirror = element.asType();//example: com.example.myrouter.MainActivity

            String[] list = router.paths();

            TypeElement activityType = mElements.getTypeElement(RouterConsts.KEY_ACTIVITY);
            TypeElement processorType = mElements.getTypeElement(RouterConsts.KEY_PROCESSOR);

            // debug
            for (String r : list) {
                RouterLog.i(">>> RouteProcessor router: " + r);
            }

            if (mTypes.isSubtype(typeMirror, activityType.asType())) {
                // activity
                for (String r : list) {
                    spec.get(0).addStatement("list.put($S,$T.class)", r, element.asType());
                }

            } else if (mTypes.isSubtype(typeMirror, processorType.asType())) {
                // Processor
                for (String r : list) {
                    spec.get(1).addStatement("list.put($S,$T.class)", r, element.asType());
                }

            } else {
                RouterLog.i(">>> RouteProcessor router not support: " + typeMirror + " <<<");
            }
        }

        // Service
        Set<? extends Element> serviceList = roundEnvironment.getElementsAnnotatedWith(Service.class);
        for (Element element : serviceList) {
            if (element.getKind() != ElementKind.CLASS) {  //class
                continue;
            }

            TypeMirror typeMirror = element.asType();//example: com.tutorgroup.moduleroom.impl.RoomServiceImpl

            // 无法获取值
            Service service = element.getAnnotation(Service.class);

            RouterLog.i(">>> RouteProcessor service 0 : " + typeMirror);                    // com.tutorgroup.modulebase.service.IRoomService
            RouterLog.i(">>> RouteProcessor service 1 : " + getClassFromAnnotationV2(element));  // com.tutorgroup.moduleroom.impl.RoomServiceImpl
            // service annotation
            //TypeElement serviceType = mElements.getTypeElement(RouterConsts.KEY_SERVICE_CLASS);
            // Attempt to access Class object for TypeMirror com.tutorgroup.modulebase.service.IRoomService
            //RouterLog.i(">>> RouteProcessor router: " + service.parent() + "<<<");

            Object parent = getClassFromAnnotation(element, "parent"); // com.tutorgroup.modulebase.service.IRoomService

            Object isSingle = getClassFromAnnotation(element, "single");
            if (isSingle == null) {
                isSingle = true;
            }

            // list.put(IRoomService.class,new ServiceConfigItem(Class<T> parent, Class<? extends T> impl, boolean single))
            spec.get(2).addStatement("list.put($T.class,new $T($T.class,$T.class, new $T() ,$L))",
                    parent,                     // IRoomService.class
                    //serviceType.asType(),       // new ServiceConfigItem(,,,,)
                    ParameterizedTypeName.get(ClassName.get(RouterConsts.KEY_SERVICE_PACKAGE, RouterConsts.KEY_SERVICE_NAME), TypeName.get((TypeMirror) parent)), // new ServiceConfigItem(,,,,)
                    parent,                                                   // IRoomService.class
                    typeMirror,                                               // RoomServiceImpl.class
                    typeMirror,                                               // new RoomServiceImpl()
                    isSingle                                                  // true
            );
        }

        spec.get(0).addStatement("return list");
        spec.get(1).addStatement("return list");
        spec.get(2).addStatement("return list");

        TypeSpec typeSpec = TypeSpec.classBuilder("RouterLoader$" + moduleName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addSuperinterface(ClassName.get(RouterConsts.KEY_ROUTER_LOADER_PACKAGE, RouterConsts.KEY_ROUTER_LOADER_NAME))
                .addMethod(spec.get(0).build())
                .addMethod(spec.get(1).build())
                .addMethod(spec.get(2).build())
                .build();

        JavaFile javaFile = JavaFile.builder(RouterConsts.ROUTE_ROOT_PAKCAGE, typeSpec).build();
        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
            RouterLog.i("RouteProcessor Error : "+e.getMessage());
        }

        RouterLog.i("========================= RouteProcessor end ===========================");
        return true;
    }

    private Object getClassFromAnnotation(Element key, String methodNName) {
        List<? extends AnnotationMirror> annotationMirrors = key.getAnnotationMirrors();
        for (AnnotationMirror annotationMirror : annotationMirrors) {
            if (Service.class.getName().equals(annotationMirror.getAnnotationType().toString())) {
                Set<? extends ExecutableElement> keySet = annotationMirror.getElementValues().keySet();
                for (ExecutableElement executableElement : keySet) {
                    if (Objects.equals(executableElement.getSimpleName().toString(), methodNName)) {
                        return annotationMirror.getElementValues().get(executableElement).getValue();
                    }
                }
            }
        }
        return null;
    }

    private String getClassFromAnnotationV2(Element key) {
        try {
            key.getAnnotation(Service.class).parent();
        } catch (MirroredTypeException e) {
            TypeMirror typeMirror = e.getTypeMirror();
            return typeMirror.toString();
        }
        return null;
    }
}