package com.vczyh.miniframework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropsUtil {

    public static Properties getProps(String propsName) {
        if (propsName == null) {
            return null;
        }
        if (!propsName.endsWith(".properties")) {
            propsName += ".properties";
        }
        InputStream is = PropsUtil.class.getClassLoader().getResourceAsStream(propsName);
        Properties props = new Properties();
        try {
            props.load(is);
            return props;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
