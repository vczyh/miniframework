package com.vczyh.miniframework.ioc;

import com.vczyh.miniframework.core.ClassHelper;
import com.vczyh.miniframework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanHelper {

    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>();

    static {
        System.out.println("加载： " + BeanHelper.class.getName());
        Set<Class<?>> classSet = ClassHelper.getBeanClassSet();
        for (Class<?> beanClass : classSet) {
            Object obj = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass, obj);
        }
    }

    /**
     * 获取 BeanMap
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 获取bean实例
     */
    public static <T> T getBean(Class<T> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("找不到bean通过：" + cls);
        }
        return (T) BEAN_MAP.get(cls);
    }

}
