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

import com.zhb.forever.framework.util.AjaxData;
import com.zhb.forever.framework.util.PasswordUtil;
import com.zhb.forever.framework.util.RandomUtil;
import com.zhb.forever.framework.util.StringUtil;
import com.zhb.vue.dic.FunctionTypeEnum;
import com.zhb.vue.params.UserInfoParam;
import com.zhb.vue.pojo.FunctionInfoData;
import com.zhb.vue.pojo.IconInfoData;
import com.zhb.vue.pojo.UserFunctionInfoData;
import com.zhb.vue.pojo.UserInfoData;
import com.zhb.vue.service.FunctionInfoService;
import com.zhb.vue.service.IconInfoService;
import com.zhb.vue.service.UserInfoService;
import com.zhb.vue.util.Data2VO;
import com.zhb.vue.web.util.WebAppUtil;
import com.zhb.vue.web.util.WriteJSUtil;
import com.zhb.vue.web.vo.LoginInfoVO;

@Controller
@RequestMapping("/logincontroller")
public class LoginController {
    
    private Logger logger = LoggerFactory.getLogger(LoginController.class);
    
    @Autowired
    private UserInfoService userInfoService;
    
    @Autowired
    private FunctionInfoService functionInfoService;
    
    @Autowired
    private IconInfoService iconInfoService;
    
    @RequestMapping(value = "/tologin",method = RequestMethod.GET)
    @Transactional
    public String toLogin(HttpServletRequest request,HttpServletResponse response) {
        System.out.println(request.getAttribute("redirectUrl"));
        return "login.index";
    }
    
    @RequestMapping(value = "/login/api",method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public AjaxData login(HttpServletRequest request,HttpServletResponse response,UserInfoParam param) {
        AjaxData ajaxData = new AjaxData();
        if (StringUtil.isBlank(param.getUserName())|| StringUtil.isBlank(param.getPassword())) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("请填写用户名或密码");
            return ajaxData;
        }
        List<UserInfoData> userInfoDatas = userInfoService.getUserInfos(param);
        
        if (null == userInfoDatas || userInfoDatas.size() == 0 ) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("没有这个用户");
            return ajaxData;
        }
        UserInfoData userInfoData = userInfoDatas.get(0);
        String password = PasswordUtil.encrypt(param.getUserName(), param.getPassword(), PasswordUtil.generateSalt(userInfoData.getSalt()));
        if (!userInfoData.getPassword().equals(password)) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("密码错误");
            return ajaxData;
        }
        LoginInfoVO loginInfoVO = new LoginInfoVO();
        
        loginInfoVO.setUserInfoVO(Data2VO.userInfoDat2VO(userInfoData));
        WebAppUtil.setLogInfoVO(request, loginInfoVO);
        WebAppUtil.setUserId(request, userInfoData.getId());
        
        ajaxData.setFlag(true);
        return ajaxData;
    }
    
    @RequestMapping(value = "/initroot/api",method = RequestMethod.GET)
    @Transactional
    public void initRoot(HttpServletRequest request,HttpServletResponse response) {
        
        UserInfoParam param = new UserInfoParam();
        param.setUserName("root");
        List<UserInfoData> userInfoDatas = userInfoService.getUserInfos(param);
        if (null == userInfoDatas || userInfoDatas.size() == 0 ) {
            //用户信息
            UserInfoData data = new UserInfoData();
            data.setUserName("root");
            String salt = RandomUtil.getRandomString(8);
            data.setPassword(PasswordUtil.encrypt("root", PasswordUtil.DEFAULT_PASSWORD, PasswordUtil.generateSalt(salt)));
            data.setSalt(salt);
            userInfoService.saveOrUpdate(data);
            
            //图标
            IconInfoData iconInfoData = new IconInfoData();
            iconInfoData.setName("用户管理");
            iconInfoData.setValue("ios-person");
            iconInfoService.saveOrUpdate(iconInfoData);
            
            //功能信息
            FunctionInfoData root = new FunctionInfoData();
            root.setName("用户管理");
            root.setType(FunctionTypeEnum.ROOT.getIndex());
            root.setPath("userinfocontroller");
            root.setIconInfoData(iconInfoData);
            root.setOrder(1);
            functionInfoService.saveOrUpdate(root);
            
            FunctionInfoData children = new FunctionInfoData();
            children.setName("个人信息");
            children.setType(FunctionTypeEnum.ONE_LEVEL.getIndex());
            children.setPath("/htgl/userinfocontroller/searchuserinfo");
            children.setOrder(2);
            children.setParentFunctionInfo(root);
            functionInfoService.saveOrUpdate(children);
            
            //人员功能关系
            UserFunctionInfoData userFunctionInfoData = new UserFunctionInfoData();
            userFunctionInfoData.setUserInfoData(data);
            userFunctionInfoData.setFunctionInfoData(children);
            functionInfoService.saveOrUpdate(userFunctionInfoData);
            
            
            WriteJSUtil.writeJS("init root success", response);
        }
        WriteJSUtil.writeJS("root 已存在", response);
    }
    
}
