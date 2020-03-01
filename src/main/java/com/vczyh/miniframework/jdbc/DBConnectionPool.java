package com.vczyh.miniframework.jdbc;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;

public class DBConnectionPool implements DataSource {
    private String url;
    private String userName;
    private String password;
    private String driver;
    private int initSize;
    private int maxSize;

    private  LinkedList<Connection> connectionList = null;

    @Override
    public Connection getConnection() throws SQLException {
        try {
            if (connectionList == null) {
                connectionList = new LinkedList<>();
                Class.forName(driver);
                for (int i = 0; i < initSize; i++) {
                    Connection connection = DriverManager.getConnection(url, userName, password);
                    connectionList.add(connection);
                }
                System.out.println("已经初始化数据库连接池：" + connectionList.size());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (connectionList.size() > 0) {
            final Connection conn = connectionList.removeFirst();
            return (Connection) Proxy.newProxyInstance(DBConnectionPool.class.getClassLoader(), new Class[] {Connection.class}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if (!"close".equals(method.getName())) {
                        System.out.println("执行");
                        return method.invoke(conn, args);
                    } else {
                        connectionList.add(conn);
                        System.out.println("已经回收连接");
                        return null;
                    }
                }
            });
        } else {
            System.out.println("连接池繁忙，请等待");
            return null;
        }
    }


    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setInitSize(int initSize) {
        this.initSize = initSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
}
