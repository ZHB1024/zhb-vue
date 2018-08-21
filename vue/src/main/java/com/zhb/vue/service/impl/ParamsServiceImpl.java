package com.zhb.vue.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhb.vue.dao.ParamsDao;
import com.zhb.vue.pojo.ParamsData;
import com.zhb.vue.service.ParamsService;

@Service
public class ParamsServiceImpl implements ParamsService{
    
    @Autowired
    private ParamsDao paramsDao;

    @Override
    public List<ParamsData> getParams() {
        return paramsDao.getParams();
    }

}
