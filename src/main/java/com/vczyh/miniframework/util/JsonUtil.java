package com.vczyh.miniframework.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 将 POJO 转为 Json
     */
    public static <T> String toJson(T obj) {
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("POJO转为Json失败", e);
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * 将 Json 转为 POJO
     */
    public static <T> T fromJson(String json, Class<T> type) {
        T obj;
        try {
            obj = OBJECT_MAPPER.readValue(json, type);
        } catch (Exception e) {
            logger.error("POJO转为Json失败", e);
            throw new RuntimeException(e);
        }
        return obj;
    }

}
