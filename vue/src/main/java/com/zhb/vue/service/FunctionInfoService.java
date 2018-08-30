package com.zhb.vue.service;

import java.util.List;

import com.zhb.vue.pojo.FunctionInfoData;
import com.zhb.vue.pojo.UserFunctionInfoData;
import com.zhb.vue.pojo.UserInfoData;

public interface FunctionInfoService {
    
    /**
     * 新增或修改功能
     * @param data
     */
    void saveOrUpdate(FunctionInfoData data);
    
    /**
     * 新增或修改 人员功能关系
     * @param data
     */
    void saveOrUpdate(UserFunctionInfoData data);
    
    
    /**
     * 根据用户信息 获取 用户功能关系
     * @param data
     * @return
     */
    List<UserFunctionInfoData> getDataByUser(UserInfoData data);
    

}
