package cn.okjiaoyu.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: liuzhanhui
 * @Decription:
 * @Date: Created in 2018-07-20:13:35
 * Modify date: 2018-07-20:13:35
 */
public class CastUtil {


    private static final Logger logger = LoggerFactory.getLogger(CastUtil.class);
    /**
     * 转型为String,默认为""
     * @param obj
     * @return
     */
    public static String castString(Object obj){
       return castString(obj, "");
    }

    /**
     * 转型为String
     * @param obj
     * @return
     */
    public static String castString(Object obj, String defaultValue){
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    /**
     * 转型为int, 默认为0
     * @param obj
     * @return
     */
    public static int castInt(Object obj){
        return castInt(obj, 0);
    }

    /**
     * 转型为int
     * @param obj
     * @param defaultValue
     * @return
     */
    public static int castInt(Object obj, int defaultValue){
        int value = defaultValue;
        if (obj != null){
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)){
                try {
                    value = Integer.parseInt(strValue);
                }catch (NumberFormatException e){
                    throw new NumberFormatException("'"+strValue+"':转换int类型失败");
                }
            }
        }
        return value;
    }

    /**
     * 转为boolean, 默认为true
     * @param obj
     * @return
     */
    public static boolean castBoolean(Object obj) throws Exception {
        return castBoolean(obj, true);
    }

    /**
     * 转为boolean
     * @param obj
     * @param defaultValue
     * @return
     */
    public static boolean castBoolean(Object obj, boolean defaultValue) throws Exception {
        boolean value = defaultValue;
        if (obj != null){
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)){
               switch (strValue.toUpperCase()){
                   case "TRUE":
                       value = true;
                       break;
                   case "FALSE":
                       value = false;
                       break;
                    default:
                        throw new Exception("'"+strValue+"':不能转换为boolean类型");
               }
            }
        }
        return value;
    }

    /**
     * 转为long值，默认为0L
     * @param obj
     * @return
     */
    public static long castLong(Object obj){
        return castLong(obj, 0L);
    }
    /**
     * 转为long值
     * @param obj
     * @param defaultValue
     * @return
     */
    public static long castLong(Object obj, long defaultValue){
        long value = defaultValue;
        if (obj != null){
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)){
                try {
                    value = Long.valueOf(strValue);
                }catch (NumberFormatException e){
                    throw new NumberFormatException("'"+strValue+"':转换long类型失败");
                }
            }
        }
        return value;
    }

    /**
     * 转为double类型，默认为0.0
     * @param obj
     * @return
     */
    public static double castDouble(Object obj){
        return castDouble(obj, 0.0);
    }

    /**
     * 转为double类型
     * @param obj
     * @param defaultValue
     * @return
     */
    public static double castDouble(Object obj, double defaultValue){
        double value = defaultValue;
        if (obj != null){
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)){
                try {
                    value = Double.valueOf(value);
                }catch (NumberFormatException e){
                    throw new NumberFormatException("'"+strValue+"':转换double类型失败");
                }
            }
        }
        return value;
    }

    public static void main(String[] args) {
        String a = "avcd";
        System.out.println(Boolean.valueOf(a));
    }
}
