package cn.okjiaoyu.helper;

import cn.okjiaoyu.util.ClassUtil;
import org.apache.commons.lang3.ClassUtils;

/**
 * @Author: liuzhanhui
 * @Decription: 加载helper类，执行静态代码
 * @Date: Created in 2018-07-24:17:07
 * Modify date: 2018-07-24:17:07
 */
public final class HelperLoader {

    public static void init(){
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };

        for (Class<?> clazz : classList){
            ClassUtil.loadClass(clazz.getName(), true);
        }
    }
}
