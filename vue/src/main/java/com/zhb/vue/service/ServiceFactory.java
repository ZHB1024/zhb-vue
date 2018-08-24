package com.zhb.vue.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zhb.vue.Constant;

public class ServiceFactory {
    
    private static ClassPathXmlApplicationContext beanFac = new ClassPathXmlApplicationContext(Constant.APPLICATIONCONTEXT_CONF);
    
    public static Object getBean(String beanId){
        return beanFac.getBean(beanId);
    }

}
