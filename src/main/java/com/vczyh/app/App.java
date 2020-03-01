package com.vczyh.app;

import com.vczyh.app.controller.TestController;
import com.vczyh.app.entity.User;
import com.vczyh.miniframework.InitUtil;
import com.vczyh.miniframework.annotation.Autowired;
import com.vczyh.miniframework.annotation.Component;
import com.vczyh.miniframework.annotation.RequestMappinig;
import com.vczyh.miniframework.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.List;


/**
 * Hello world!
 */
@Component
public class App {
    @Autowired
    private static User user;

    public static void main(String[] args) throws NoSuchMethodException {

        Method method = TestController.class.getMethod("test", String.class, int.class);
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            Annotation[] annotations = parameter.getAnnotations();
            for (Annotation annotation : annotations) {
                System.out.println(annotation.annotationType());
                RequestParam requestParam = (RequestParam) parameter.getAnnotation(RequestParam.class);
                System.out.println(requestParam.value());
            }
        }

        List<String> list = new LinkedList<>();
        list.add("1");
        list.add("1");
        list.add("2");
        list.add(null);
        System.out.println(list.toArray().length);


        System.out.println(Number.class.isAssignableFrom(int.class));
    }
}
