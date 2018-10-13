package com.zhb.vue.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zhb.vue.Constant;

public class ServiceFactory {
    
    private static ClassPathXmlApplicationContext beanFac = new ClassPathXmlApplicationContext(Constant.APPLICATIONCONTEXT_CONF);
    
    public static Object getBean(String beanId){
        return beanFac.getBean(beanId);
    }
    
    public static VerificationCodeInfoService getVerificationCodeInfoService() {
        VerificationCodeInfoService verificationCodeInfoService = beanFac.getBean("verificationCodeInfoServiceImpl", VerificationCodeInfoService.class);
        return verificationCodeInfoService;
    }
    
    public static AttachmentInfoService getAttachmentInfoService() {
        AttachmentInfoService attachmentInfoService = beanFac.getBean("attachmentInfoServiceImpl", AttachmentInfoService.class);
        return attachmentInfoService;
    }

}
