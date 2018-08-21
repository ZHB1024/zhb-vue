package com.zhb.vue.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhb.vue.pojo.ParamsData;
import com.zhb.vue.service.ParamsService;

@Controller
@RequestMapping("/logincontroller")
public class LoginController {
    
    private Logger logger = LoggerFactory.getLogger(LoginController.class);
    
    @Autowired
    private ParamsService paramsService;
    
    @RequestMapping("/tologin")
    @Transactional
    public String toLogin(HttpServletRequest request,HttpServletResponse response) {
        logger.info("---------------zhb----------------------");
        List<ParamsData> datas = paramsService.getParams();
        if (null != datas) {
            for (ParamsData paramsData : datas) {
                logger.info(paramsData.getName());
            }
        }
        return "login.index";
    }

}
