package com.vczyh.miniframework.bean;

import java.util.HashMap;
import java.util.Map;

public class View {

    private String path;
    private Map<String, Object> modle;

    public View(String path, Map<String, Object> modle) {
        this.path = path;
        this.modle = modle;
    }

    public View(String path) {
        this.path = path;
        this.modle = new HashMap<String, Object>();
    }

    public void addModle(String key, Object value) {
        modle.put(key, value);
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModle() {
        return modle;
    }
}
