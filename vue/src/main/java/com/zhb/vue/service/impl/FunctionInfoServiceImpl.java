package com.zhb.vue.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhb.vue.dao.FunctionInfoDao;
import com.zhb.vue.dao.UserFunctionInfoDao;
import com.zhb.vue.pojo.FunctionInfoData;
import com.zhb.vue.pojo.UserFunctionInfoData;
import com.zhb.vue.pojo.UserInfoData;
import com.zhb.vue.service.FunctionInfoService;

@Service
public class FunctionInfoServiceImpl implements FunctionInfoService {
    
    @Autowired
    private FunctionInfoDao functionInfoDao;
    
    @Autowired
    private UserFunctionInfoDao userFunctionInfoDao;

    @Override
    public void saveOrUpdate(FunctionInfoData data) {
        functionInfoDao.saveOrUpdate(data);
    }

    @Override
    public void saveOrUpdate(UserFunctionInfoData data) {
        userFunctionInfoDao.saveOrUpdate(data);
    }

    @Override
    public List<UserFunctionInfoData> getDataByUser(UserInfoData data) {
        return userFunctionInfoDao.getDataByUser(data);
    }
    
    

}