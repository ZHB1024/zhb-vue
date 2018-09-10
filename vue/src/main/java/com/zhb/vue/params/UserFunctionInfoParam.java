package com.zhb.vue.params;

import com.zhb.vue.pojo.FunctionInfoData;
import com.zhb.vue.pojo.UserInfoData;

public class UserFunctionInfoParam {

    private String id;
    private UserInfoData userInfoData;
    private FunctionInfoData functionInfoData;
    
    private String userId;
    private String functionId;
    
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public UserInfoData getUserInfoData() {
        return userInfoData;
    }
    public void setUserInfoData(UserInfoData userInfoData) {
        this.userInfoData = userInfoData;
    }
    public FunctionInfoData getFunctionInfoData() {
        return functionInfoData;
    }
    public void setFunctionInfoData(FunctionInfoData functionInfoData) {
        this.functionInfoData = functionInfoData;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getFunctionId() {
        return functionId;
    }
    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }
    
}
