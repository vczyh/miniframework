package com.vczyh.miniframework.bean;

import java.lang.reflect.Method;

public class Hander {

    private Class<?> controllerClass;

    private Method method;

    public Hander(Class<?> controllerClass, Method method) {
        this.controllerClass = controllerClass;
        this.method = method;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "Hander{" +
                "controllerClass=" + controllerClass +
                ", method=" + method +
                '}';
    }
}
