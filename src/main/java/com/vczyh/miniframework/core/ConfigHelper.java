package com.vczyh.miniframework.core;

import com.vczyh.miniframework.ConfigConstant;
import com.vczyh.miniframework.util.PropsUtil;

import java.util.Map;
import java.util.Properties;

public class ConfigHelper {
    private static final Properties configProps = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    public static String getString(String key) {
        return PropsUtil.getString(configProps, key);
    }

    public static String getString(String key, String defaultValue) {
        return PropsUtil.getString(configProps, key, defaultValue);
    }


    public static int getInt(String key) {
        return PropsUtil.getNumber(configProps, key);
    }

    public static int getInt(String key, int defaultValue) {
        return PropsUtil.getNumber(configProps, key, defaultValue);
    }

    public static boolean getBoolean(String key) {
        return PropsUtil.getBoolean(configProps, key);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return PropsUtil.getBoolean(configProps, key, defaultValue);
    }

    public static Map<String, Object> getMap(String prefix) {
        return PropsUtil.getMap(configProps, prefix);
    }
}
