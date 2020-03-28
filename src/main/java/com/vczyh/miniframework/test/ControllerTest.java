package com.vczyh.miniframework.test;

import com.vczyh.miniframework.annotation.Action;
import com.vczyh.miniframework.annotation.Controller;
import com.vczyh.miniframework.bean.Data;
import com.vczyh.miniframework.bean.Param;
import com.vczyh.miniframework.bean.View;

import java.util.Map;

@Controller
public class ControllerTest {

    @Action("GET:/a/data")
    public Data test1(Param param) {
        Map<String, Object> paramMap = param.getParamMap();
        for (String str : paramMap.keySet()) {
            Object obj = paramMap.get(str);
            System.out.println("key:" + str + " value:" + obj);
        }
        return new Data("Zhang");
    }

    @Action("GET:/a/view")
    public View view (Param param) {
//        return new View("view.jsp");
        return new View("view.jsp");
    }
}
