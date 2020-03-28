package com.vczyh.miniframework;

import com.vczyh.miniframework.bean.Data;
import com.vczyh.miniframework.bean.Handler;
import com.vczyh.miniframework.bean.Param;
import com.vczyh.miniframework.bean.View;
import com.vczyh.miniframework.core.ConfigHelper;
import com.vczyh.miniframework.helper.ControllerHelper;
import com.vczyh.miniframework.ioc.BeanHelper;
import com.vczyh.miniframework.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
@WebServlet(urlPatterns = "/", loadOnStartup = 0)
//@WebServlet(urlPatterns = "/a", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet{

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        HelperLoader.init();
        ServletContext servletContext = servletConfig.getServletContext();
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getString(ConfigConstant.APP_JSP_PATH) + "*");
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getString(ConfigConstant.APP_ASSET_PATH) + "*");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HelperLoader.init();
        String requestMethod = request.getMethod();
        String requestPath = request.getServletPath();
//        System.out.println(request.getParameter("name"));
//        System.out.println(request.getParameter("id"));
        System.out.println("requestMethod：" + requestMethod);//
        System.out.println("requestPath:" + requestPath);//
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        System.out.println(handler == null);
        if (handler != null) {
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            Map<String, Object> paramMap = new HashMap<String, Object>();
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String paramValue = request.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }
            String body = CodeUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
//            System.out.println("body: " + body);
            if (StringUtil.isNotEmpty(body)) {
                String[] params = StringUtil.splitString(body, "&");
                if (ArrayUtil.isNotEmpty(params)) {
                    for (String param : params) {
                        String[] array = StringUtil.splitString(param, "=");
                        if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
                            String paramName = array[0];
                            String paramvalue = array[1];
                            paramMap.put(paramName, paramvalue);
                        }
                    }

                }
            }
            Param param = new Param(paramMap);
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
            if (result instanceof View) {
                View view = (View) result;
                String path = view.getPath();
                if (path.startsWith("/")) {
                    response.sendRedirect(request.getContextPath() + path);
                } else {
                    Map<String, Object> modle = view.getModle();
                    for (Map.Entry<String, Object> entry : modle.entrySet()) {
                        request.setAttribute(entry.getKey(), entry.getValue());
                    }
                    request.getRequestDispatcher(ConfigHelper.getString(ConfigConstant.APP_JSP_PATH) + path)
                            .forward(request, response);
                }
            } else if (result instanceof Data) {
                Data data = (Data) result;
                Object modle = data.getModle();
                if (modle != null) {
                    String json = JsonUtil.toJson(modle);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter writer = response.getWriter();
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }
        }
        PrintWriter writer = response.getWriter();
        writer.write("<h1>11111111111111<h1>");
    }
}
