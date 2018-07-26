package cn.okjiaoyu.helper;
import cn.okjiaoyu.util.MapUtil;
import cn.okjiaoyu.util.PropertiesUtil;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * @Author: liuzhanhui
 * @Decription:
 * @Date: Created in 2018-07-23:14:14
 * Modify date: 2018-07-23:14:14
 */
public final class DatabaseHelper {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);
    //数据库与实体结合的操作api(dbutils)
    private static final QueryRunner queryRunner = new QueryRunner();
    //数据库连接池
    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL;
    //创建数据库连接
    private static final BasicDataSource DATA_SOURCE;
    private static final String DRIVER;
    private static final String URL;
    private static final String NAME;
    private static final String PASSWORD;
    private static final int MAXIDLE = 8;
    private static final int MAX_WAIT_MILLIS = -1;
    private static final int INITIAL_SIZE = 5;
    private static final int MAX_TOTAL = 10;

    static {
        //初始化线程池
        CONNECTION_THREAD_LOCAL = new ThreadLocal<>();
        //初始化线程创建者
        DATA_SOURCE = new BasicDataSource();
        //读取数据库文件
        Properties config = PropertiesUtil.loadProps("mysql.properties");
        DRIVER = config.getProperty("jdbc.driver");
        URL = config.getProperty("jdbc.url");
        NAME = config.getProperty("jdbc.username");
        PASSWORD = config.getProperty("jdbc.password");
        //初始化数据库连接数据
        DATA_SOURCE.setDriverClassName(DRIVER);
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(NAME);
        DATA_SOURCE.setPassword(PASSWORD);
        DATA_SOURCE.setMaxIdle(MAXIDLE);
        DATA_SOURCE.setMaxWaitMillis(MAX_WAIT_MILLIS);
        DATA_SOURCE.setInitialSize(INITIAL_SIZE);
        DATA_SOURCE.setMaxTotal(MAX_TOTAL);
    }

    /**
     * 查询实体数组
     * @param entityClass
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params){
        List<T> entityList;
        Connection connection = getConnection();
        try {
            entityList = queryRunner.query(connection, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
            logger.error("qurey entityList failure", e);
            throw new RuntimeException();
        }finally {
            closeConnection(connection);
        }
        return entityList;
    }

    /**
     * 查询单个实体
     * @param entityClass
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params){
        T entity;
        Connection connection = getConnection();
        try {
            entity = queryRunner.query(connection, sql, new BeanHandler<T>(entityClass), params);
        } catch (SQLException e) {
            logger.error("qurey entity failure", e);
            throw new RuntimeException();
        }finally {
            closeConnection(connection);
        }
        return entity;
    }

    /**
     * 执行更新语句（插入，更新，删除）
     * @param sql
     * @param params
     * @return
     */
    public static int executeUpdate(String sql, Object... params){
        Connection connection = getConnection();
        int result = 100;
        try {
            result = queryRunner.update(connection, sql, params);
        } catch (SQLException e) {
            logger.error("execute update failure", e);
            throw new RuntimeException();
        }finally {
            closeConnection(connection);
        }
        return result;
    }

    /**
     * 查询无序数组
     * @param sql
     * @param params
     * @return
     */
    public static List<Map<String, Object>> queryMapList(String sql, Object... params){
        Connection connection = getConnection();
        List<Map<String, Object>> result = null;
        try {
            result = queryRunner.query(sql, new MapListHandler(), params);
        } catch (SQLException e) {
            logger.error("qurey mapList failure", e);
            throw new RuntimeException();
        }finally {
            closeConnection(connection);
        }
        return result;
    }

    /**
     * 执行sql文件
     * @param filePath
     */
    public static void executeSqlFile(String filePath){
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        String line;
        try {
            while ((line = bufferedReader.readLine())!= null){
                executeUpdate(line);
            }
        } catch (IOException e) {
            logger.error("execute sql file failure", e);
            throw new RuntimeException();
        }
    }

    /**
     * 添加实体
     * @param entityClass
     * @param fieldMap
     * @param <T>
     * @return
     */
    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap){

        if (MapUtil.isEmpty(fieldMap)){
            logger.error("insert entity failure: params is null");
            throw new RuntimeException();
        }
        String sql = "INSERT INTO "+getTableName(entityClass);
        StringBuffer columns = new StringBuffer("(");
        StringBuffer values = new StringBuffer("(");
        for (String columnName:fieldMap.keySet()){
            columns.append(columnName);
            columns.append(", ");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
        values.replace(values.lastIndexOf(", "), values.length(), ")");
        sql += columns + "VALUES" + values;

        Object[] params = fieldMap.values().toArray();
        return executeUpdate(sql, params) == 1;
    }

    /**
     * 更新实体
     * @param entityClass
     * @param id
     * @param fieldMap
     * @param <T>
     * @return
     */
    public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap){

        //mysql> update customer set name='customer1_1',contact='刘备',telephone='15910401321',email='liubei@okay.cn',remark='蜀国皇帝' where id = 1;
        if (MapUtil.isEmpty(fieldMap)){
            logger.error("update entity failure: params is null");
            throw new RuntimeException();
        }
        String sql = "UPDATE "+getTableName(entityClass)+" SET ";
        StringBuffer columns = new StringBuffer();

        for (String keyName:fieldMap.keySet()){
            columns.append(keyName).append("=?, ");
        }
        sql += columns.substring(0, columns.lastIndexOf(", "))+" WHERE id=?";
        Collection params = fieldMap.values();
        params.add(id);
        return executeUpdate(sql,  params.toArray()) == 1;
    }

    /**
     * 删除实体
     * @param entityClass
     * @param id
     * @param <T>
     * @return
     */
    public static <T> boolean deleteEntity(Class<T> entityClass, long id){

        String sql = "DELETE FROM "+getTableName(entityClass)+" WHERE id=?";
        return executeUpdate(sql, id) == 1;
    }

    /**
     * 获取connection
     * @return
     */
    public static Connection getConnection(){
        Connection connection = CONNECTION_THREAD_LOCAL.get();
        if (connection == null){
            try {
                connection = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                logger.error("get connection failure", e);
                throw new RuntimeException();
            }
            finally {
                CONNECTION_THREAD_LOCAL.set(connection);
            }
        }
        return connection;
    }

    /**
     * 关闭数据库连接
     * @param connection
     */
    public static void closeConnection(Connection connection){
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("close connection failure", e);
            }
        }
    }

    public static String getTableName(Class<?> entity){
        StringBuffer stringBuffer = new StringBuffer();
        String className = entity.getSimpleName();
        String firstLetter = String.valueOf(className.charAt(0));
        String residueLetter = className.substring(1, className.length());
        char[] cns = residueLetter.toCharArray();
        stringBuffer.append(firstLetter);
        for (char cn:cns){
            if (cn < 'Z' && cn > 'A'){
                stringBuffer.append("_");
            }
            stringBuffer.append(cn);
        }
        return stringBuffer.toString().toLowerCase();
    }
}
