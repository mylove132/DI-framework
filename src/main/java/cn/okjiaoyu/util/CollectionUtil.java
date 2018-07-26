package cn.okjiaoyu.util;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @Author: liuzhanhui
 * @Decription:
 * @Date: Created in 2018-07-22:18:23
 * Modify date: 2018-07-22:18:23
 */
public class CollectionUtil {

    /**
     * 判断数组不为空
     * @param collection
     * @return
     */
    public static boolean isNoEmpty(Collection<?> collection){
        return CollectionUtils.isNotEmpty(collection);
    }

    /**
     * 判断数据为空
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection){
        return CollectionUtils.isEmpty(collection);
    }
}
