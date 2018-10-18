package com.zhb.vue.thread.spider.qbl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.zhb.forever.framework.util.FileUtil;
import com.zhb.forever.framework.util.JsoupUtil;
import com.zhb.forever.framework.util.StringUtil;

public class ReadUrlToQueueRunnable implements Runnable {

    private Logger logger = LoggerFactory.getLogger(ReadUrlToQueueRunnable.class);
    
    private String url;
    private String urlTarget;
    private ArrayBlockingQueue<JSONObject> resources ;
    
    private AtomicInteger endPage ;
    private AtomicInteger beginPage ;
    private AtomicInteger totalCount;
    
    public ReadUrlToQueueRunnable(String url,AtomicInteger beginPage,AtomicInteger endPage,AtomicInteger totalCount,ArrayBlockingQueue<JSONObject> resources) {
        this.url = url;
        this.resources = resources;
        this.endPage = endPage;
        this.beginPage = beginPage;
        this.totalCount = totalCount;
    }

    @Override
    public void run() {
        logger.info("--Read---UrlThread--------------------------开始-------");
        String targetUrl = "";
        while(endPage.get() >= beginPage.get()) {
            targetUrl = url + "_" + beginPage.get() + ".html";
            addChildPageImagePath(targetUrl);
            beginPage.incrementAndGet();
        }
        
        logger.info("--Read-EndUrlThread--------------------------结束-------");

    }
    
    private void addChildPageImagePath(String targetPath) {
        if (StringUtil.isNotBlank(targetPath)) {
            Document doc = JsoupUtil.getDocumentByUrl(targetPath);
            if (null == doc) {
                return;
            }
            Elements divs = doc.getElementsByClass("art");
            if (null != divs) {
                for (Element element : divs) {
                    Elements hrefs = element.select("a[href]");
                    if (null == hrefs) {
                        continue;
                    }
                    for (Element a : hrefs) {
                        String href = a.attr("abs:href");
                        addImagePath(href);
                    }
                }
            }
        }
    }
    
    private void addImagePath(String targetPath) {
        if (StringUtil.isNotBlank(targetPath)) {
            Document doc = JsoupUtil.getDocumentByUrl(targetPath);
            if (null == doc) {
                return;
            }
            Elements artbody = doc.getElementsByClass("artbody");
            if (null == artbody) {
                return;
            }
            
            String name = FileUtil.randomName();
            int i = 0;
            for (Element link : artbody) {
                Elements hrefs = link.select("img[src]");
                if (null == hrefs) {
                    continue ;
                }
                for (Element element : hrefs) {
                    if (null == element) {
                        continue;
                    }
                    i++;
                    StringBuilder fileName = new StringBuilder(name);
                    fileName.append("_"+i);
                    fileName.append(".jpg");
                    JSONObject object = new JSONObject();
                    object.put("name", fileName);
                    object.put("url", element.attr("abs:src"));
                    while (!resources.offer(object)) {
                        logger.info("--队列已满------等待消费-------");
                        try {
                            Thread.currentThread().sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            logger.error("向队列里添加url异常");
                        }
                    }
                    logger.info("--向队列里添加成功----第 " + beginPage.get() + " 页------第 " + totalCount.incrementAndGet() + " 个");
                }
            }
        }
    }

}