package com.vczyh.miniframework.processor;

import java.lang.reflect.Field;

public class ColumProcessor {

    public static void getColumName(Class<?> clzz) {
        Field[] fields = clzz.getDeclaredFields();
        for (Field field : fields) {

//            field.setAccessible(true);
            System.out.println(field.getType() == int.class);
            System.out.println(field.getName());
        }
    }

    public static void main(String[] args) {
//        ColumProcessor columProcessor = new ColumProcessor();
        ColumProcessor.getColumName(Test.class);
    }
}
