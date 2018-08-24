package com.zhb.vue.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhb.vue.dao.UserInfoDao;
import com.zhb.vue.params.UserInfoParam;
import com.zhb.vue.pojo.UserInfoData;
import com.zhb.vue.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    
    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public void saveOrUpdate(UserInfoData data) {
        if (null == data) {
            return ;
        }
        userInfoDao.saveOrUpdate(data);
    }

    @Override
    public List<UserInfoData> getUserInfos(UserInfoParam userInfoParam) {
        return userInfoDao.getUserInfos(userInfoParam);
    }

}
