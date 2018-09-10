package com.zhb.vue.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.zhb.vue.base.BaseTest;
import com.zhb.vue.params.IconInfoParam;
import com.zhb.vue.pojo.IconInfoData;

public class IconInfoServiceTest extends BaseTest {
    
    @Autowired
    private IconInfoService iconInfoService;
    
    @Test
    @Transactional
    @Rollback(true)
    public void saveOrUpdateTest() {
        IconInfoData data = new IconInfoData();
        data.setName("功能");
        data.setValue("<Icon type=\"ios-construct\" />");
        iconInfoService.saveOrUpdate(data);
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void getIconInfoTest() {
        IconInfoParam param = new IconInfoParam();
        List<IconInfoData> datas = iconInfoService.getIconInfos(param);
        if (null != datas) {
            for (IconInfoData iconInfoData : datas) {
                System.out.println(iconInfoData.getName() + "--" + iconInfoData.getValue());
            }
        }
    }

}
