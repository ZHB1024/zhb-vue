package com.zhb.vue.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhb.vue.dao.IconInfoDao;
import com.zhb.vue.params.IconInfoParam;
import com.zhb.vue.pojo.IconInfoData;
import com.zhb.vue.service.IconInfoService;

@Service
public class IconInfoServiceImpl implements IconInfoService {
    
    @Autowired
    private IconInfoDao iconInfoDao;

    @Override
    public void saveOrUpdate(IconInfoData data) {
        iconInfoDao.saveOrUpdate(data);
    }

    @Override
    public List<IconInfoData> getIconInfos(IconInfoParam param) {
        return iconInfoDao.getIconInfos(param);
    }

}
