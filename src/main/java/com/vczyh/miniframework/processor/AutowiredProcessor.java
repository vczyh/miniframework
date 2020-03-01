package com.vczyh.miniframework.processor;

import com.vczyh.miniframework.annotation.Autowired;
import com.vczyh.miniframework.util.ClassUtil;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AutowiredProcessor {
    private final static Map<Class<?>, Object> COMP_BEAN = ComponentProcessor.getCompBean();
    private final static Set<Class<?>> CLASS_SET = ClassUtil.getClassSet();
    private final static Set<Class<?>> AUTOWIRED_CLASS_SET = new HashSet<>();


    static {
//        System.out.println(COMP_BEAN.size());
        for (Class<?> aClass : CLASS_SET) {
            if (aClass == Autowired.class) {
                AUTOWIRED_CLASS_SET.add(aClass);
            }
        }
        init();
    }

    public static void init() {
        for (Map.Entry<Class<?>, Object> beanEntry : COMP_BEAN.entrySet()) {
            Class beanClass = beanEntry.getKey();
            Object beanInstance = beanEntry.getValue();
            Field[] fields = beanClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Class fieldType = field.getType();
                    Object obj = COMP_BEAN.get(fieldType);
                    if (obj == null) {
                        System.out.println("没有找到：" + fieldType);
                        continue;
                    }

//                    System.out.println("aurh：" + fieldType);
                    try {
                        field.setAccessible(true);
                        field.set(beanInstance, obj);
                        field.setAccessible(false);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        System.out.println("设置field错误");
                    }
                }
            }
        }
    }

}
