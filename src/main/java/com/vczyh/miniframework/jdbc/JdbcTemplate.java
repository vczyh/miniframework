package com.vczyh.miniframework.jdbc;


import com.vczyh.miniframework.annotation.Colum;
import com.vczyh.miniframework.annotation.Id;
import com.vczyh.miniframework.processor.TableProcessor;
import com.vczyh.miniframework.processor.Test;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class JdbcTemplate {

    private DataSource dataSource;

    /**
     * select * from table where ... and ...
     */
    public <T> List<T> selectAll(T entity) {
        String tableName = TableProcessor.getTableName(entity.getClass());
        Field[] fields = entity.getClass().getDeclaredFields();

        String sql = "select * from " + tableName + " where ";
        Set<Field> fieldSet = new LinkedHashSet<Field>();

        try {
            //获取非空的field
            for (Field field : fields) {
                field.setAccessible(true);
                Object columValue = field.get(entity);
                if (columValue != null) {
                    fieldSet.add(field);
                }
                field.setAccessible(false);
            }
            Field[] fieldsArray = new Field[fieldSet.size()];
            fieldsArray = fieldSet.toArray(fieldsArray);

            for (int i = 0; i < fieldsArray.length; i++) {
                Field field = fieldsArray[i];
                field.setAccessible(true);
                Object fieldValue = field.get(entity);
                field.setAccessible(false);
                if (fieldValue == null) continue;
                Class fieldType = field.getType();
                Colum colum = field.getAnnotation(Colum.class);
                String columName = colum.value();
                String columValue = fieldValue.toString();
                if (fieldType != Integer.class) columValue = "'" + columValue + "'";
                sql += columName + " = " + columValue + " and ";
            }
            if (sql.endsWith(" where ")) {
                sql = sql.substring(0, sql.lastIndexOf(" where ")) + ";";
            } else {
                sql = sql.substring(0, sql.lastIndexOf(" and ")) + ";";
            }
            System.out.println(sql);
            Connection connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery();
            List<T> list = new ArrayList<>();
            while (resultSet.next()) {
                T obj = (T) entity.getClass().newInstance();
                for (Field field : obj.getClass().getDeclaredFields()) {
                    String fieldName = field.getName();
                    Class fieldType = field.getType();
                    String setterMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Method method = obj.getClass().getDeclaredMethod(setterMethodName, fieldType);
                    Colum colum = field.getAnnotation(Colum.class);
                    String columName = colum.value();
                    if (fieldType == Integer.class) {
                        method.invoke(obj, resultSet.getInt(columName));
                    } else {
                        method.invoke(obj, resultSet.getString(columName));
                    }
                }
                list.add(obj);
            }
            connection.close();
            return list;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("error");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T selectOne(T entity) {
        List<T> result = selectAll(entity);
        if (result.size() == 0) {
            return null;
        } else {
            return result.get(0);
        }
    }

    public <T> int insertOne(T entity) {
        String tableName = TableProcessor.getTableName(entity.getClass());
        Field[] fields = entity.getClass().getDeclaredFields();
        String sql = "insert into " + tableName + " (";
        String sql2 = "values (";
        try {
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                Colum colum = field.getAnnotation(Colum.class);
                Class fieldType = field.getType();
                field.setAccessible(true);
                Object fieldValue = field.get(entity);
                if (fieldValue == null) {
                    continue;
                }
                field.setAccessible(false);
                String columName = colum.value();
                String columValue = fieldValue.toString();
                if (fieldType != Integer.class) {
                    columValue = "'" + columValue + "'";
                }
                field.setAccessible(false);
                sql += columName + ", ";
                sql2 += columValue + ", ";
            }
            if (sql.endsWith("(")) {
                System.out.println("插入操作不能没有参数");
            }
            sql = sql.substring(0, sql.lastIndexOf(", "));
            sql2 = sql2.substring(0, sql2.lastIndexOf(", "));
            sql += ") " + sql2 + ");";
            Connection connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            System.out.println(pstmt.executeUpdate());
            connection.close();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(sql);
        return 0;
    }

    public <T> int update(T entity) {
        String tableName = TableProcessor.getTableName(entity.getClass());
        Field[] fields = entity.getClass().getDeclaredFields();
        String sql = "update " + tableName + " set ";
        String sql2 = " where "; //主鍵
        try {
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                Class fieldtype = field.getType();
                field.setAccessible(true);
                Object fieldValue = field.get(entity);
                field.setAccessible(false);
                if (fieldValue == null) continue;
                Colum colum = field.getAnnotation(Colum.class);
                String columName = colum.value();
                String columValue = fieldValue.toString();
                if (fieldtype != Integer.class) columValue = "'" + columValue + "'";
                sql += columName + " = " + columValue + ", ";
                Id id = field.getAnnotation(Id.class);
                if (id != null) {
                    sql2 += columName + " = " + columValue;
                }
            }
            if (sql.endsWith(" set ")) {
                System.out.println("更新操作需要参数");
            }
            sql = sql.substring(0, sql.lastIndexOf(", "));
            sql += sql2 + ";";
            System.out.println(sql);
            Connection connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            System.out.println(pstmt.executeUpdate());
            connection.close();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public <T> int delete(T entity) {
        String tableName = TableProcessor.getTableName(entity.getClass());
        Field[] fields = entity.getClass().getDeclaredFields();
        String sql = "delete from " + tableName + " where ";
        try {
            for (Field field : fields) {
                Class fieldType = field.getType();
                field.setAccessible(true);
                Object fieldValue = field.get(entity);
                field.setAccessible(false);
                if (fieldValue == null) continue;
                Colum colum = field.getAnnotation(Colum.class);
                String columName = colum.value();
                String columValue = fieldValue.toString();
                if (fieldType != Integer.class) columValue = "'" + columValue + "'";
                sql += columName + " = " + columValue + " and ";
            }
            if (sql.endsWith(" where ")) {
                sql = sql.substring(0, sql.lastIndexOf(" where "));
            } else {
                sql = sql.substring(0, sql.lastIndexOf(" and "));
            }
            sql += ";";
            System.out.println(sql);
            Connection connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            System.out.println(pstmt.executeUpdate());
            connection.close();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static void main(String[] args) {

        DBConnectionPool dbConnectionPool = new DBConnectionPool();
        dbConnectionPool.setDriver("com.mysql.jdbc.Driver");
        dbConnectionPool.setInitSize(5);
        dbConnectionPool.setUrl("jdbc:mysql://localhost:3306/work");
        dbConnectionPool.setUserName("root");
        dbConnectionPool.setPassword("vczyh123");

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dbConnectionPool);
        Test test = new Test();
        test.setId(111);
        test.setAge(123);
        test.setName("zhang");
        test.setSex("女");
    }
}
