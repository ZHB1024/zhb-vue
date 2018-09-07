package com.zhb.vue.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhb.vue.dao.DicInfoDao;
import com.zhb.vue.params.DicInfoParam;
import com.zhb.vue.pojo.DicInfoData;
import com.zhb.vue.service.DicInfoService;

@Service
public class DicInfoServiceImpl implements DicInfoService {
    
    @Autowired
    private DicInfoDao dicInfoDao;

    @Override
    public void saveOrUpdate(DicInfoData data) {
        dicInfoDao.saveOrUpdate(data);
    }
    
    @Override
    public void saveOrUpdate(List<DicInfoData> datas) {
        if (null == datas || datas.size() == 0) {
            return;
        }
        
        for (DicInfoData dicInfoData : datas) {
            dicInfoDao.saveOrUpdate(dicInfoData);
        }
    }

    @Override
    public List<DicInfoData> getDicInfos(DicInfoParam param) {
        return dicInfoDao.getDicInfos(param);
    }

}
