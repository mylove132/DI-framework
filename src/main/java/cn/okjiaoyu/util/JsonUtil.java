package cn.okjiaoyu.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Author: liuzhanhui
 * @Decription: json工具类
 * @Date: Created in 2018-07-24:18:24
 * Modify date: 2018-07-24:18:24
 */
public final class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 将entity转为json
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String toJson(T obj){
        String json = "";
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("covent entity to JSON failure", e);
            throw new RuntimeException(e);
        }
        return json;
    }

    public static <T> T fromJson(String json, Class<T> entity){
        T pojo = null;
        try {
            pojo = OBJECT_MAPPER.readValue(json, entity);
        } catch (IOException e) {
            logger.error("covent JSON to entity failure", e);
            throw new RuntimeException(e);
        }
        return pojo;
    }
}
