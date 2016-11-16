package annotation;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * @description 胡成杰(chengjiehu@creditease.cn)
 * 2016/11/16 20:12
 */
//@SupportedSourceVersion(SourceVersion.latestSupported())
//@SupportedAnnotationTypes({
//        // 合法注解全名的集合
//})
public class MyProcessor extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment env){ }

    @Override
    public boolean process(Set<? extends TypeElement> annoations, RoundEnvironment env) {
        return false;
    }

//    @Override
//    public Set<String> getSupportedAnnotationTypes() {
//        return null;
//    }
//
//    @Override
//    public SourceVersion getSupportedSourceVersion() {
//        return SourceVersion.latestSupported();
//    }

}