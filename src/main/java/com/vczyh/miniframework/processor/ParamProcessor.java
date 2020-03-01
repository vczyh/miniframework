package com.vczyh.miniframework.processor;

import org.omg.CORBA.ObjectHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ParamProcessor {

    private static final Map<String, Object> PARAM_MAP = new HashMap<>();
    private static HttpServletRequest request = null;

    public static void setParamMap(Map<String, Object> paramMap) {
        PARAM_MAP.putAll(paramMap);
    }

    public static Object getParam(String paramName) {
        return PARAM_MAP.get(paramName);
    }

    public static void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        request = httpServletRequest;
    }

    public static HttpServletRequest getHttpServletRequest() {
        return request;
    }
}
