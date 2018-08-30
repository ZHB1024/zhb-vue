package com.zhb.vue.dao;

import com.zhb.vue.pojo.FunctionInfoData;

public interface FunctionInfoDao {
    
    /**
     * 新增或修改功能
     * @param data
     */
    void saveOrUpdate(FunctionInfoData data);
    
    
}
