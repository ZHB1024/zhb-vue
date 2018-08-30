package com.zhb.vue.service;

import com.zhb.vue.pojo.IconInfoData;

public interface IconInfoService {
    
    /**
     * 新增或修改 图标
     * @param data
     */
    void saveOrUpdate(IconInfoData data);

}
