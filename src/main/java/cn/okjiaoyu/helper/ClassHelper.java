package cn.okjiaoyu.helper;


import cn.okjiaoyu.annotation.Controller;
import cn.okjiaoyu.annotation.Service;
import cn.okjiaoyu.util.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: liuzhanhui
 * @Decription: 类操作助手
 * @Date: Created in 2018-07-24:15:31
 * Modify date: 2018-07-24:15:31
 */
public final class ClassHelper {

    /**
     * 定义类集合（用于加载所存放的类）
     */
    private static final Set<Class<?>> CLASS_SET;

    static {
        String appBasePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(appBasePackage);
    }

    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    /**
     * 获取所有的service注解
     * @return
     */
    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> serviceClassSet = new HashSet<Class<?>>();
        for (Class<?> clazz : CLASS_SET){
            if (clazz.isAnnotationPresent(Service.class)){
                serviceClassSet.add(clazz);
            }
        }
        return serviceClassSet;
    }

    /**
     * 获取所有的controller注解
     * @return
     */
    public static Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> controllerClassSet = new HashSet<Class<?>>();
        for (Class<?> clazz : CLASS_SET){
            if (clazz.isAnnotationPresent(Controller.class)){
                controllerClassSet.add(clazz);
            }
        }
        return controllerClassSet;
    }

    /**
     * 获取应用包下的所有bean类
     * @return
     */
    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }
}
