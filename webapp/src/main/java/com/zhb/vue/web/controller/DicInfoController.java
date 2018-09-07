package com.zhb.vue.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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

import com.zhb.forever.framework.dic.DeleteFlagEnum;
import com.zhb.forever.framework.util.AjaxData;
import com.zhb.forever.framework.util.PoiUtil;
import com.zhb.forever.framework.util.StringUtil;
import com.zhb.vue.params.DicInfoParam;
import com.zhb.vue.pojo.DicInfoData;
import com.zhb.vue.service.DicInfoService;
import com.zhb.vue.web.util.Data2JSONUtil;
import com.zhb.vue.web.util.WebAppUtil;
import com.zhb.vue.web.util.WriteJSUtil;

@Controller
@RequestMapping("/htgl/dicinfocontroller")
public class DicInfoController {

    private static Logger logger = LoggerFactory.getLogger(DicInfoController.class);
    
    @Autowired
    private DicInfoService dicInfoService;
    
    @RequestMapping(value = "/todicindex",method = RequestMethod.GET)
    @Transactional
    public String toDicIndex(HttpServletRequest request,HttpServletResponse response) {
        if (StringUtil.isBlank(WebAppUtil.getUserId(request))) {
            return "login.index";
        }
        return "htgl.dic.index";
    }
    
    @RequestMapping(value = "/getdic/api")
    @ResponseBody
    @Transactional
    public AjaxData getDic(HttpServletRequest request,HttpServletResponse response,DicInfoParam param) {
        AjaxData ajaxData = new AjaxData();
        if (StringUtil.isBlank(WebAppUtil.getUserId(request))) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("请先登录");
            return ajaxData;
        }
        List<DicInfoData> datas = dicInfoService.getDicInfos(param);
        ajaxData.setData(Data2JSONUtil.dicInfoDatas2JSONArray(datas));
        ajaxData.setFlag(true);
        return ajaxData;
    }
    
    @RequestMapping(value = "/touploaddic",method = RequestMethod.GET)
    @Transactional
    public String toUploadDic(HttpServletRequest request,HttpServletResponse response) {
        if (StringUtil.isBlank(WebAppUtil.getUserId(request))) {
            return "login.index";
        }
        return "htgl.dic.upload";
    }
    
    @RequestMapping(value = "/uploaddic")
    @Transactional
    public String uploadDic(HttpServletRequest request,HttpServletResponse response) {
        AjaxData ajaxData = new AjaxData();
        if (StringUtil.isBlank(WebAppUtil.getUserId(request))) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("请先登录");
            return WriteJSUtil.writeJS("请先登录", response);
        }
        
        // 转型为MultipartHttpRequest：
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获得文件：
        MultipartFile multipartFile = multipartRequest.getFile("upFile");
        Long fileSize = multipartFile.getSize();
        String fileType = multipartFile.getContentType();
        String fileName = multipartFile.getOriginalFilename();;
        
        Workbook wb = null;;
        try {
            wb = PoiUtil.createWorkbook(multipartFile.getInputStream());
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        Map<Integer, List<DicInfoData>> dataMap = new HashMap<>();
        int sheetCount = wb.getNumberOfSheets();
        for (int i = 0; i < sheetCount; i++) {
            Sheet sheet = wb.getSheetAt(i);
            if (null == sheet) {
                continue;
            }
            int totalRow = sheet.getPhysicalNumberOfRows();
            if (totalRow ==0 ) {
                continue;
            }
            if (totalRow == 1) {
                return WriteJSUtil.writeJS("请补全第 " + (i+1) +" 个sheet页的内容", response);
            }
            List<DicInfoData> dicInfoDatas = new ArrayList<>();
            int firstRow = sheet.getFirstRowNum();
            int lastRow = sheet.getLastRowNum();
            for(int currentRow=firstRow+1; currentRow <= lastRow; currentRow++) {
                Row row = sheet.getRow(currentRow);
                if (null == row) {
                    continue;
                }
                
                String[] values = new String[9];
                for(int cellIndex=0;cellIndex < 9;cellIndex++) {
                    Cell cell = row.getCell(cellIndex);
                    if (null == cell) {
                        continue;
                    }
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String content = cell.getStringCellValue();
                    if (StringUtil.isNotBlank(content)) {
                        values[cellIndex] = content;
                    }
                }
                
                DicInfoData data = new DicInfoData();
                if (StringUtil.isBlank(values[0])) {
                    return WriteJSUtil.writeJS("请补全第 " + (i+1) +" 个sheet页,第 " + (currentRow+1) + " 行，第 1 列的内容" , response);
                }
                data.setCategory(values[0]);
                
                if (StringUtil.isBlank(values[1])) {
                    return WriteJSUtil.writeJS("请补全第 " + (i+1) +" 个sheet页,第 " + (currentRow+1) + " 行，第 2 列的内容" , response);
                }
                data.setCode(values[1]);
                
                if (StringUtil.isBlank(values[2])) {
                    return WriteJSUtil.writeJS("请补全第 " + (i+1) +" 个sheet页,第 " + (currentRow+1) + " 行，第 3 列的内容" , response);
                }
                data.setName(values[2]);
                data.setName2(values[3]);
                data.setName3(values[4]);
                
                if (StringUtil.isBlank(values[5])) {
                    return WriteJSUtil.writeJS("请补全第 " + (i+1) +" 个sheet页,第 " + (currentRow+1) + " 行，第 6 列的内容" , response);
                }
                data.setType(values[5]);
                
                if (StringUtil.isBlank(values[6])) {
                    return WriteJSUtil.writeJS("请补全第 " + (i+1) +" 个sheet页,第 " + (currentRow+1) + " 行，第 7 列的内容" , response);
                }
                data.setOrderIndex(Integer.valueOf(values[6]));
                
                data.setRemark(values[7]);
                
                if (StringUtil.isBlank(values[8])) {
                    data.setDeleteFlag(DeleteFlagEnum.UDEL.getIndex());
                }else {
                    data.setDeleteFlag(Integer.valueOf(values[8]));
                }
                
                dicInfoDatas.add(data);
                
            }
            dataMap.put(i, dicInfoDatas);
        }
        if (dataMap.size() > 0) {
            for (Map.Entry<Integer, List<DicInfoData>> entry : dataMap.entrySet()) {
                List<DicInfoData> datas = entry.getValue();
                if (datas.size() > 0) {
                    dicInfoService.saveOrUpdate(datas);
                }
            }
        }
        ajaxData.setFlag(true);
        return "htgl.dic.index";
    }
    
    
}
