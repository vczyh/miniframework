package com.vczyh.miniframework.processor;

import com.vczyh.miniframework.annotation.Bean;
import com.vczyh.miniframework.annotation.Configuration;
import com.vczyh.miniframework.util.ClassUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 解析 @configuration 和 @Bean
 * @author vczyh
 * @date 2018/12/18
 */
public class ConfigurationProcessor {
    private final static Set<Class<?>> CLASS_SET = ClassUtil.getClassSet();
    private final static Map<Class<?>, Object> COMP_BEAN = ComponentProcessor.getCompBean();
    private final static Set<Class<?>> CONFIGURATION_CLASS_SET = new HashSet<>();

    static {
        for (Class<?> clzz : CLASS_SET) {
            if (clzz.isAnnotationPresent(Configuration.class)) {
                CONFIGURATION_CLASS_SET.add(clzz);
            }
        }
        init();
    }

    public static void init() {
        for (Class<?> clzz : CONFIGURATION_CLASS_SET) {
            if (clzz.isAnnotationPresent(Configuration.class)) {
                Object classInstance = COMP_BEAN.get(clzz);
                Method[] methods = clzz.getMethods();
                for (Method method : methods) {
                    try {
                        if (method.isAnnotationPresent(Bean.class)) {
                            Object result = method.invoke(classInstance);
                            Class resultType = result.getClass();
//                            System.out.println("config " + resultType);
                            COMP_BEAN.put(resultType, result);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
