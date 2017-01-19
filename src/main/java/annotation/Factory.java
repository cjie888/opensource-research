package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description 胡成杰(chengjiehu@creditease.cn)
 * 2016/11/16 20:23
 */
@Target(ElementType.TYPE) @Retention(RetentionPolicy.CLASS)
public @interface Factory {

    /**
     * 工厂的名字
     */
    Class type();

    /**
     * 用来表示生成哪个对象的唯一id
     */
    String id();
}
