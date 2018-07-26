package cn.okjiaoyu.helper;

import cn.okjiaoyu.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: liuzhanhui
 * @Decription:
 * @Date: Created in 2018-07-24:16:02
 * Modify date: 2018-07-24:16:02
 */
public class BeanHelper {

    /**
     * 定义Bean映射（用于存放bean类与bean实例）
     */
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>();

    static {
        Set<Class<?>> classSet = ClassHelper.getBeanClassSet();
        for (Class<?> clazz : classSet){
            BEAN_MAP.put(clazz, ReflectionUtil.newInstance(clazz));
        }
    }

    /**
     * 获得所有bean的map
     * @return
     */
    public static Map<Class<?>, Object> getBeanMap(){
        return BEAN_MAP;
    }

    /**
     * 根据类获取实例对象
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> cls){
        if (!BEAN_MAP.containsKey(cls)){
            throw new RuntimeException("can not get bean by class:"+cls);
        }
        return (T) BEAN_MAP.get(cls);
    }
}
