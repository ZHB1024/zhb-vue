package com.zhb.vue.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="user_function_info")
public class UserFunctionInfoData {
    
    private String id;
    private UserInfoData userInfoData;
    private FunctionInfoData functionInfoData;
    
    @Id
    @GeneratedValue(generator = "app_seq")
    @GenericGenerator(name = "app_seq", strategy = "com.zhb.vue.pojo.strategy.StringRandomGenerator")
    @Column(name = "ID")
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    public UserInfoData getUserInfoData() {
        return userInfoData;
    }
    public void setUserInfoData(UserInfoData userInfoData) {
        this.userInfoData = userInfoData;
    }
    
    @JoinColumn(name = "function_id")
    @ManyToOne(fetch = FetchType.LAZY)
    public FunctionInfoData getFunctionInfoData() {
        return functionInfoData;
    }
    public void setFunctionInfoData(FunctionInfoData functionInfoData) {
        this.functionInfoData = functionInfoData;
    }
}