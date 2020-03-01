package com.vczyh.app.controller;

import com.vczyh.app.entity.User;
import com.vczyh.miniframework.annotation.Controller;
import com.vczyh.miniframework.annotation.RequestMappinig;
import com.vczyh.miniframework.annotation.RequestParam;
import com.vczyh.miniframework.bean.View;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TestController {

    @RequestMappinig(value = "/test", method = "GET")
    public View test(HttpServletRequest request) {
        User user = new User("aaa", "bbb");
//        return user;
        return new View("hello.jsp");
    }


}
