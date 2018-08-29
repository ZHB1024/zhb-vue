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
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhb.forever.framework.util.StringUtil;
import com.zhb.vue.params.UserInfoParam;
import com.zhb.vue.pojo.ParamsData;
import com.zhb.vue.pojo.UserInfoData;
import com.zhb.vue.service.ParamsService;
import com.zhb.vue.service.UserInfoService;
import com.zhb.vue.util.PasswordUtil;
import com.zhb.vue.web.util.AjaxData;
import com.zhb.vue.web.util.WebAppUtil;
import com.zhb.vue.web.vo.LoginInfoVO;

@Controller
@RequestMapping("/logincontroller")
public class LoginController {
    
    private Logger logger = LoggerFactory.getLogger(LoginController.class);
    
    @Autowired
    private ParamsService paramsService;
    @Autowired
    private UserInfoService userInfoService;
    
    @RequestMapping(value = "/tologin",method = RequestMethod.GET)
    @Transactional
    public String toLogin(HttpServletRequest request,HttpServletResponse response) {
        return "login.index";
    }
    
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @Transactional
    public AjaxData login(HttpServletRequest request,HttpServletResponse response,UserInfoParam param) {
        AjaxData ajaxData = new AjaxData();
        if (StringUtil.isBlank(param.getUserName())) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("请填写用户名");
            return ajaxData;
        }
        List<UserInfoData> userInfoDatas = userInfoService.getUserInfos(param);
        
        if (null == userInfoDatas || userInfoDatas.size() == 0 ) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("没有这个用户");
            return ajaxData;
        }
        UserInfoData userInfoData = userInfoDatas.get(0);
        String password = PasswordUtil.decrypt(param.getUserName(), param.getPassword(), PasswordUtil.generateSalt(userInfoData.getSalt()));
        if (!userInfoData.getPassword().equals(password)) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("密码错误");
            return ajaxData;
        }
        LoginInfoVO loginInfoVO = new LoginInfoVO();
        loginInfoVO.setUserInfoData(userInfoData);
        WebAppUtil.setLoginUserVO(request, loginInfoVO);
        WebAppUtil.setUserId(request, userInfoData.getId());
        
        ajaxData.setFlag(true);
        return ajaxData;
    }
    
    
    /*@RequestMapping("/tologin")
    @Transactional
    public String toLogin(HttpServletRequest request,HttpServletResponse response) {
        logger.info("---------------login----------------------");
        List<ParamsData> datas = paramsService.getParams();
        if (null != datas) {
            for (ParamsData paramsData : datas) {
                logger.info(paramsData.getName());
            }
        }
        return "login.index";
    }
    
    @RequestMapping("/tohtgl")
    @Transactional
    public String toHtgl(HttpServletRequest request,HttpServletResponse response) {
        logger.info("---------------htgl----------------------");
        List<ParamsData> datas = paramsService.getParams();
        if (null != datas) {
            for (ParamsData paramsData : datas) {
                logger.info(paramsData.getName());
            }
        }
        return "htgl.index";
    }*/

}
