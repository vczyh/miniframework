package com.vczyh.miniframework.ioc;

import com.vczyh.miniframework.annotation.Inject;
import com.vczyh.miniframework.util.ArrayUtil;
import com.vczyh.miniframework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

public class IocHelper {

    static {
        System.out.println("加载： " + IocHelper.class.getName());
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
            Class<?> beanClass = beanEntry.getKey();
            Object beanInstance = beanEntry.getValue();
            Field[] beanFields = beanClass.getDeclaredFields();
            if (ArrayUtil.isNotEmpty(beanFields)) {
                for (Field beanField : beanFields) {
                    if (beanField.isAnnotationPresent(Inject.class)) {
                        Class<?> beanFieldClass = beanField.getType();
                        Object beanFieldInstance = beanMap.get(beanFieldClass);
                        ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                    }
                }
            }
        }
    }
}
