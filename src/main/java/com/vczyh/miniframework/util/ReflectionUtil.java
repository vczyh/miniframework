package com.vczyh.miniframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class ReflectionUtil {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 创建实例
     */
    public static Object newInstance(Class<?> cls) {
        Object instance;
        try {
            instance = cls.newInstance();
        } catch (Exception e) {
            logger.error("创建实例失败", e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * 设置成员变量的值
     */
    public static void setField(Object obj, Field field, Object value) {
        field.setAccessible(true);
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            logger.error("设置变量失败", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 调用方法
     */
    public static Object invokeMethod(Object obj, Method method, Object... args) {
        Object result;
        method.setAccessible(true);
        try {
            result = method.invoke(obj, args);
        } catch (Exception e) {
            logger.error("调用方法失败",e );
            throw new RuntimeException(e);
        }
        return result;
    }
}
