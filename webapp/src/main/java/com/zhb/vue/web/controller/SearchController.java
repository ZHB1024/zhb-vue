package com.zhb.vue.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.solr.client.solrj.SolrQuery;
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
import com.zhb.forever.framework.page.Page;
import com.zhb.forever.framework.page.PageUtil;
import com.zhb.forever.framework.util.AjaxData;
import com.zhb.forever.framework.util.DateTimeUtil;
import com.zhb.forever.framework.util.StringUtil;
import com.zhb.forever.framework.vo.KeyValueVO;
import com.zhb.forever.search.SearchFactory;
import com.zhb.forever.search.solr.SolrClient;
import com.zhb.forever.search.solr.param.AttachmentInfoSolrIndexParam;
import com.zhb.forever.search.solr.vo.AttachmentInfoSolrData;
import com.zhb.vue.params.AttachmentInfoParam;
import com.zhb.vue.params.param2SolrIndexParam;
import com.zhb.vue.service.AttachmentInfoService;
import com.zhb.vue.web.util.Data2JSONUtil;
import com.zhb.vue.web.util.WebAppUtil;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年10月25日下午1:26:49
*/

@Controller
@RequestMapping("/htgl/searchController")
public class SearchController {
    
    private Logger logger = LoggerFactory.getLogger(SearchController.class);
    
    private SolrClient solrClient = SearchFactory.getSolrClientBean();
    
    @RequestMapping(value = "/toindex",method = RequestMethod.GET)
    @Transactional
    public String toIndex(HttpServletRequest request,HttpServletResponse response){
        return "htgl.search.index";
    }
    
    /*solr search*/
    @RequestMapping(value = "/solrsearch/api",method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public AjaxData solrSearch(HttpServletRequest request, HttpServletResponse response,AttachmentInfoParam param) {
        AjaxData ajaxData = new AjaxData();
        if (StringUtil.isBlank(WebAppUtil.getUserId(request))) {
            ajaxData.setFlag(false);
            ajaxData.addMessage("请先登录");
            return ajaxData;
        }
        
        AttachmentInfoSolrIndexParam params = param2SolrIndexParam.attachmentParam2SolrIndexParam(param);
        ajaxData = searchAttachmentInfoSolrIndex2AjaxDataPage(params);
        
        return ajaxData;
        
        
        /*List<AttachmentInfoSolrData> datas = solrClient.getAttachments("20181014150903-4631-1.jpg", "createTime", 0, 50);
        if (null != datas && datas.size() > 0) {
            for (AttachmentInfoSolrData data : datas) {
                logger.info(data.getId() + "," + data.getFileName() + "," + DateTimeUtil.getDateTime(data.getCreateTime(), "yyyy-MM-dd HH:mm:ss") + "," + data.getFilePath());
            }
            ajaxData.setFlag(true);
            ajaxData.setData("成功");
        }else {
            ajaxData.setFlag(true);
            ajaxData.setData("没有查到结果");
        }
        
        return ajaxData;*/
    }
    
    //共用查询附件索引,分页
    private AjaxData searchAttachmentInfoSolrIndex2AjaxDataPage(AttachmentInfoSolrIndexParam param) {
        AjaxData ajaxData = new AjaxData();
        if (null == param) {
            param = new AttachmentInfoSolrIndexParam();
        }
        //排序字段
        param.addSort("likeDegree",SolrQuery.ORDER.desc);
        param.addSort("fileName",SolrQuery.ORDER.asc);
        param.addSort("type",SolrQuery.ORDER.asc);
        
        //设置分页信息
        if(null == param.getCurrentPage()){
            param.setCurrentPage(1);
        }
        if(null == param.getPageSize()){
            param.setPageSize(PageUtil.PAGE_SIZE);
        }
        param.setStart(param.getPageSize()*(param.getCurrentPage()-1));
        
        Page<AttachmentInfoSolrData> page = solrClient.searchAttachmentsForPage(param);
        JSONObject jsonObject = new JSONObject();
        if (null != page) {
            JSONArray jsonArray = Data2JSONUtil.attachmentInfoSolrIndexDatas2JSONObject(page.getList());
            jsonObject = PageUtil.pageInfo2JSON(page.getTotalCount(), page.getPageCount(), page.getCurrrentPage(), jsonArray);
        }else{
            jsonObject = PageUtil.pageInfo2JSON(0,param.getPageSize(),1,new JSONArray());
        }
        ajaxData.setFlag(true);
        ajaxData.setData(jsonObject);
        return ajaxData;
    }

}


