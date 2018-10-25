package com.zhb.vue.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhb.forever.framework.util.AjaxData;
import com.zhb.forever.framework.vo.KeyValueVO;
import com.zhb.forever.search.SearchFactory;
import com.zhb.forever.search.solr.SolrClient;
import com.zhb.forever.search.solr.vo.NewsIndexVO;

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
    public AjaxData addSolr(HttpServletRequest request, HttpServletResponse response) {
        AjaxData ajaxData = new AjaxData();
        List<KeyValueVO> news = new ArrayList<>();
        for(int i=1;i<=50;i++) {
            KeyValueVO vo = new KeyValueVO();
            vo.setId(i+"你好");
            vo.setKey(i+"我不错");
            vo.setValue(i+"天气晴朗");
            news.add(vo);
        }
        /*if (null != news && news.size() > 0) {
            for (KeyValueVO newsInfo : news) {
                solrClient.addNews(newsInfo.getId(), newsInfo.getKey(), newsInfo.getValue());
            }
        }*/
        
        List<NewsIndexVO> vos = solrClient.getNews("天气", "id", 0, 50);
        if (null != vos) {
            logger.info("总共：" + vos.size());
            for (NewsIndexVO newsIndexVO : vos) {
                logger.info(newsIndexVO.getId() + "," + newsIndexVO.getTitle() + "," + newsIndexVO.getContent());
            }
        }
        
        ajaxData.setFlag(true);
        ajaxData.setData("成功");
        return ajaxData;
    }

}


