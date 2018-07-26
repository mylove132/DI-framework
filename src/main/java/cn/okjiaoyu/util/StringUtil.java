package cn.okjiaoyu.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author: liuzhanhui
 * @Decription:
 * @Date: Created in 2018-07-20:13:40
 * Modify date: 2018-07-20:13:40
 */
public final class StringUtil {

    /**
     * 判断字符串为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if (str != null){
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    /**
     * 判断字符串不为空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str){
        if (str != null){
            str = str.trim();
        }
        return StringUtils.isNotEmpty(str);
    }
}
