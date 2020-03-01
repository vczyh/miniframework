package com.vczyh.miniframework.bean;

import java.util.HashMap;
import java.util.Map;

public class View {

    private String path;

    private Map<String, Object> model;

    public View() {
        this.path = "";
        this.model = new HashMap<>();
    }

    public View(String path) {
        this.path = path;
        this.model = new HashMap<>();
    }

    public View(String path, Map<String, Object> model) {
        this.path = path;
        this.model = model;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }

    public void addAttribute(String key, Object object) {
        this.model.put(key, object);
    }
}
