package com.zhb.vue.thread;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.zhb.forever.search.solr.vo.AttachmentInfoSolrData;
import com.zhb.vue.pojo.AttachmentInfoData;
import com.zhb.vue.thread.runnable.UpdateAttachmentSolrIndexRunnable;
import com.zhb.vue.util.Data2SolrIndexUtil;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年10月26日上午10:08:17
*/

public class AttachmentInfo2SolrIndexThread {

    public static boolean createAttachmentSolrIndex(List<AttachmentInfoData> fileInfoDatas) {
        boolean flag = true;
        try {
            List<AttachmentInfoSolrData> datas = Data2SolrIndexUtil.AttachmentInfoDatas2SolrIndex(fileInfoDatas);
            if (null != datas) {
                ExecutorService es = Executors.newFixedThreadPool(1);
                es.execute(new UpdateAttachmentSolrIndexRunnable("thread-addAttachmentSolrIndex", datas));
                es.shutdown();
            }
        }catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

}


