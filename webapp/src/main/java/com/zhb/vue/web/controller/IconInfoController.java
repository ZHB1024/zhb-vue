package com.zhb.vue.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhb.vue.service.IconInfoService;

@Controller
@RequestMapping("/iconinfocontroller")
public class IconInfoController {
    
    private static Logger logger = LoggerFactory.getLogger(IconInfoController.class);
    
    @Autowired
    private IconInfoService iconInfoService;
    
    
    
    
    

}
