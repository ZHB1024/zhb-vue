package com.zhb.vue.dao;

import com.zhb.vue.pojo.IconInfoData;

public interface IconInfoDao {
    
    
    /**
     * 新增或修改 图标
     * @param data
     */
    void saveOrUpdate(IconInfoData data);

}
