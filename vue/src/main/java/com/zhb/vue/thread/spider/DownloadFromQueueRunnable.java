package com.zhb.vue.thread.spider;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhb.forever.framework.util.StringUtil;

public class DownloadFromQueueRunnable implements Runnable {
    
    private Logger logger = LoggerFactory.getLogger(DownloadFromQueueRunnable.class);
    
    private ArrayBlockingQueue<String> resources ;
    private String creatorId;
    private AtomicInteger count = new AtomicInteger(0);
    private AtomicInteger shutdowmFlag = new AtomicInteger(0);
    
    
    public DownloadFromQueueRunnable(ArrayBlockingQueue<String> resources,String creatorId) {
        this.resources = resources;
        this.creatorId = creatorId;
    }

    @Override
    public void run() {
        
        while(true) {
            String url = null;
            while(StringUtil.isBlank(url=resources.poll())){
                int flag = shutdowmFlag.incrementAndGet();
                if (flag > 200) {
                    return;
                }
                System.out.println("队列已空------等待-------------");
                try {
                    Thread.currentThread().sleep(10);
                } catch (InterruptedException e) {
                    logger.error("从队列里取url异常");
                    e.printStackTrace();
                }
            }
            shutdowmFlag = new AtomicInteger(0);
            ExecutorService es = Executors.newFixedThreadPool(1);
            es.execute(new DownloadImageRunnable(url,creatorId,count.incrementAndGet()));
            es.shutdown();
        }
    }

}
