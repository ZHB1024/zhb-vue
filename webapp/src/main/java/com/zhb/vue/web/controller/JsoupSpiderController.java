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
import com.zhb.vue.thread.spider.qbl.DownloadQBLFromQueueRunnable;
import com.zhb.vue.thread.spider.qbl.ReadUrlToQueueRunnable;
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
        Document doc = JsoupUtil.getDocumentByUrl("http://111av.org/html/tupian/siwa/index_2.html");
        if (null != doc) {
            Elements divs = doc.getElementsByClass("art");
            if (null != divs) {
                for (Element element : divs) {
                    Elements hrefs = element.select("a[href]");
                    if (null == hrefs) {
                        continue;
                    }
                    for (Element a : hrefs) {
                        String href = a.attr("abs:href");
                        System.out.println(href);
                    }
                }
            }
        }
        return "htgl.spider.index";
    }

    //http://www.mtl018.com/forum-58-1.html
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
    
    //http://111av.org/html/tupian/siwa/index.html
    @RequestMapping(value="/spideryellow2",method=RequestMethod.POST)
    @ResponseBody
    @Transactional
    public AjaxData spiderYellow2(HttpServletRequest request,HttpServletResponse response){
        AjaxData ajaxData = new AjaxData();
        String userId = WebAppUtil.getUserId(request);
        String url = PropertyUtil.getSpiderUrl();
        Integer totalPage = PropertyUtil.getSpiderTotalPage();
        ArrayBlockingQueue<JSONObject> resources = new ArrayBlockingQueue<JSONObject>(100000);

        AtomicInteger beginPage = new AtomicInteger(2);
        AtomicInteger endPage = new AtomicInteger(totalPage);
        AtomicInteger totalCount = new AtomicInteger(0);
        ExecutorService es = Executors.newFixedThreadPool(2);
        
        es.execute(new ReadUrlToQueueRunnable(url,beginPage,endPage,totalCount,resources));
        es.execute(new DownloadQBLFromQueueRunnable(resources,userId));
        
        es.shutdown();
        
        ajaxData.setFlag(true);

        return ajaxData;
    }

}
