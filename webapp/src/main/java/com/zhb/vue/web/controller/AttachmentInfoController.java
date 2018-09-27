package com.zhb.vue.web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletOutputStream;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhb.forever.framework.Constants;
import com.zhb.forever.framework.dic.AttachmentTypeEnum;
import com.zhb.forever.framework.page.Page;
import com.zhb.forever.framework.page.PageUtil;
import com.zhb.forever.framework.util.AjaxData;
import com.zhb.forever.framework.util.DateTimeUtil;
import com.zhb.forever.framework.util.DownloadUtil;
import com.zhb.forever.framework.util.PropertyUtil;
import com.zhb.forever.framework.util.StringUtil;
import com.zhb.forever.framework.vo.KeyValueVO;
import com.zhb.forever.framework.vo.OrderVO;
import com.zhb.vue.params.AttachmentInfoParam;
import com.zhb.vue.pojo.AttachmentInfoData;
import com.zhb.vue.service.AttachmentInfoService;
import com.zhb.vue.web.util.Data2JSONUtil;
import com.zhb.vue.web.util.WebAppUtil;
import com.zhb.vue.web.util.WriteJSUtil;

@Controller
@RequestMapping("/htgl/attachmentinfocontroller")
public class AttachmentInfoController {
    
    private Logger logger = LoggerFactory.getLogger(AttachmentInfoController.class);
    
    @Autowired
    private AttachmentInfoService attachmentInfoService;
    
    //toindex
    @RequestMapping(value = "/toindex",method = RequestMethod.GET)
    @Transactional
    public String toIndex(HttpServletRequest request,HttpServletResponse response) {
        if (StringUtil.isBlank(WebAppUtil.getUserId(request))) {
            return "login.index";
        }
        return "htgl.attachment.index";
    }
    
    //查询
    @RequestMapping(value = "/getattachmentinfo/api")
    @ResponseBody
    @Transactional
    public AjaxData getAttachmentInfo(HttpServletRequest request,HttpServletResponse response,AttachmentInfoParam param) {
        AjaxData ajaxData = new AjaxData();
        if (StringUtil.isBlank(WebAppUtil.getUserId(request))) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("请先登录");
            return ajaxData;
        }
        ajaxData = searchAttachmentInfo2AjaxData(param, request);
        return ajaxData;
    }
    
    //查询,分页
    @RequestMapping(value = "/getattachmentinfopage/api")
    @ResponseBody
    @Transactional
    public AjaxData getAttachmentInfoPage(HttpServletRequest request,HttpServletResponse response,AttachmentInfoParam param) {
        AjaxData ajaxData = new AjaxData();
        if (StringUtil.isBlank(WebAppUtil.getUserId(request))) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("请先登录");
            return ajaxData;
        }
        ajaxData = searchAttachmentInfo2AjaxDataPage(param);
        return ajaxData;
    }
    
    //toupload
    @RequestMapping(value = "/toupload",method = RequestMethod.GET)
    @Transactional
    public String toUpload(HttpServletRequest request,HttpServletResponse response) {
        if (StringUtil.isBlank(WebAppUtil.getUserId(request))) {
            return "login.index";
        }
        return "htgl.attachment.upload";
    }
    
    //上传
    @RequestMapping(value = "/uploadattachmentinfo")
    @Transactional
    public String uploadAttachmentInfo(HttpServletRequest request,HttpServletResponse response) {
        if (StringUtil.isBlank(WebAppUtil.getUserId(request))) {
            return WriteJSUtil.writeJS("请先登录", response);
        }
        
        // 转型为MultipartHttpRequest：
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获得文件
        MultipartFile multipartFile = multipartRequest.getFile("upFile");
        if (null == multipartFile) {
            return WriteJSUtil.writeJS("请选择待上传的附件", response);
        }
        
        Long fileSize = multipartFile.getSize();
        if (fileSize > Constants.FILE_MAX_SIZE) {
            return WriteJSUtil.writeJS("文件大小不能超过20M", response);
        }
        String contentType = multipartFile.getContentType();
        String fileName = multipartFile.getOriginalFilename();
        
        String filePath = PropertyUtil.getUploadPath();
        File fileUpload = new File(filePath);
        if (!fileUpload.exists()) {
            fileUpload.mkdirs();
        }
        String fileTempName = DateTimeUtil.getDateTime(Calendar.getInstance(), "yyyyMMddHHmmss") + fileName.substring(fileName.indexOf("."));
        String uploadPath = fileUpload + File.separator + fileTempName;
        
        File file = new File(uploadPath);
        
        InputStream is = null;
        OutputStream licOutput = null;
        try {
            is = multipartFile.getInputStream();
            licOutput = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b)) != -1) {
                licOutput.write(b, 0, len);
            }
            licOutput.flush();
            AttachmentInfoData fileInfoData = new AttachmentInfoData();
            fileInfoData.setFileName(fileName);
            fileInfoData.setFilePath(uploadPath);
            fileInfoData.setFileSize(String.valueOf(fileSize));
            fileInfoData.setContentType(contentType);
            fileInfoData.setType(AttachmentTypeEnum.geTypeEnum(fileName).getIndex());
            attachmentInfoService.saveOrUpdate(fileInfoData);
        } catch (IOException e) {
            e.printStackTrace();
            return WriteJSUtil.writeJS("上传异常", response);
        }finally {
            if (null != licOutput) {
                try {
                    licOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "htgl.attachment.index";
    }
    
    //下载
    @RequestMapping(value = "/downloadattachmentinfo")
    @Transactional
    public void downloadAttachmentInfo(HttpServletRequest request,HttpServletResponse response,String id) {
        if (StringUtil.isBlank(WebAppUtil.getUserId(request))) {
            String rootPath = WebAppUtil.getRootPath(request);
            String imagePath = rootPath + "images" + File.separator + "loading.gif";
            response.setContentType("image/jpeg");
            DownloadUtil.downloadDefault(request, response, imagePath);
            return;
        }
        
        if(StringUtil.isBlank(id)) {
            String rootPath = WebAppUtil.getRootPath(request);
            String imagePath = rootPath + "images" + File.separator + "loading.gif";
            response.setContentType("image/jpeg");
            DownloadUtil.downloadDefault(request, response, imagePath);
            return;
        }
        
        AttachmentInfoData data = attachmentInfoService.getAttachmentInfoById(id);
        if (null == data || AttachmentTypeEnum.IMAGE.getIndex() != data.getType()){
            String rootPath = WebAppUtil.getRootPath(request);
            String imagePath = rootPath + "images" + File.separator + "loading.gif";
            response.setContentType("image/jpeg");
            DownloadUtil.downloadDefault(request, response, imagePath);
            return;
        }
        try {
            DownloadUtil.processBeforeDownload(request, response, data.getContentType(), data.getFileName());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        File file = new File(data.getFilePath());
        
        FileInputStream fis = null;
        ServletOutputStream sos = null;
        BufferedInputStream bi = null;
        BufferedOutputStream bo = null;
        try {
            fis = new FileInputStream(file);
            bi = new BufferedInputStream(fis);
            sos = response.getOutputStream();
            bo = new BufferedOutputStream(sos);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = bi.read(buffer, 0, buffer.length)) != -1) {
                bo.write(buffer, 0, bytesRead);
            }
            bo.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null != bo) {
                try {
                    bo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != sos) {
                try {
                    sos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != bi) {
                try {
                    bi.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    //附件类型
    @RequestMapping(value = "/getattachmenttype/api")
    @ResponseBody
    @Transactional
    public AjaxData getAttachmentType(HttpServletRequest request,HttpServletResponse response) {
        AjaxData ajaxData = new AjaxData();
        if (StringUtil.isBlank(WebAppUtil.getUserId(request))) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("请先登录");
            return ajaxData;
        }
        List<KeyValueVO> vos = AttachmentTypeEnum.getAll();
        ajaxData.setFlag(true);
        ajaxData.setData(vos);
        return ajaxData;
    }
    
    //共用查询,不分页
    private AjaxData searchAttachmentInfo2AjaxData(AttachmentInfoParam param,HttpServletRequest request) {
        AjaxData ajaxData = new AjaxData();
        if (null == param) {
            param = new AttachmentInfoParam();
        }
        
        //排序字段
        List<OrderVO> orderVos = new ArrayList<>();
        OrderVO vo = new OrderVO("category",true);
        orderVos.add(vo);
        OrderVO vo2 = new OrderVO("orderIndex",true);
        orderVos.add(vo2);
        
        List<AttachmentInfoData> attachmentInfos = attachmentInfoService.getAttachmentInfos(param,orderVos);
        if (null != attachmentInfos) {
            ajaxData.setData(Data2JSONUtil.attachmentInfoDatas2JSONArray(attachmentInfos));
        }
        ajaxData.setFlag(true);
        return ajaxData;
    }
    
    //共用查询,分页
    private AjaxData searchAttachmentInfo2AjaxDataPage(AttachmentInfoParam param) {
        AjaxData ajaxData = new AjaxData();
        if (null == param) {
            param = new AttachmentInfoParam();
        }
        if ("undefined".equals(param.getType())) {
            param.setType(null);
        }
        //排序字段
        List<OrderVO> orderVos = new ArrayList<>();
        OrderVO vo = new OrderVO("createTime",false);
        orderVos.add(vo);
        OrderVO vo2 = new OrderVO("type",true);
        orderVos.add(vo2);
        
        //设置分页信息
        if(null == param.getCurrentPage()){
            param.setCurrentPage(1);
        }
        if(null == param.getPageSize()){
            param.setPageSize(PageUtil.PAGE_SIZE);
        }
        param.setStart(param.getPageSize()*(param.getCurrentPage()-1));
        
        Page<AttachmentInfoData> page = attachmentInfoService.getAttachmentInfosPage(param,orderVos);
        JSONObject jsonObject = new JSONObject();
        if (null != page) {
            JSONArray jsonArray = Data2JSONUtil.attachmentInfoDatas2JSONArray(page.getList());
            jsonObject = PageUtil.pageInfo2JSON(page.getTotalCount(), page.getPageCount(), page.getCurrrentPage(), jsonArray);
        }else{
            jsonObject = PageUtil.pageInfo2JSON(0,param.getPageSize(),1,new JSONArray());
        }
        ajaxData.setFlag(true);
        ajaxData.setData(jsonObject);
        return ajaxData;
    }

}
