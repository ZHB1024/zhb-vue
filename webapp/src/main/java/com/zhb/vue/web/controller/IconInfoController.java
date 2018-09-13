package com.zhb.vue.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.zhb.forever.framework.vo.OrderVO;
import com.zhb.vue.params.IconInfoParam;
import com.zhb.vue.pojo.IconInfoData;
import com.zhb.vue.service.IconInfoService;
import com.zhb.vue.web.util.Data2JSONUtil;
import com.zhb.vue.web.util.WriteJSUtil;

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
    public AjaxData searchIconInfo(HttpServletRequest request,HttpServletResponse response,IconInfoParam param) {
        AjaxData ajaxData = searchIconInfo2AjaxData(param,request);
        return ajaxData;
    }
    
    //to新增icon信息
    @RequestMapping(value="/toadd",method=RequestMethod.GET)
    @Transactional
    public String toAdd(HttpServletRequest request,HttpServletResponse response) {
        return "htgl.icon.add";
    }
    
    //新增icon信息
    @RequestMapping(value="/addiconinfo/api")
    @ResponseBody
    @Transactional
    public AjaxData addIconInfo(HttpServletRequest request,HttpServletResponse response,IconInfoParam param) {
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
    
    //to修改icon信息
    @RequestMapping(value="/toupdate",method=RequestMethod.GET)
    @Transactional
    public String toUdate(HttpServletRequest request,HttpServletResponse response,IconInfoParam param) {
        if (StringUtil.isBlank(param.getId())) {
            return WriteJSUtil.writeJS("非法操作", response);
        }
        IconInfoData data = iconInfoService.getIconInfoById(param.getId());
        if (null == data || 1 == data.getDeleteFlag()) {
            return WriteJSUtil.writeJS("非法操作", response);
        }
        request.setAttribute("iconInfoJson", Data2JSONUtil.iconInfoData2JSONArray(data));
        return "htgl.icon.update";
    }
    
    //修改icon信息
    @RequestMapping(value="/updateiconinfo/api")
    @ResponseBody
    @Transactional
    public AjaxData updateIconInfo(HttpServletRequest request,HttpServletResponse response,IconInfoParam param) {
        AjaxData ajaxData = new AjaxData();
        if (StringUtil.isBlank(param.getId())) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("非法操作");
            return ajaxData;
        }
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
        
        IconInfoData data = iconInfoService.getIconInfoById(param.getId());
        if (null == data) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("非法操作");
            return ajaxData;
        }
        data.setName(param.getName());
        data.setValue(param.getValue());
        data.setUpdateTime(Calendar.getInstance());
        iconInfoService.saveOrUpdate(data);
        
        ajaxData.setFlag(true);
        return ajaxData;
    }
    
    //删除或开通图标
    @RequestMapping(value="/deloropenicon/api",method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public AjaxData delOrOpenIcon(HttpServletRequest request,HttpServletResponse response,IconInfoParam param) {
        AjaxData ajaxData = new AjaxData();
        if (StringUtil.isBlank(param.getId()) || null == param.getDeleteFlag()) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("非法操作");
            return ajaxData;
        }
        IconInfoData data = iconInfoService.getIconInfoById(param.getId());
        if (null == data) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("非法操作");
            return ajaxData;
        }
        data.setDeleteFlag(param.getDeleteFlag());
        data.setUpdateTime(Calendar.getInstance());
        iconInfoService.saveOrUpdate(data);
        
        ajaxData = searchIconInfo2AjaxData(null, request);
        return ajaxData;
    }
    
    
    //共用查询
    private AjaxData searchIconInfo2AjaxData(IconInfoParam param,HttpServletRequest request) {
        AjaxData ajaxData = new AjaxData();
        if (null == param) {
            param = new IconInfoParam();
        }
        
        //排序字段
        List<OrderVO> orderVos = new ArrayList<>();
        OrderVO vo = new OrderVO("deleteFlag",true);
        orderVos.add(vo);
        OrderVO vo2 = new OrderVO("createTime",false);
        orderVos.add(vo2);
        
        List<IconInfoData> userInfos = iconInfoService.getIconInfos(param,orderVos);
        if (null != userInfos) {
            ajaxData.setData(Data2JSONUtil.iconInfoDatas2JSONArray(userInfos));
        }
        ajaxData.setFlag(true);
        return ajaxData;
    }
    
}
