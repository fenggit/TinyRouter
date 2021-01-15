package com.tinyrouter.compiler;

import com.tinyrouter.annotation.Router;
import com.tinyrouter.annotation.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public abstract class BaseProcessor extends AbstractProcessor {
    Filer mFiler;
    Elements mElements;
    Types mTypes;
    // Module name, maybe its 'app' or others
    String moduleName = null;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        RouterLog.i(">>> RouteProcessor init. <<<");

        mFiler = processingEnvironment.getFiler();
        mElements = processingEnvironment.getElementUtils();
        mTypes = processingEnvironment.getTypeUtils();

        // Attempt to get user configuration [moduleName]
        Map<String, String> options = processingEnv.getOptions();
        if (processingEnv.getOptions() != null && !processingEnv.getOptions().isEmpty()) {
            moduleName = options.get(RouterConsts.KEY_MODULE_NAME);
        }

        if (moduleName != null && !moduleName.isEmpty()) {
            moduleName = moduleName.replaceAll("[^0-9a-zA-Z_]+", "");
            RouterLog.i("The user has configuration the module name, it was [" + moduleName + "]");
        } else {
            throw new RuntimeException("Simple Router::Compiler >>> No module name, for more information, look at :" + RouterConsts.NO_MODULE_NAME_TIPS);
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> list = new HashSet<>();
        list.add(Router.class.getCanonicalName());
        list.add(Service.class.getCanonicalName());
        return list;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}
