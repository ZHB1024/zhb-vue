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
import com.zhb.forever.framework.util.StringUtil;
import com.zhb.vue.params.IconInfoParam;
import com.zhb.vue.pojo.IconInfoData;
import com.zhb.vue.service.IconInfoService;
import com.zhb.vue.web.util.Data2JSONUtil;

@Controller
@RequestMapping("/htgl/iconinfocontroller")
public class IconInfoController {
    
    private static Logger logger = LoggerFactory.getLogger(IconInfoController.class);
    
    @Autowired
    private IconInfoService iconInfoService;
    
    @RequestMapping(value="/toindex",method=RequestMethod.GET)
    @Transactional
    public String toIndex(HttpServletRequest request,HttpServletResponse response) {
        return "htgl.icon.index";
    }
    
    //查询icon信息
    @RequestMapping(value="/geticoninfo/api")
    @ResponseBody
    @Transactional
    public AjaxData searchUserInfo(HttpServletRequest request,HttpServletResponse response,IconInfoParam param) {
        AjaxData ajaxData = new AjaxData();
        List<IconInfoData> iconIndos = iconInfoService.getIconInfos(param);
        if (null != iconIndos) {
            ajaxData.setData(Data2JSONUtil.iconInfoDatas2JSONArray(iconIndos));
        }
        ajaxData.setFlag(true);
        
        return ajaxData;
    }
    
    
    @RequestMapping(value="/toadd",method=RequestMethod.GET)
    @Transactional
    public String toAdd(HttpServletRequest request,HttpServletResponse response) {
        return "htgl.icon.add";
    }
    
    //新增icon信息
    @RequestMapping(value="/addiconinfo/api")
    @ResponseBody
    @Transactional
    public AjaxData addUserInfo(HttpServletRequest request,HttpServletResponse response,IconInfoParam param) {
        AjaxData ajaxData = new AjaxData();
        if (StringUtil.isBlank(param.getName())) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("请输入图标名称");
            return ajaxData;
        }
        
        if (StringUtil.isBlank(param.getValue())) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("请输入图标代码");
            return ajaxData;
        }
        IconInfoData data = new IconInfoData();
        data.setName(param.getName());
        data.setValue(param.getValue());
        iconInfoService.saveOrUpdate(data);
        
        ajaxData.setFlag(true);
        return ajaxData;
    }
    
}
