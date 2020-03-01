package com.vczyh.miniframework.processor;


import com.vczyh.miniframework.annotation.Table;

public class TableProcessor {

    public static String getTableName(Class<?> clzz) {
        Table table = clzz.getAnnotation(Table.class);
        if (table == null || table.value().equals("")) {
            // error
            System.out.println("error");
        }
        return table.value();
    }


}
