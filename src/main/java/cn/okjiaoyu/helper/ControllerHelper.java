package cn.okjiaoyu.helper;

import cn.okjiaoyu.annotation.Action;
import cn.okjiaoyu.entity.Handle;
import cn.okjiaoyu.entity.Request;
import cn.okjiaoyu.util.CollectionUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: liuzhanhui
 * @Decription:
 * @Date: Created in 2018-07-24:16:47
 * Modify date: 2018-07-24:16:47
 */
public final class ControllerHelper {

    /**
     * 定义请求与处理器的映射关系
     */
    private static final Map<Request, Handle> ACTION_MAP = new HashMap<Request, Handle>();
    static {
        Set<Class<?>> controllerSet = ClassHelper.getControllerClassSet();
        if (CollectionUtil.isNoEmpty(controllerSet)){
            for (Class<?> clazz : controllerSet){
                Method[] methods = clazz.getDeclaredMethods();
                if (ArrayUtils.isNotEmpty(methods)){
                    for (Method method : methods){
                        if (method.isAnnotationPresent(Action.class)){
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            if (mapping.matches("\\w+:/\\w*")){
                                String array[] = mapping.split(":");
                                if (ArrayUtils.isNotEmpty(array) && array.length == 2){
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod, requestPath);
                                    Handle handle = new Handle(clazz, method);
                                    ACTION_MAP.put(request, handle);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取handle
     * @param requestMethod
     * @param requestPath
     * @return
     */
    public static Handle getHandle(String requestMethod, String requestPath){
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}
