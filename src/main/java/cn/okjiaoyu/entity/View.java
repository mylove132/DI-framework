package cn.okjiaoyu.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: liuzhanhui
 * @Decription: controller返回view
 * @Date: Created in 2018-07-24:17:17
 * Modify date: 2018-07-24:17:17
 */
public class View {

    //视图路径
    private String path;

    private Map<String, Object> model;

    public View(String path){
        this.path = path;
        this.model = new HashMap<String, Object>();
    }

    public View addView(String key, Object value){
        model.put(key, value);
        return this;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
