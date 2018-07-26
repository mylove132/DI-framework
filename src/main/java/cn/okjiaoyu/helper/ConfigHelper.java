package cn.okjiaoyu.helper;


import cn.okjiaoyu.ConfigConstant;
import cn.okjiaoyu.util.PropertiesUtil;

import java.util.Properties;

/**
 * @Author: liuzhanhui
 * @Decription:
 * @Date: Created in 2018-07-24:13:23
 * Modify date: 2018-07-24:13:23
 */
public class ConfigHelper {

    private static final Properties CONFIG = PropertiesUtil.loadProps(ConfigConstant.CONFIG_NAME);


    /**
     * 获取JDBC驱动
     * @return
     */
    public static String getJdbcDriver(){
        return PropertiesUtil.getString(CONFIG, ConfigConstant.JDBC_DRIVER);
    }

    /**
     * 获取JDBC URL
     * @return
     */
    public static String getJdbcUrl(){
        return PropertiesUtil.getString(CONFIG, ConfigConstant.JDBC_URL);
    }

    /**
     * 获取JDBC用户名
     * @return
     */
    public static String getJdbcUsername(){
        return PropertiesUtil.getString(CONFIG, ConfigConstant.JDBC_USERNAME);
    }

    /**
     * 获取JDBC密码
     * @return
     */
    public static String getJdbcPassword(){
        return PropertiesUtil.getString(CONFIG, ConfigConstant.JDBC_PASSWORD);
    }

    /**
     * 获取应用基础包名
     * @return
     */
    public static String getAppBasePackage(){
        return PropertiesUtil.getString(CONFIG, ConfigConstant.APP_BASE_PACKAGE);
    }

    /**
     * 获取jsp文件位置
     * @return
     */
    public static String getAppJspPath(){
        return PropertiesUtil.getString(CONFIG, ConfigConstant.APP_JSP_PATH, "/WEB-INF/view/");
    }
    /**
     * 获取静态文件路径
     * @return
     */
    public static String getAppAssetPath(){
        return PropertiesUtil.getString(CONFIG, ConfigConstant.APP_ASSET_PATH, "/asset/");
    }
}
