package com.vczyh.miniframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;

public class CodeUtil {

    private static final Logger logger = LoggerFactory.getLogger(CodeUtil.class);

    /**
     * URL编码
     */
    public static String encodeURL(String source) {
        String target;
        try {
            target = URLEncoder.encode(source, "UTF-8");
        } catch (Exception e) {
            logger.error("URL编码失败", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * URl解码
     */
    public static String decodeURL(String source) {
        String target;
        try {
            target = URLEncoder.encode(source, "UTF-8");
        } catch (Exception e) {
            logger.error("URL解码失败", e);
            throw new RuntimeException(e);
        }
        return target;
    }
}
