package com.zhb.vue.thread.runnable;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhb.forever.search.SearchFactory;
import com.zhb.forever.search.solr.SolrClient;
import com.zhb.forever.search.solr.vo.AttachmentInfoSolrData;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年10月26日上午9:21:40
*/

public class UpdateAttachmentSolrIndexRunnable implements Runnable {
    
    private Logger logger = LoggerFactory.getLogger(UpdateAttachmentSolrIndexRunnable.class);
    
    private String name;
    private List<AttachmentInfoSolrData> datas;
    
    private SolrClient solrClient = null;

    public UpdateAttachmentSolrIndexRunnable(String name,List<AttachmentInfoSolrData> datas) {
        this.name = name;
        this.solrClient = SearchFactory.getSolrClientBean();
        this.datas = datas;
    }

    @Override
    public void run() {
        if (null != datas && datas.size() > 0) {
            try {
                solrClient.addAttachments(datas);
                logger.info("添加附件索引线程" + name + " 添加了" + datas.size() + "个索引");
            } catch (Exception e) {
                logger.error("添加附件索引线程" + name + "异常.........");
                e.printStackTrace();
            }
        }

    }

}


