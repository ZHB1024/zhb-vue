package com.zhb.vue.web.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.WebUtils;

import com.zhb.vue.web.Constant;
import com.zhb.vue.web.vo.LoginUserVO;

public class WebAppUtil {
    
    
    public static LoginUserVO getLoginUserVO(HttpServletRequest request) {
        return (LoginUserVO) WebUtils.getSessionAttribute(request, Constant.SESSION_ZHB_VUE);
    }

    public static void setLoginUserVO(HttpServletRequest request, LoginUserVO data) {
        WebUtils.setSessionAttribute(request, Constant.SESSION_ZHB_VUE, data);
    }

    public static String getIp(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

}
