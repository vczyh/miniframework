package com.vczyh.miniframework.processor;


import com.vczyh.miniframework.annotation.Component;
import com.vczyh.miniframework.annotation.Configuration;
import com.vczyh.miniframework.annotation.Controller;
import com.vczyh.miniframework.util.ClassUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *扫描 @Component @Configuration. @Controller 注册为bean
 * @author vczyh
 * @date 2018/12/18
 */
public class ComponentProcessor {
    private final static Set<Class<?>> CLASS_SET = ClassUtil.getClassSet();
    private final static Map<Class<?>, Object> COMP_BEAN;

    static {
        COMP_BEAN = new HashMap<>();
        System.out.println(CLASS_SET.size());
        try {
            for (Class<?> clzz : CLASS_SET) {
                if (clzz.isAnnotationPresent(Component.class) ||
                        clzz.isAnnotationPresent(Configuration.class) ||
                        clzz.isAnnotationPresent(Controller.class)) {
                    System.out.println(clzz);
                    Object obj = clzz.newInstance();
                    COMP_BEAN.put(clzz, obj);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public static Map<Class<?>, Object> getCompBean() {
        return COMP_BEAN;
    }

    public static Object getBean(Class clzz) {
        return COMP_BEAN.get(clzz);
    }
}
