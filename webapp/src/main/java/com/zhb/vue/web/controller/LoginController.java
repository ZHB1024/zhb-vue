package com.zhb.vue.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logincontroller")
public class LoginController {
    
    private Logger logger = LoggerFactory.getLogger(LoginController.class);
    
    @RequestMapping("/zhbController")
    public String testController(HttpServletRequest request,HttpServletResponse response) {
        logger.info("---------------zhb----------------------");
        return "zhb.index";
    }

}
