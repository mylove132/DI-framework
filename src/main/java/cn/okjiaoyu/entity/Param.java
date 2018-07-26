package cn.okjiaoyu.entity;

import cn.okjiaoyu.util.CastUtil;

import java.util.Map;

/**
 * @Author: liuzhanhui
 * @Decription:
 * @Date: Created in 2018-07-24:17:13
 * Modify date: 2018-07-24:17:13
 */
public class Param {

    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap){
        this.paramMap = paramMap;
    }

    /**
     * 根据名称获取long型参数
     * @param name
     * @return
     */
    public long getLongValue(String name){
        return CastUtil.castLong(paramMap.get(name));
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }
}
