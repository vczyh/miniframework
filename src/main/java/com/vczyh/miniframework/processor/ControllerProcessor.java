package com.vczyh.miniframework.processor;

import com.vczyh.app.controller.TestController;
import com.vczyh.miniframework.annotation.Controller;
import com.vczyh.miniframework.annotation.RequestMappinig;
import com.vczyh.miniframework.annotation.RequestParam;
import com.vczyh.miniframework.bean.Hander;
import com.vczyh.miniframework.bean.Request;
import com.vczyh.miniframework.util.ClassUtil;
import com.vczyh.miniframework.util.ReflectUtil;
import javafx.scene.effect.SepiaTone;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.security.Policy;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Controller解析
 *
 * @author vczyh
 * @date 2018/12/18
 */
public class ControllerProcessor {

    private static final Map<Class<?>, Object> COMP_BEAN = ComponentProcessor.getCompBean();
    private static final Set<Class<?>> CONTROLLER_CLASS_SET = ClassUtil.isAnnotationClass(Controller.class);
    private static final Map<Request, Hander> CONTRO_MAP = new HashMap<>();

    static {
        for (Class clzz : CONTROLLER_CLASS_SET) {
            Method[] methods = clzz.getDeclaredMethods();
            System.out.println("methods:" + methods);
            for (Method method : methods) {
                if (method.isAnnotationPresent(RequestMappinig.class)) {
                    RequestMappinig requestMappinig = method.getAnnotation(RequestMappinig.class);
                    String requestPath = requestMappinig.value();
                    String requestMethod = requestMappinig.method();
                    Request request = new Request(requestPath, requestMethod);
                    Hander hander = new Hander(clzz, method);
                    CONTRO_MAP.put(request, hander);
                }
            }
        }
    }

    public static Map<Request, Hander> getControMap() {
        return CONTRO_MAP;
    }

    public static Hander getHander(Request request) {
        System.out.println(request.toString());
//        System.out.println(CONTRO_MAP);
        return CONTRO_MAP.get(request);
    }

    public static Object invokeMethod(Request request) {
        Hander hander = getHander(request);
        Method method = hander.getMethod();
        Object controllerBean = ComponentProcessor.getBean(hander.getControllerClass());
        LinkedList<Object> paramValues = new LinkedList<>();
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
           if (parameter.getType() == HttpServletRequest.class) {
               paramValues.add(ParamProcessor.getHttpServletRequest());
           }
        }
        Object result = ReflectUtil.invokeMethod(controllerBean, method, paramValues.toArray());
        return result;
    }

}