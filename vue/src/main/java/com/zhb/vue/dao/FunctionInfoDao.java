package com.zhb.vue.dao;

import java.util.List;

import com.zhb.vue.params.FunctionInfoParam;
import com.zhb.vue.pojo.FunctionInfoData;

public interface FunctionInfoDao {
    
    /**
     * *新增或修改功能
     * @param data
     */
    void saveOrUpdate(FunctionInfoData data);
    
    /**
     * *获取功能
     * @param param
     * @return
     */
    List<FunctionInfoData> getFunctions(FunctionInfoParam param);
    
    /**
     * *获取最大排序号
     * @return
     */
    int getMaxOrder();
    
    
}
