package com.vczyh.miniframework;


import com.vczyh.miniframework.processor.AutowiredProcessor;
import com.vczyh.miniframework.processor.ComponentProcessor;
import com.vczyh.miniframework.processor.ConfigurationProcessor;
import com.vczyh.miniframework.processor.ControllerProcessor;
import com.vczyh.miniframework.util.ClassUtil;

public class InitUtil {

    public static void init() {
        System.out.println("入口");
        Class[] classes = new Class[]{
                ComponentProcessor.class,
                ConfigurationProcessor.class,
                ControllerProcessor.class,
                AutowiredProcessor.class,

        };
        for (Class clzz : classes) {
            ClassLoader classLoader = InitUtil.class.getClassLoader();
            try {
                Class.forName(clzz.getName(), true, classLoader);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
