package com.zhb.vue.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhb.vue.web.Constant;
import com.zhb.vue.web.util.WebAppUtil;
import com.zhb.vue.web.vo.LoginInfoVO;

public class LoginFilter implements Filter {
    

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)arg0;
        LoginInfoVO loginUserVO = WebAppUtil.getLoginInfoVO(request);
        if (null == loginUserVO || null == loginUserVO.getUserInfoVO()) {
            String ctxPath = request.getContextPath();
            try {
                request.setAttribute(Constant.REQUEST_ERROR, "登陆后才能访问系统");
                request.setAttribute("redirectUrl", request.getRequestURL());
                HttpServletResponse response =(HttpServletResponse)arg1;
                //request.getRequestDispatcher(ctxPath + "/login/login.jsp").forward(request, response);
                request.getRequestDispatcher(ctxPath + "/logincontroller/tologin").forward(request, response);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
            return;
        }
        arg2.doFilter(request, arg1);

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

}
