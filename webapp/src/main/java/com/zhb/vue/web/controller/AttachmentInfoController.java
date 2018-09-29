package com.zhb.vue.web.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhb.forever.framework.Constants;
import com.zhb.forever.framework.dic.AttachmentTypeEnum;
import com.zhb.forever.framework.page.Page;
import com.zhb.forever.framework.page.PageUtil;
import com.zhb.forever.framework.util.AjaxData;
import com.zhb.forever.framework.util.DownloadUtil;
import com.zhb.forever.framework.util.ImageUtil;
import com.zhb.forever.framework.util.PropertyUtil;
import com.zhb.forever.framework.util.StringUtil;
import com.zhb.forever.framework.util.UploadUtil;
import com.zhb.forever.framework.vo.KeyValueVO;
import com.zhb.forever.framework.vo.OrderVO;
import com.zhb.vue.params.AttachmentInfoParam;
import com.zhb.vue.pojo.AttachmentInfoData;
import com.zhb.vue.service.AttachmentInfoService;
import com.zhb.vue.web.util.Data2JSONUtil;
import com.zhb.vue.web.util.WebAppUtil;

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
    
    //删除
    @RequestMapping(value = "/deleteattachmentinfo/api")
    @ResponseBody
    @Transactional
    public AjaxData deleteAttachmentInfo(HttpServletRequest request,HttpServletResponse response,AttachmentInfoParam param) {
        AjaxData ajaxData = new AjaxData();
        if (StringUtil.isBlank(WebAppUtil.getUserId(request))) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("请先登录");
            return ajaxData;
        }
        if (StringUtil.isBlank(param.getId())) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("请选择待删除的数据");
            return ajaxData;
        }
        
        AttachmentInfoData data = attachmentInfoService.getAttachmentInfoById(param.getId());
        if (null == data) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("没有待删除的数据");
            return ajaxData;
        }
        
        File origin = new File(data.getFilePath());
        if (origin.exists()) {
            origin.delete();
        }
        
        if (StringUtil.isNotBlank(data.getThumbnailPath())) {
            File thumbnail = new File(data.getThumbnailPath());
            if (thumbnail.exists()) {
                thumbnail.delete();
            }
        }
        
        attachmentInfoService.deleteAttachmentInfo(data);
        
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
    @ResponseBody
    @Transactional
    public AjaxData uploadAttachmentInfo(HttpServletRequest request,HttpServletResponse response) {
        AjaxData ajaxData = new AjaxData();
        if (StringUtil.isBlank(WebAppUtil.getUserId(request))) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("请先登录");
            return ajaxData;
        }
        
        // 转型为MultipartHttpRequest：
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获得文件
        MultipartFile multipartFile = multipartRequest.getFile("upFile");
        if (null == multipartFile) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("请选择待上传的附件");
            return ajaxData;
        }
        //文件大小
        Long fileSize = multipartFile.getSize();
        if (fileSize > Constants.FILE_MAX_SIZE) {
            ajaxData.setFlag(false);
            ajaxData.addMessage(multipartFile.getOriginalFilename() + " 文件大小不能超过" + Constants.FILE_MAX_SIZE_MB);
            return ajaxData;
        }
        //文件内容类型
        String contentType = multipartFile.getContentType();
        //文件名字
        String fileName = multipartFile.getOriginalFilename();
        Integer type = AttachmentTypeEnum.geTypeEnum(fileName).getIndex();
        
        //原始文件保存路径
        String filePath = PropertyUtil.getUploadPath();
        
        File fileUpload = new File(filePath);
        if (!fileUpload.exists()) {
            fileUpload.mkdirs();
        }
        String fileTempName = DownloadUtil.randomName() + fileName.substring(fileName.indexOf("."));
        String uploadPath = fileUpload + File.separator + fileTempName;
        
        File file = new File(uploadPath);
        try {
            UploadUtil.inputStream2File(multipartFile.getInputStream(), file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        String uploadThumbnailPath = null;
        if (1 ==  type) {//图片时，才有缩略图
            String fileThumbnailPath = PropertyUtil.getUploadThumbnailPath();//缩略图路径
            File fileThumbnailUpload = new File(fileThumbnailPath);
            if (!fileThumbnailUpload.exists()) {
                fileThumbnailUpload.mkdirs();
            }
            uploadThumbnailPath = fileThumbnailPath + File.separator + fileTempName;
            
            File thumbnailFile = new File(uploadThumbnailPath);
            
            if (fileSize > Constants.SMALL_IMAGE_SIZE) {
                try {
                    byte[] bytes = ImageUtil.smallImage(multipartFile.getInputStream(), ImageUtil.getImageSuffix(file),fileSize, Constants.SMALL_IMAGE_SIZE);
                    InputStream is = new ByteArrayInputStream(bytes);
                    UploadUtil.inputStream2File(is, thumbnailFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                uploadThumbnailPath = uploadPath;
            }
        }
        
        AttachmentInfoData fileInfoData = new AttachmentInfoData();
        fileInfoData.setFileName(fileName);
        fileInfoData.setFilePath(uploadPath);
        fileInfoData.setThumbnailPath(uploadThumbnailPath);
        fileInfoData.setFileSize(String.valueOf(fileSize));
        fileInfoData.setContentType(contentType);
        fileInfoData.setType(AttachmentTypeEnum.geTypeEnum(fileName).getIndex());
        attachmentInfoService.saveOrUpdate(fileInfoData);
        ajaxData.setFlag(true);
        return ajaxData;
    }
    
    //获取附件
    @RequestMapping(value = "/downloadattachmentinfo")
    @Transactional
    public void downloadAttachmentInfo(HttpServletRequest request,HttpServletResponse response,String id) {
        if (StringUtil.isBlank(WebAppUtil.getUserId(request))) {
            return;
        }
        
        if(StringUtil.isBlank(id)) {
            return;
        }
        
        AttachmentInfoData data = attachmentInfoService.getAttachmentInfoById(id);
        if (null == data ){
            return;
        }
        try {
            DownloadUtil.processBeforeDownload(request, response, data.getContentType(), data.getFileName());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        
        DownloadUtil.downloadAttachment(request, response, data.getFilePath());
    }
    
    //获取原图
    @RequestMapping(value = "/getoriginalattachmentinfo")
    @Transactional
    public void getOriginalAttachmentInfo(HttpServletRequest request,HttpServletResponse response,String id) {
        if (StringUtil.isBlank(WebAppUtil.getUserId(request))) {
            String rootPath = WebAppUtil.getRootPath(request);
            String imagePath = rootPath + "images" + File.separator + "loading.gif";
            response.setContentType("image/jpeg");
            DownloadUtil.downloadAttachment(request, response, imagePath);
            return;
        }
        
        if(StringUtil.isBlank(id)) {
            String rootPath = WebAppUtil.getRootPath(request);
            String imagePath = rootPath + "images" + File.separator + "loading.gif";
            response.setContentType("image/jpeg");
            DownloadUtil.downloadAttachment(request, response, imagePath);
            return;
        }
        
        AttachmentInfoData data = attachmentInfoService.getAttachmentInfoById(id);
        if (null == data || AttachmentTypeEnum.IMAGE.getIndex() != data.getType()){
            String rootPath = WebAppUtil.getRootPath(request);
            String imagePath = rootPath + "images" + File.separator + "loading.gif";
            response.setContentType("image/jpeg");
            DownloadUtil.downloadAttachment(request, response, imagePath);
            return;
        }
        
        // will return -1 if no header...(没缓存的照片时no header)
        long clientLastModified = request.getDateHeader("If-Modified-Since");
        if (clientLastModified != -1) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return;
        }
        
        try {
            //在浏览器缓存30天
            DownloadUtil.processExpiresTime(response);
            DownloadUtil.processBeforeDownload(request, response, data.getContentType(), data.getFileName());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        
        //加水印 下载
        DownloadUtil.downloadAttachmentWithWaterPrint(request, response, data.getFilePath(), data.getContentType().contains("gif"));
        
    }
    
    //获取缩略图
    @RequestMapping(value = "/getthumbnailattachmentinfo")
    @Transactional
    public void getThumbnailAttachmentInfo(HttpServletRequest request,HttpServletResponse response,String id) {
        if (StringUtil.isBlank(WebAppUtil.getUserId(request))) {
            String rootPath = WebAppUtil.getRootPath(request);
            String imagePath = rootPath + "images" + File.separator + "loading.gif";
            response.setContentType("image/jpeg");
            DownloadUtil.downloadAttachment(request, response, imagePath);
            return;
        }
        
        if(StringUtil.isBlank(id)) {
            String rootPath = WebAppUtil.getRootPath(request);
            String imagePath = rootPath + "images" + File.separator + "loading.gif";
            response.setContentType("image/jpeg");
            DownloadUtil.downloadAttachment(request, response, imagePath);
            return;
        }
        
        AttachmentInfoData data = attachmentInfoService.getAttachmentInfoById(id);
        if (null == data || AttachmentTypeEnum.IMAGE.getIndex() != data.getType()){
            String rootPath = WebAppUtil.getRootPath(request);
            String imagePath = rootPath + "images" + File.separator + "loading.gif";
            response.setContentType("image/jpeg");
            DownloadUtil.downloadAttachment(request, response, imagePath);
            return;
        }
        
        long clientLastModified = request.getDateHeader("If-Modified-Since");
        if (clientLastModified != -1) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return;
        }
        
        try {
            //在浏览器缓存30天
            DownloadUtil.processExpiresTime(response);
            DownloadUtil.processBeforeDownload(request, response, data.getContentType(), data.getFileName());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        
        DownloadUtil.downloadAttachment(request, response, data.getThumbnailPath());
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
        param.setId(null);
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
        param.setId(null);
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
