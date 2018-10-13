package com.zhb.vue.thread.spider;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhb.forever.framework.util.JsoupUtil;
import com.zhb.forever.framework.util.StringUtil;

public class ReadUrlToQueueRunnable implements Runnable {
    
    private Logger logger = LoggerFactory.getLogger(ReadUrlToQueueRunnable.class);
    
    private String url;
    private String urlTarget;
    private Integer totalPage;
    private ArrayBlockingQueue<String> resources ;
    
    private AtomicInteger page ;
    private AtomicInteger totalCount = new AtomicInteger(0);
    
    public ReadUrlToQueueRunnable(String url,String urlTarget,Integer totalPage,ArrayBlockingQueue<String> resources) {
        this.url = url;
        this.urlTarget = urlTarget;
        this.totalPage = totalPage;
        this.resources = resources;
        page = new AtomicInteger(totalPage);
    }

    @Override
    public void run() {
        while(page.get() > 0) {
            String targetUrl = url + urlTarget + page.get() + ".html";
            addChildPageImagePath(targetUrl);
            page.decrementAndGet();
        }

    }
    
    private void addChildPageImagePath(String targetPath) {
        if (StringUtil.isNotBlank(targetPath)) {
            Document doc = JsoupUtil.getDocumentByUrl(targetPath);
            if (null == doc) {
                return;
            }
            Element main = doc.getElementById("moderate");
            if (null == main) {
                return;
            }
            Elements liEle = main.getElementsByTag("li");
            if (null == liEle) {
                return;
            }
            for (Element link : liEle) {
                Elements links = link.getElementsByClass("z");
                if (null == links) {
                    return;
                }
                addImagePath(links.get(0).attr("abs:href"));
            }
        }
    }
    
    private void addImagePath(String targetPath) {
        if (StringUtil.isNotBlank(targetPath)) {
            Document doc = JsoupUtil.getDocumentByUrl(targetPath);
            if (null == doc) {
                return;
            }
            Elements main = doc.getElementsByClass("t_f");
            if (null == main) {
                return;
            }
            for (Element link : main) {
                Elements hrefs = link.select("img[src]");
                if (null == hrefs) {
                    return ;
                }
                for (Element element : hrefs) {
                    if (null == element) {
                        return ;
                    }
                    while (!resources.offer(element.attr("abs:file"))) {
                        logger.info("队列已满------等待-------");
                        try {
                            Thread.currentThread().sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            logger.error("向队列里添加url异常");
                        }
                    }
                    logger.info("向队列里添加成功----------------第 " + totalCount.incrementAndGet() + " 个");
                }
            }
        }
    }

}
