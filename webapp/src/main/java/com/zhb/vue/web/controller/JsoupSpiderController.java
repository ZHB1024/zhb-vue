package com.zhb.vue.web.controller;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.zhb.forever.framework.util.AjaxData;
import com.zhb.forever.framework.util.DateTimeUtil;
import com.zhb.forever.framework.util.JsoupUtil;
import com.zhb.forever.framework.util.PropertyUtil;
import com.zhb.vue.thread.spider.DownloadFromQueueRunnable;
import com.zhb.vue.thread.spider.ReadBeginUrlToQueueRunnable;
import com.zhb.vue.thread.spider.ReadEndUrlToQueueRunnable;
import com.zhb.vue.web.util.JsoupSpiderRunnableUtil;
import com.zhb.vue.web.util.WebAppUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/htgl/jsoupspidercontroller")
public class JsoupSpiderController {
    
    private Logger logger = LoggerFactory.getLogger(JsoupSpiderController.class);
    
    @RequestMapping(value="/toIndex",method=RequestMethod.GET)
    public String toSpider(HttpServletRequest request,HttpServletResponse response){
        /*Document doc = JsoupUtil.getDocumentByUrl("http://www.mtl018.com/thread-1996-1-156.html");
        if (null == doc) {
        }
        Elements main = doc.getElementsByClass("t_f");
        for (Element link : main) {
            Elements hrefs = link.select("img[src]");
            for (Element element : hrefs) {
                System.out.println(element.attr("abs:file"));
            }
                
        }*/
        return "htgl.spider.index";
    }

    @RequestMapping(value="/spideryellow",method=RequestMethod.POST)
    @ResponseBody
    @Transactional
    public AjaxData spiderYellow(HttpServletRequest request,HttpServletResponse response){
        AjaxData ajaxData = new AjaxData();
        String userId = WebAppUtil.getUserId(request);
        String url = PropertyUtil.getSpiderUrl();
        String urlTarget = PropertyUtil.getSpiderUrlTarget();
        Integer totalPage = PropertyUtil.getSpiderTotalPage();
        ArrayBlockingQueue<JSONObject> resources = new ArrayBlockingQueue<JSONObject>(100000);

        AtomicInteger beginPage = new AtomicInteger(0);
        AtomicInteger endPage = new AtomicInteger(totalPage);
        AtomicInteger totalCount = new AtomicInteger(0);
        ExecutorService es = Executors.newFixedThreadPool(3);
        es.execute(new ReadEndUrlToQueueRunnable(url,urlTarget,beginPage,endPage,totalCount,resources));
        //es.execute(new ReadBeginUrlToQueueRunnable(url,urlTarget,beginPage,endPage,totalCount,resources));
        
        es.execute(new DownloadFromQueueRunnable(resources,userId));
        
        es.shutdown();
        
        ajaxData.setFlag(true);
        return ajaxData;
    }
    
    @RequestMapping(value="/spider2",method=RequestMethod.POST)
    public String spider2(HttpServletRequest request,HttpServletResponse response){
        String url = JsoupUtil.getUrl();
        String basePath = JsoupUtil.getBaseSavePath();
        String personalizedPath = JsoupUtil.getPersonalizedSavePath();
        String targetSavePath = basePath + personalizedPath + DateTimeUtil.TODAY_FORMAT;

        int totalPage = Integer.valueOf(JsoupUtil.getTotalPage());
        int totalThread = Integer.valueOf(JsoupUtil.getTotalThread());
        int perPage = totalPage/totalThread;

        ExecutorService es = Executors.newFixedThreadPool(totalThread);
        int totalThreadIndex = totalThread-1;
        for(int i=0 ;i < totalThread ;i++) {
            if (i != totalThreadIndex) {
                es.execute(new JsoupSpiderRunnableUtil(url,targetSavePath,i,i*totalThread+1,i*totalThread+perPage));
            }else {
                es.execute(new JsoupSpiderRunnableUtil(url,targetSavePath,i,i*totalThread+1,totalPage));
            }

        }
        es.shutdown();
        
        /*String url = "https://movie.douban.com/explore#!type=movie&tag=热门&sort=rank&page_limit=20&page_start=0";
        //Document doc = JsoupUtil.getDocumentByUrl(url);

        *//**HtmlUnit请求web页面*//*
        WebClient wc = new WebClient();
        wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
        wc.getOptions().setCssEnabled(false); //禁用css支持
        wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
        wc.getOptions().setTimeout(10000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
        HtmlPage page = null;
        try {
            page = wc.getPage(url);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        String pageXml = page.asXml(); //以xml的形式获取响应文本

        *//**jsoup解析文档*//*
        Document doc = Jsoup.parse(pageXml);
        System.out.println(doc);*/

        return "htgl.spider.index";
    }

}
