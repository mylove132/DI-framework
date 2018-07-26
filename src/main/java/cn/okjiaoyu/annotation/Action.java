package cn.okjiaoyu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: liuzhanhui
 * @Decription: action注解类，定义在方法上
 * @Date: Created in 2018-07-24:15:01
 * Modify date: 2018-07-24:15:01
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {

    /**
     * 请求类型与路径
     * @return
     */
    String value();
}
