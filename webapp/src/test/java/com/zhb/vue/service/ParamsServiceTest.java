package com.zhb.vue.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.zhb.vue.base.BaseTest;
import com.zhb.vue.pojo.ParamsData;

public class ParamsServiceTest extends BaseTest{
    
    @Autowired
    private ParamsService paramsService;
    
    @Test
    @Transactional
    @Rollback(true)
    public void getParamsTest() {
        List<ParamsData> datas = paramsService.getParams();
        if (null != datas) {
            for (ParamsData paramsData : datas) {
                System.out.println(paramsData.getName());
            }
        }
    }

}
