package cn.okjiaoyu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: liuzhanhui
 * @Decription: controller注解类，定义在类上
 * @Date: Created in 2018-07-24:14:59
 * Modify date: 2018-07-24:14:59
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
}
