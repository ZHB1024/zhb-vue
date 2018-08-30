package com.zhb.vue.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.zhb.forever.framework.util.AjaxData;
import com.zhb.vue.pojo.UserFunctionInfoData;
import com.zhb.vue.pojo.UserInfoData;
import com.zhb.vue.service.FunctionInfoService;
import com.zhb.vue.util.FunctionUtil;
import com.zhb.vue.web.util.WebAppUtil;

@Controller
@RequestMapping("/htgl/functioninfocontroller")
public class FunctionInfoController {
    
    private static Logger logger = LoggerFactory.getLogger(FunctionInfoController.class);
    
    @Autowired
    private FunctionInfoService functionInfoService;
    
    @RequestMapping("/getfunctions/api")
    @ResponseBody
    @Transactional
    public AjaxData getFunctions(HttpServletRequest request) {
        AjaxData ajaxData = new AjaxData();
        UserInfoData userInfo = WebAppUtil.getLoginInfoVO(request).getUserInfoData();
        List<UserFunctionInfoData> datas = functionInfoService.getDataByUser(userInfo);
        JSONArray jsonArray = FunctionUtil.generateJSonArray(datas);
        
        ajaxData.setData(jsonArray);
        ajaxData.setFlag(true);
        return ajaxData;
    }

}
