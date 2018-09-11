package com.zhb.vue.web.controller;

import java.util.Collections;
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

import com.alibaba.fastjson.JSONArray;
import com.zhb.forever.framework.util.AjaxData;
import com.zhb.forever.framework.util.StringUtil;
import com.zhb.vue.params.FunctionInfoParam;
import com.zhb.vue.params.UserFunctionInfoParam;
import com.zhb.vue.params.UserInfoParam;
import com.zhb.vue.pojo.FunctionInfoData;
import com.zhb.vue.pojo.UserFunctionInfoData;
import com.zhb.vue.pojo.UserInfoData;
import com.zhb.vue.service.FunctionInfoService;
import com.zhb.vue.service.UserInfoService;
import com.zhb.vue.web.util.Data2JSONUtil;
import com.zhb.vue.web.util.Param2DataUtil;
import com.zhb.vue.web.util.WebAppUtil;

@Controller
@RequestMapping("/htgl/authoritycontroller")
public class AuthorityController {

    private Logger logger = LoggerFactory.getLogger(AuthorityController.class);
    
    @Autowired
    private UserInfoService userInfoService;
    
    @Autowired
    private FunctionInfoService functionInfoService;
    
    //左侧功能菜单
    @RequestMapping("/getfunctions/api")
    @ResponseBody
    @Transactional
    public AjaxData getFunctions(HttpServletRequest request,HttpServletResponse response) {
        AjaxData ajaxData = new AjaxData();
        UserInfoParam userInfoParam = new UserInfoParam();
        userInfoParam.setId(WebAppUtil.getUserId(request));
        List<UserInfoData> userInfoData = userInfoService.getUserInfos(userInfoParam);
        List<UserFunctionInfoData> datas = functionInfoService.getDataByUser(userInfoData.get(0));
        JSONArray jsonArray = Data2JSONUtil.generateJSonArray(datas);
        
        ajaxData.setData(jsonArray);
        ajaxData.setFlag(true);
        return ajaxData;
    }
    
    @RequestMapping(value="/toindex",method=RequestMethod.GET)
    @Transactional
    public String toAuthority(HttpServletRequest request,HttpServletResponse response) {
        return "htgl.authority.index";
    }
    
    @RequestMapping("/getauthority/api")
    @ResponseBody
    @Transactional
    public AjaxData getAuthority(HttpServletRequest request,HttpServletResponse response,UserFunctionInfoParam param) {
        AjaxData ajaxData = new AjaxData();
        
        if (StringUtil.isNotBlank(param.getUserId())) {
            UserInfoParam userInfoParam = new UserInfoParam();
            userInfoParam.setId(param.getUserId());
            List<UserInfoData> userInfoData = userInfoService.getUserInfos(userInfoParam);
            param.setUserInfoData(userInfoData.get(0));
        }
        if (StringUtil.isNotBlank(param.getFunctionId())) {
            FunctionInfoParam functionInfoParam = new FunctionInfoParam();
            functionInfoParam.setId(param.getFunctionId());
            List<FunctionInfoData> datas = functionInfoService.getFunctions(functionInfoParam);
            param.setFunctionInfoData(datas.get(0));
        }
        
        List<UserFunctionInfoData> datas = functionInfoService.getUserFunctionInfoDatas(param);
        
        ajaxData.setData(Data2JSONUtil.userFunctionInfoDatas2JSONObject(datas));
        ajaxData.setFlag(true);
        return ajaxData;
    }
    
    
    @RequestMapping(value="/toadd",method=RequestMethod.GET)
    @Transactional
    public String toadd(HttpServletRequest request,HttpServletResponse response) {
        return "htgl.authority.add";
    }
    
    @RequestMapping("/addauthority/api")
    @ResponseBody
    @Transactional
    public AjaxData addAuthority(HttpServletRequest request,HttpServletResponse response,UserFunctionInfoParam param,boolean opt) {
        AjaxData ajaxData = new AjaxData();
        
        if (StringUtil.isBlank(param.getUserId())) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("请选择用户");
            return ajaxData;
        }
        if (StringUtil.isBlank(param.getFunctionId())) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("请选择功能");
            return ajaxData;
        }
        
        UserInfoParam userInfoParam = new UserInfoParam();
        userInfoParam.setId(param.getUserId());
        List<UserInfoData> userInfoData = userInfoService.getUserInfos(userInfoParam);
        param.setUserInfoData(userInfoData.get(0));
        
        String[] functionIds = param.getFunctionId().split(",");
        for (String funId : functionIds) {
            FunctionInfoParam functionInfoParam = new FunctionInfoParam();
            functionInfoParam.setId(funId);
            List<FunctionInfoData> datas = functionInfoService.getFunctions(functionInfoParam);
            param.setFunctionInfoData(datas.get(0));
            
            List<UserFunctionInfoData> userFunctionInfoDatas = functionInfoService.getUserFunctionInfoDatas(param);
            if (null == userFunctionInfoDatas || userFunctionInfoDatas.size() == 0) {
                if (opt) {//新增
                    UserFunctionInfoData data = new UserFunctionInfoData();
                    Param2DataUtil.userFunctionParam2Data(param, data);
                    functionInfoService.saveOrUpdate(data);
                }
            }else {
                if (!opt) {//删除
                    functionInfoService.delUserFunctionInfoData(userFunctionInfoDatas.get(0));
                }
            }
        }
        
        ajaxData.setFlag(true);
        return ajaxData;
    }
}
