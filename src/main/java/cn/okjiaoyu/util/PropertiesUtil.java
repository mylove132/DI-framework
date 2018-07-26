package cn.okjiaoyu.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author: liuzhanhui
 * @Decription:
 * @Date: Created in 2018-07-20:11:48
 * Modify date: 2018-07-20:11:48
 */
public class PropertiesUtil {

    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    public static Properties loadProps(String fileName) {
        Properties properties = null;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new FileNotFoundException(fileName + "未找到");
            }
            properties = new Properties();
            properties.load(is);
        } catch (IOException e) {
            logger.error(fileName + "文件未找到");
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("关闭流异常");
                }
            }
        }
        return properties;
    }

    /**
     * 获取String value值
     * @param properties
     * @param key
     * @return
     */
    public static String getString(Properties properties, String key){
        return getString(properties, key, "");
    }
    /**
     * 可以指定默认值
     * @param properties
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(Properties properties, String key, String defaultValue){
        String value = defaultValue;
        if (properties.containsKey(key)){
            value = properties.getProperty(key);
        }
        return value;
    }

    /**
     * 获取int value值
     * @param properties
     * @param key
     * @return
     */
    public static int getInt(Properties properties, String key){
        return getInt(properties, key, 0);
    }
    /**
     * 可以指定默认值
     * @param properties
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getInt(Properties properties, String key, int defaultValue){
        int value = defaultValue;
        if (properties.containsKey(key)){
            value = CastUtil.castInt(properties.getProperty(key));
        }
        return value;
    }

    /**
     * 获取long 默认值0L
     * @param properties
     * @param key
     * @return
     */
    public static long getLong(Properties properties, String key){
        return getLong(properties, key, 0L);
    }
    /**
     *
     * @param properties
     * @param key
     * @param defaultValue
     * @return
     */
    public static long getLong(Properties properties, String key, long defaultValue){
        long value = defaultValue;
        if (properties.containsKey(key)){
            value = CastUtil.castLong(properties.getProperty(key));
        }
        return value;
    }

    /**
     * 获取boolean类型值，默认为false
     * @param properties
     * @param key
     * @return
     */
    public static boolean getBoolean(Properties properties, String key) throws Exception {
        return getBoolean(properties, key, false);
    }
    public static boolean getBoolean(Properties properties, String key, boolean defaultValue) throws Exception {
        boolean value = defaultValue;
        if (properties.containsKey(key)){
            value = CastUtil.castBoolean(properties.getProperty(key));
        }
        return value;
    }

    public static void main(String[] args) throws Exception {
        boolean name = PropertiesUtil.getBoolean(PropertiesUtil.loadProps("mysql.properties"), "username");
        System.out.println(name);
    }
}
