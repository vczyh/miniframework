package com.vczyh.miniframework.helper;

import com.vczyh.miniframework.annotation.Action;
import com.vczyh.miniframework.bean.Handler;
import com.vczyh.miniframework.bean.Request;
import com.vczyh.miniframework.core.ClassHelper;
import com.vczyh.miniframework.util.ArrayUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControllerHelper {

    private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();

    static {
        System.out.println("加载： " + ControllerHelper.class.getName());
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        boolean b = controllerClassSet == null;
        System.out.println("controllerClassSet: " + b);
        if (controllerClassSet != null) {
            for (Class<?> controllerClass : controllerClassSet) {
                Method[] methods = controllerClass.getDeclaredMethods();
                if (ArrayUtil.isNotEmpty(methods)) {
                    for (Method method : methods) {
                        System.out.println(method);
                        if (method.isAnnotationPresent(Action.class)) {
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            System.out.println(mapping);
//                            if (mapping.matches("\\w+:/\\w*")) {
                            if (mapping != null) {
                                System.out.println("11111");
                                String[] array = mapping.split(":");
                                if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
                                    System.out.println(array);
                                    Request request = new Request(array[0], array[1]);
                                    System.out.println(request);
//                                    System.out.println("request.hashCode.first" + request.hashCode());
                                    Handler handler = new Handler(controllerClass, method);
                                    ACTION_MAP.put(request, handler);
                                }
                            }

                        }

                    }
                }
            }
        }
    }

    public static Handler getHandler(String requestMethod, String requestPath) {
        return ACTION_MAP.get(new Request(requestMethod, requestPath));
    }

    public static void print() {
        for (Map.Entry<Request, Handler> entry : ACTION_MAP.entrySet()) {
            System.out.println("Request.method：" + entry.getKey().getRequestMethod() +
            "  Request.path：" + entry.getKey().getRequestPath());
            System.out.println("Handler.controllerClass：" + entry.getValue().getControllerClass() +
                    "  Handler.actionMethod：" + entry.getValue().getActionMethod());
        }
    }
}
