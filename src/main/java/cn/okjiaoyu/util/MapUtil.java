package cn.okjiaoyu.util;

import org.apache.commons.collections4.MapUtils;

import java.util.Map;

/**
 * @Author: liuzhanhui
 * @Decription:
 * @Date: Created in 2018-07-23:15:11
 * Modify date: 2018-07-23:15:11
 */
public class MapUtil {

    public static boolean isEmpty(Map<?, ?> fieldMap){
        return MapUtils.isEmpty(fieldMap);
    }

    public static boolean isNotEmpty(Map<?, ?> fieldMap){
        return MapUtils.isNotEmpty(fieldMap);
    }
}
