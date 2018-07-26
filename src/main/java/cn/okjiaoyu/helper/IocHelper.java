package cn.okjiaoyu.helper;

import cn.okjiaoyu.annotation.Inject;
import cn.okjiaoyu.util.MapUtil;
import cn.okjiaoyu.util.ReflectionUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @Author: liuzhanhui
 * @Decription: 依赖注入助手
 * @Date: Created in 2018-07-24:16:14
 * Modify date: 2018-07-24:16:14
 */
public final class IocHelper {

    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (MapUtil.isNotEmpty(beanMap)){
            for (Map.Entry<Class<?>, Object> map: beanMap.entrySet()){
                Class<?> clazz = map.getKey();
                Object instance = map.getValue();
                //获取类的成员变量
                Field[] beanFields = clazz.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(beanFields)){
                    for (Field field : beanFields){
                        if (field.isAnnotationPresent(Inject.class)){
                            Class<?> beanFieldClass = field.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null){
                                ReflectionUtil.setField(instance, field, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
