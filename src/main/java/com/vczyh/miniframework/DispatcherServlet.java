package com.vczyh.miniframework;

import com.vczyh.miniframework.bean.Hander;
import com.vczyh.miniframework.bean.Request;
import com.vczyh.miniframework.bean.View;
import com.vczyh.miniframework.processor.ComponentProcessor;
import com.vczyh.miniframework.processor.ControllerProcessor;
import com.vczyh.miniframework.processor.ParamProcessor;
import com.vczyh.miniframework.util.JsonUtil;
import com.vczyh.miniframework.util.ReflectUtil;

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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        InitUtil.init();
        ServletContext servletContext = config.getServletContext();
//        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
//        jspServlet.addMapping("/WEB-INF/jsp/" + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestPath = req.getServletPath();
        String requestMethod = req.getMethod();
        Request request = new Request(requestPath, requestMethod);
        Hander hander = ControllerProcessor.getHander(request);
        if (hander != null) {
            System.out.println("hander" + hander);
            Map<String, Object> PARAM_MAP = new HashMap<>();
            Enumeration<String> paramNames = req.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paraName = paramNames.nextElement();
                Object paraValue = req.getParameter(paraName);
                PARAM_MAP.put(paraName, paraValue);
            }
            ParamProcessor.setParamMap(PARAM_MAP);
            ParamProcessor.setHttpServletRequest(req);
            Object result = ControllerProcessor.invokeMethod(request);
            if (result instanceof View) {
                View view = (View) result;
                // 客户端跳转
                if (view.getPath().startsWith("/")) {
                    resp.sendRedirect(req.getContextPath() + view.getPath());
                } else {
                    for (Map.Entry<String, Object> entry : view.getModel().entrySet()) {
                        req.setAttribute(entry.getKey(), entry.getValue());
                    }
                    req.getRequestDispatcher("/WEB-INF/jsp/" + view.getPath()).forward(req, resp);
                }
            } else {
                String json = JsonUtil.toJson(result);
                PrintWriter out = resp.getWriter();
                out.print(json);
                out.flush();
                out.close();

            }
        }
    }
}
