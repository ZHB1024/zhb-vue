package com.zhb.vue.service;

import java.util.List;

import com.zhb.vue.params.FunctionInfoParam;
import com.zhb.vue.params.UserFunctionInfoParam;
import com.zhb.vue.pojo.FunctionInfoData;
import com.zhb.vue.pojo.UserFunctionInfoData;
import com.zhb.vue.pojo.UserInfoData;

public interface FunctionInfoService {
    
    /**
     * *新增或修改功能
     * @param data
     */
    void saveOrUpdate(FunctionInfoData data);
    
    /**
     * *新增或修改 人员功能关系
     * @param data
     */
    void saveOrUpdate(UserFunctionInfoData data);
    
    
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
    
    
    /**
     * *根据用户信息 获取 用户功能关系
     * @param data
     * @return
     */
    List<UserFunctionInfoData> getDataByUser(UserInfoData data);
    
    /**
     * *获取 用户功能关系（授权）
     * @param param
     * @return
     */
    List<UserFunctionInfoData> getUserFunctionInfoDatas(UserFunctionInfoParam param);
    
    

}
