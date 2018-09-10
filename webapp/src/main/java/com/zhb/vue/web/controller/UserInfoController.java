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
import com.zhb.forever.framework.util.PasswordUtil;
import com.zhb.forever.framework.util.RandomUtil;
import com.zhb.forever.framework.util.StringUtil;
import com.zhb.forever.framework.vo.UserInfoVO;
import com.zhb.vue.params.UserInfoParam;
import com.zhb.vue.pojo.UserInfoData;
import com.zhb.vue.service.UserInfoService;
import com.zhb.vue.util.Data2VO;
import com.zhb.vue.web.util.CheckUtil;
import com.zhb.vue.web.util.Data2JSONUtil;
import com.zhb.vue.web.util.FlushSessionUtil;
import com.zhb.vue.web.util.Param2DataUtil;
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
    
    
    //查询用户信息
    @RequestMapping(value="/searchuserinfo/api")
    @ResponseBody
    @Transactional
    public AjaxData searchUserInfo(HttpServletRequest request,HttpServletResponse response,UserInfoParam param) {
        AjaxData ajaxData = new AjaxData();
        List<UserInfoData> userInfos = userInfoService.getUserInfos(param);
        if (null != userInfos) {
            ajaxData.setData(Data2JSONUtil.userInfoDatas2JSONArray(userInfos));
        }
        ajaxData.setFlag(true);
        
        return ajaxData;
    }
    
    //to用户个人信息
    @RequestMapping(value="/toselfinfo",method=RequestMethod.GET)
    @Transactional
    public String toSelfInfo(HttpServletRequest request,HttpServletResponse response) {
        UserInfoVO vo = WebAppUtil.getLoginInfoVO(request).getUserInfoVO();
        if (null == vo) {
            return "login.index";
        }
        
        return "htgl.user.info";
    }
    
    //获取个人信息
    @RequestMapping(value="/selfinfo/api")
    @ResponseBody
    @Transactional
    public AjaxData selfInfo(HttpServletRequest request,HttpServletResponse response) {
        AjaxData ajaxData = new AjaxData();
        UserInfoVO vo = WebAppUtil.getLoginInfoVO(request).getUserInfoVO();
        if (null == vo) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("请先登录");
            return ajaxData;
        }
        ajaxData.setFlag(true);
        ajaxData.setData(Data2JSONUtil.userInfoVO2JSONObject(vo));
        return ajaxData;
    }
    
    //to新增一个用户
    @RequestMapping(value="/toadduserinfo",method=RequestMethod.GET)
    @Transactional
    public String toAddUserInfo(HttpServletRequest request,HttpServletResponse response) {
        UserInfoVO vo = WebAppUtil.getLoginInfoVO(request).getUserInfoVO();
        if (null == vo) {
            return "login.index";
        }
        return "htgl.user.add";
    }
    
    //新增一个用户
    @RequestMapping(value="/adduserinfo/api",method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public AjaxData addUserInfo(HttpServletRequest request,HttpServletResponse response,UserInfoParam param,String confirmPassword) {
        AjaxData ajaxData = new AjaxData();
        UserInfoVO vo = WebAppUtil.getLoginInfoVO(request).getUserInfoVO();
        if (null == vo) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("请先登录");
            return ajaxData;
        }
        if (StringUtil.isBlank(param.getUserName())) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("请输入用户名");
            return ajaxData;
        }
        
        if (StringUtil.isBlank(param.getPassword())|| StringUtil.isBlank(confirmPassword)) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("请输入密码或确认密码");
            return ajaxData;
        }
        if (!param.getPassword().equals(confirmPassword)) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("密码与确认密码必须相同");
            return ajaxData;
        }
        
        UserInfoParam userInfoParam = new UserInfoParam();
        userInfoParam.setUserName(param.getUserName());
        List<UserInfoData> datas = userInfoService.getUserInfos(userInfoParam);
        if (null != datas && datas.size() > 0 ) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("此用户名已存在，请更换用户名");
            return ajaxData;
        }
        
        UserInfoData userInfoData = new UserInfoData();
        userInfoData.setUserName(param.getUserName());
        String salt = RandomUtil.getRandomString(8);
        userInfoData.setSalt(salt);
        userInfoData.setPassword(PasswordUtil.encrypt(param.getUserName(), param.getPassword(), PasswordUtil.generateSalt(salt)));
        userInfoService.saveOrUpdate(userInfoData);
        
        ajaxData.setFlag(true);
        return ajaxData;
    }
    
    //to修改用户个人信息
    @RequestMapping(value="/toupdateselfinfo",method=RequestMethod.GET)
    @Transactional
    public String toUpdateSelfInfo(HttpServletRequest request,HttpServletResponse response) {
        UserInfoVO vo = WebAppUtil.getLoginInfoVO(request).getUserInfoVO();
        if (null == vo) {
            return "login.index";
        }
        request.setAttribute("userInfoJson", Data2JSONUtil.userInfoVO2JSONObject(vo));
        return "htgl.user.update";
    }
    
    //修改个人信息
    @RequestMapping(value="/updateselfinfo/api",method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public AjaxData updateSelfInfo(HttpServletRequest request,HttpServletResponse response,UserInfoParam param) {
        AjaxData ajaxData = new AjaxData();
        UserInfoVO vo = WebAppUtil.getLoginInfoVO(request).getUserInfoVO();
        if (null == vo) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("请先登录");
            return ajaxData;
        }
        String msg = CheckUtil.userInfoParamCheck(param);
        if (StringUtil.isNotBlank(msg)) {
            ajaxData.setFlag(false);
            ajaxData.addMessage(msg);
            return ajaxData;
        }
        
        if (!vo.getId().equals(param.getId()) || !vo.getUserName().equals(param.getUserName())) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("您无权修改这条数据");
            return ajaxData;
        }
        
        UserInfoParam userInfoParam = new UserInfoParam();
        userInfoParam.setId(param.getId());
        List<UserInfoData> datas = userInfoService.getUserInfos(userInfoParam);
        UserInfoData userInfoData = null;
        if (null != datas && datas.size() > 0 ) {
            userInfoData = datas.get(0);
            Param2DataUtil.userInfoParam2Data(param, userInfoData);
            userInfoService.saveOrUpdate(userInfoData);
            
            //刷新用户缓存
            FlushSessionUtil.flushWebAppUserInfo(request,Data2VO.userInfoDat2VO(userInfoData));
            
            ajaxData.setFlag(true);
            return ajaxData;
        }
        
        ajaxData.setFlag(false);
        ajaxData.addMessage("没有这条数据");
        return ajaxData;
    }
    
    
    //获取真实姓名
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
