package com.zhb.vue.web.controller;

import java.io.IOException;
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

import com.zhb.forever.framework.util.AjaxData;
import com.zhb.forever.framework.util.StringUtil;
import com.zhb.forever.framework.vo.UserInfoVO;
import com.zhb.vue.params.UserInfoParam;
import com.zhb.vue.pojo.UserInfoData;
import com.zhb.vue.service.UserInfoService;
import com.zhb.vue.web.util.WebAppUtil;

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
    
    //显示用户个人信息
    @RequestMapping(value="/userinfo",method=RequestMethod.GET)
    @Transactional
    public String userInfo(HttpServletRequest request,HttpServletResponse response) {
        UserInfoVO vo = WebAppUtil.getLoginInfoVO(request).getUserInfoVO();
        if (null == vo) {
            return "login.index";
        }
        request.setAttribute("userInfo", vo);
        
        return "htgl.user.info";
    }
    
    
    @RequestMapping(value="/realname/api")
    @ResponseBody
    @Transactional
    public AjaxData getRealName(HttpServletRequest request,HttpServletResponse response) {
        AjaxData ajaxData = new AjaxData();
        UserInfoVO vo = WebAppUtil.getLoginInfoVO(request).getUserInfoVO();
        if (null == vo || StringUtil.isBlank(vo.getRealName())) {
            ajaxData.setData("崩溃了");
        }else {
            ajaxData.setData(vo.getRealName());
        }
        ajaxData.setFlag(true);
        return ajaxData;
    }
    
    
    //退出系统
    @RequestMapping("/exit")
    @Transactional
    public void exit(HttpServletRequest request,HttpServletResponse response) {
        WebAppUtil.exit(request);
        try{
            response.sendRedirect("/logincontroller/tologin");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
