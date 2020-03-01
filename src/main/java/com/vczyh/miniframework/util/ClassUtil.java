package com.vczyh.miniframework.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class ClassUtil {

    private final static Set<Class<?>> CLASS_SET = new HashSet<>();
    private final static String[] basePack;

    static {
        basePack = null;
        getBasePack();
    }

    private static void getBasePack() {
        try {
            //默认从work扫描
            Properties pros = PropsUtil.getProps("application");
            String base = pros.getProperty("basepath");
            System.out.println(base);
            Enumeration<URL> urls = ClassUtil.class.getClassLoader().getResources(base);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                System.out.println("url" + url);
                if (url != null) {
                    String protocol = url.getProtocol();
                    if (protocol.equals("file")) {
                        String packagePath = url.getPath();
                        addClassSet(CLASS_SET, packagePath, base.replace("/", "."));

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addClassSet(Set<Class<?>> classSet, String packageParh, String packageName) {
        File[] files = new File(packageParh).listFiles();
        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                className = packageName + "." + className;
                doAddClassSet(classSet, className);
            } else {
                String subPackagePath = packageParh + "/" + fileName;
                String subPackageName = packageName + "." + fileName;
                addClassSet(classSet, subPackagePath, subPackageName);
            }
        }
    }

    private static void doAddClassSet(Set<Class<?>> classSet, String className) {
        try {
//            System.out.println(className);
            ClassLoader classLoader = ClassUtil.class.getClassLoader();
            Class clzz = Class.forName(className, true, classLoader);
//            System.out.println(clzz); // todo
            CLASS_SET.add(clzz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    public static Set<Class<?>> isAnnotationClass(Class annotation) {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class clzz : CLASS_SET) {
            if (clzz.isAnnotationPresent(annotation)) {
                classSet.add(clzz);
            }
        }
        return classSet;
    }

    public static void main(String[] args) {
        getBasePack();
    }
}
