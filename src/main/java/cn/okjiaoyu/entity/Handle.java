package cn.okjiaoyu.entity;

import java.lang.reflect.Method;

/**
 * @Author: liuzhanhui
 * @Decription:
 * @Date: Created in 2018-07-24:16:39
 * Modify date: 2018-07-24:16:39
 */
public class Handle {

    /**
     * controller类
     */
    private Class<?> controllerClass;

    /**
     * action方法
     */
    private Method actionMethod;

    public Handle(Class<?> controllerClass, Method  actionMethod){
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
