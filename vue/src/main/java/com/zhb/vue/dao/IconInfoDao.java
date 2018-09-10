package com.zhb.vue.dao;

import java.util.List;

import com.zhb.vue.params.IconInfoParam;
import com.zhb.vue.pojo.IconInfoData;

public interface IconInfoDao {
    
    
    /**
     * 新增或修改 图标
     * @param data
     */
    void saveOrUpdate(IconInfoData data);
    
    /**
     * *获取 图标
     * @param param
     */
    List<IconInfoData> getIconInfos(IconInfoParam param);

}
