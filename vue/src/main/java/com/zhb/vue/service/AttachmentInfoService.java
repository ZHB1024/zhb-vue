package com.zhb.vue.service;

import java.util.List;

import com.zhb.forever.framework.page.Page;
import com.zhb.forever.framework.vo.OrderVO;
import com.zhb.vue.params.AttachmentInfoParam;
import com.zhb.vue.params.DicInfoParam;
import com.zhb.vue.pojo.AttachmentInfoData;
import com.zhb.vue.pojo.DicInfoData;

public interface AttachmentInfoService {

    /**
     * 新增或修改 附件
     * @param data
     */
    void saveOrUpdate(AttachmentInfoData data);
    
    /**
     * *获取 附件
     * @param param
     */
    List<AttachmentInfoData> getAttachmentInfos(AttachmentInfoParam param,List<OrderVO> orderVos);
    
    /**
     * *获取 附件,根据id
     * 
     * @param id
     */
    AttachmentInfoData getAttachmentInfoById(String id);
    
    /**
     * *获取 附件,根据fileName
     * 
     * @param fileName
     */
    List<AttachmentInfoData> getAttachmentInfoByFileName(String fileName);

    /**
     * *删除附件
     * 
     * @param AttachmentInfoData
     */
    void deleteAttachmentInfo(AttachmentInfoData data);
    
    /**
     * *分页获取附件
     * 
     * @param param
     * @param orderVos
     * 
     * @return
     */
    Page<AttachmentInfoData> getAttachmentInfosPage(AttachmentInfoParam param,List<OrderVO> orderVos);
}