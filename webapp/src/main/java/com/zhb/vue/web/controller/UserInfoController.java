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
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhb.vue.params.UserInfoParam;
import com.zhb.vue.pojo.UserInfoData;
import com.zhb.vue.service.UserInfoService;

@Controller
@RequestMapping("/htgl/userinfocontroller")
public class UserInfoController {
    
    private static Logger logger = LoggerFactory.getLogger(UserInfoController.class);
    
    @Autowired
    private UserInfoService userInfoService;
    
    @RequestMapping(value="/touserinfo",method=RequestMethod.GET)
    @Transactional
    public String toUserInfo(HttpServletRequest request,HttpServletResponse response) {
        
        return "htgl.user.index";
    }
    
    @RequestMapping(value="/searchuserinfo/api",method=RequestMethod.POST)
    @ResponseBody
    @Transactional
    public void searchUserInfo(HttpServletRequest request,HttpServletResponse response,UserInfoParam param) {
        List<UserInfoData> userInfos = userInfoService.getUserInfos(param);
        if (null != userInfos) {
            for (UserInfoData userInfoData : userInfos) {
                logger.info(userInfoData.getRealName());
            }
        }
    }

}
