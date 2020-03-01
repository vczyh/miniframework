package com.vczyh.miniframework.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtil {

    public static Object invokeMethod(Object object, Method method, Object... args) {
        Object result = null;
        try {
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            result = method.invoke(object, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
        }
        return result;
    }

    public static Method[] getMethods(Class clzz) {
        return clzz.getDeclaredMethods();
    }

    public static Field[] getFields(Class clzz) {
        return clzz.getDeclaredFields();
    }

}
