package com.vczyh.miniframework.core;

import com.vczyh.miniframework.ConfigConstant;
import com.vczyh.miniframework.annotation.Controller;
import com.vczyh.miniframework.annotation.Service;
import com.vczyh.miniframework.util.ClassUtil;

import java.util.HashSet;
import java.util.Set;

public class ClassHelper {

    private static final Set<Class<?>> CLASS_SET;

    static {
        System.out.println("加载： " + ClassHelper.class.getName());
        String basePackage = ConfigHelper.getString(ConfigConstant.APP_BASE_PACKAGE);
        CLASS_SET = ClassUtil.getClassSet(basePackage );
    }

    /**
     * 获取包名下所有类
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取包名下所有Service类
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Service.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取包名下所有Controller类
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Controller.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取包下所有Bean类（包括Service Controller）
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
//        printClassSet(beanClassSet);
        return beanClassSet;
    }

    public static void printClassSet(Set<Class<?>> classes) {
        for (Class<?> cls : classes) {
            System.out.println(cls.getName());
        }
    }
}
