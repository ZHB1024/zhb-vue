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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhb.forever.framework.dic.DeleteFlagEnum;
import com.zhb.forever.framework.util.AjaxData;
import com.zhb.forever.framework.util.StringUtil;
import com.zhb.vue.params.FunctionInfoParam;
import com.zhb.vue.params.IconInfoParam;
import com.zhb.vue.params.UserInfoParam;
import com.zhb.vue.pojo.FunctionInfoData;
import com.zhb.vue.pojo.IconInfoData;
import com.zhb.vue.pojo.UserFunctionInfoData;
import com.zhb.vue.pojo.UserInfoData;
import com.zhb.vue.service.FunctionInfoService;
import com.zhb.vue.service.IconInfoService;
import com.zhb.vue.service.UserInfoService;
import com.zhb.vue.web.util.Data2JSONUtil;
import com.zhb.vue.web.util.Param2DataUtil;
import com.zhb.vue.web.util.WebAppUtil;

@Controller
@RequestMapping("/htgl/functioninfocontroller")
public class FunctionInfoController {
    
    private static Logger logger = LoggerFactory.getLogger(FunctionInfoController.class);
    
    @Autowired
    private FunctionInfoService functionInfoService;
    
    @Autowired
    private UserInfoService userInfoService;
    
    @Autowired
    private IconInfoService iconInfoService;
    
    
    @RequestMapping(value="/toindex",method=RequestMethod.GET)
    @Transactional
    public String toIndex(HttpServletRequest request,HttpServletResponse response) {
        return "htgl.function.index";
    }
    
    @RequestMapping("/getfunctions/api")
    @ResponseBody
    @Transactional
    public AjaxData getFunctions(HttpServletRequest request,HttpServletResponse response,FunctionInfoParam param) {
        AjaxData ajaxData = new AjaxData();
        param.setType(1);
        List<FunctionInfoData> datas = functionInfoService.getFunctions(param);
        JSONArray jsonArray = Data2JSONUtil.functionInfo2JSONArray(datas);
        
        ajaxData.setData(jsonArray);
        ajaxData.setFlag(true);
        return ajaxData;
    }
    
    @RequestMapping(value="/toadd",method=RequestMethod.GET)
    @Transactional
    public String toAdd(HttpServletRequest request,HttpServletResponse response) {
        return "htgl.function.add";
    }
    
    @RequestMapping("/addfunctioninfo/api")
    @ResponseBody
    @Transactional
    public AjaxData addFunctionInfo(HttpServletRequest request,HttpServletResponse response,FunctionInfoParam param) {
        AjaxData ajaxData = new AjaxData();
        if (StringUtil.isBlank(WebAppUtil.getUserId(request))) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("请先登录");
            return ajaxData;
        }
        
        if (StringUtil.isBlank(param.getName())) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("请输入功能名称");
            return ajaxData;
        }
        if (StringUtil.isBlank(param.getPath())) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("请输入功能路径");
            return ajaxData;
        }
        
        if (StringUtil.isNotBlank(param.getParentId()) && !"undefined".equals(param.getParentId())) {
            FunctionInfoParam param2 = new FunctionInfoParam();
            param2.setId(param.getParentId());
            List<FunctionInfoData> datas = functionInfoService.getFunctions(param2);
            if (null != datas && datas.size() > 0) {
                param.setParentFunctionInfo(datas.get(0));
                param.setType(1);
            }else {
                param.setType(0);
            }
        }else {
            param.setType(0);
        }
        
        if (StringUtil.isNotBlank(param.getIconId())) {
            IconInfoParam iconInfoParam = new IconInfoParam();
            iconInfoParam.setId(param.getIconId());
            List<IconInfoData> iconInfoDatas = iconInfoService.getIconInfos(iconInfoParam,null);
            if (null != iconInfoDatas && iconInfoDatas.size() > 0) {
                param.setIconInfoData(iconInfoDatas.get(0));
            }
        }
        param.setDeleteFlag(DeleteFlagEnum.UDEL.getIndex());
        
        FunctionInfoData data = new FunctionInfoData();
        Param2DataUtil.functionParam2Data(param, data);
        
        functionInfoService.saveOrUpdate(data);
        
        //将功能授权给管理员
        if (data.getType() == 1) {
            UserInfoParam userInfoParam = new UserInfoParam();
            userInfoParam.setId(WebAppUtil.getUserId(request));
            List<UserInfoData> userInfoDatas = userInfoService.getUserInfos(userInfoParam,null);
            
            UserFunctionInfoData userFunctionInfoData = new UserFunctionInfoData();
            userFunctionInfoData.setFunctionInfoData(data);
            userFunctionInfoData.setUserInfoData(userInfoDatas.get(0));
        }
        
        ajaxData.setFlag(true);
        return ajaxData;
    }
    
    @RequestMapping("/getparentfunctions/api")
    @ResponseBody
    @Transactional
    public AjaxData getParentFunctions(HttpServletRequest request,HttpServletResponse response) {
        AjaxData ajaxData = new AjaxData();
        FunctionInfoParam param = new FunctionInfoParam();
        param.setType(0);
        List<FunctionInfoData> datas = functionInfoService.getFunctions(param);
        JSONArray jsonArray = new JSONArray();
        for(FunctionInfoData funData : datas){
            JSONObject json = new JSONObject();
            json.put("id", funData.getId());
            json.put("name", funData.getName());
            json.put("path", funData.getPath());
            json.put("icon", funData.getIconInfoData().getName());
            jsonArray.add(json);
        }
        
        ajaxData.setData(jsonArray);
        ajaxData.setFlag(true);
        return ajaxData;
    }
    
    @RequestMapping("/getchildfunctions/api")
    @ResponseBody
    @Transactional
    public AjaxData getChildFunctions(HttpServletRequest request,HttpServletResponse response) {
        AjaxData ajaxData = new AjaxData();
        FunctionInfoParam param = new FunctionInfoParam();
        param.setType(1);
        List<FunctionInfoData> datas = functionInfoService.getFunctions(param);
        JSONArray jsonArray = new JSONArray();
        for(FunctionInfoData funData : datas){
            JSONObject json = new JSONObject();
            json.put("id", funData.getId());
            json.put("name", funData.getName());
            json.put("parentName", funData.getParentFunctionInfo().getName());
            json.put("path", funData.getPath());
            jsonArray.add(json);
        }
        
        ajaxData.setData(jsonArray);
        ajaxData.setFlag(true);
        return ajaxData;
    }
    
    @RequestMapping("/getmaxorder/api")
    @ResponseBody
    @Transactional
    public AjaxData getMaxOrder(HttpServletRequest request,HttpServletResponse response,FunctionInfoParam param) {
        AjaxData ajaxData = new AjaxData();
        if (StringUtil.isNotBlank(param.getParentId()) && !"undefined".equals(param.getParentId())) {
            FunctionInfoData data = functionInfoService.getFunctionById(param.getParentId());
            param.setParentFunctionInfo(data);
        }
        int max = functionInfoService.getMaxOrder(param);
        if (StringUtil.isNotBlank(param.getParentId()) && !"undefined".equals(param.getParentId())) {
            max += 1;
        }else {
            int temp = max / 10;
            max = (temp+1)*10 + 1;
        }
        ajaxData.setData(max);
        ajaxData.setFlag(true);
        return ajaxData;
    }
    
}
