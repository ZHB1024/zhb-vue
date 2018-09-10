package com.zhb.vue.service;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.zhb.forever.framework.dic.DeleteFlagEnum;
import com.zhb.vue.base.BaseTest;
import com.zhb.vue.params.FunctionInfoParam;
import com.zhb.vue.params.IconInfoParam;
import com.zhb.vue.params.UserInfoParam;
import com.zhb.vue.pojo.FunctionInfoData;
import com.zhb.vue.pojo.IconInfoData;
import com.zhb.vue.pojo.UserFunctionInfoData;
import com.zhb.vue.pojo.UserInfoData;

public class FunctionInfoServiceTest extends BaseTest {
    
    private Logger logger = LoggerFactory.getLogger(FunctionInfoServiceTest.class);

    @Autowired
    private FunctionInfoService functionInfoService;
    
    @Autowired
    private UserInfoService userInfoService;
    
    @Autowired
    private IconInfoService iconInfoService;
    
    @Test
    @Transactional
    @Rollback(true)
    public void saveFunctionsTest() {
        FunctionInfoData data = new FunctionInfoData();
        data.setName("授权信息");
        data.setType(1);
        data.setPath("/htgl/authoritycontroller/toindex");
        data.setOrder(32);
        
        //设置父节点
        FunctionInfoParam param = new FunctionInfoParam();
        param.setId("sp9mdx3sn9rgxslt");
        List<FunctionInfoData> datas  = functionInfoService.getFunctions(param);
        data.setParentFunctionInfo(datas.get(0));
        
        //设置图标
        /*IconInfoParam param = new IconInfoParam();
        param.setId("azxt39kmmgb5bcj5");
        List<IconInfoData> datas = iconInfoService.getIconInfos(param);
        
        data.setIconInfoData(datas.get(0));*/
        
        functionInfoService.saveOrUpdate(data);
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void getMaxOrderTest() {
        int max = functionInfoService.getMaxOrder();
        System.out.println(max);
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void getFunctionsTest() {
        FunctionInfoParam param = new FunctionInfoParam();
        param.setName("个人信息");
        param.setDeleteFlag(DeleteFlagEnum.DEL.getIndex());
        List<FunctionInfoData> datas  = functionInfoService.getFunctions(param);
        if (null != datas && datas.size() > 0) {
            for (FunctionInfoData functionInfoData : datas) {
                System.out.println(functionInfoData.getName()+ "---" + functionInfoData.getPath())  ;
            }
        }else {
            System.out.println("结果是空-------");
            logger.info("结果是空-------");
        }
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void saveUserFunctionsTest() {
        UserFunctionInfoData data = new UserFunctionInfoData();
        
        FunctionInfoParam param = new FunctionInfoParam();
        param.setName("授权信息");
        List<FunctionInfoData> datas = functionInfoService.getFunctions(param);
        
        data.setFunctionInfoData(datas.get(0));
        
        UserInfoParam param2 = new UserInfoParam();
        param2.setUserName("root");
        List<UserInfoData> datas2 = userInfoService.getUserInfos(param2);
        
        data.setUserInfoData(datas2.get(0));
        
        functionInfoService.saveOrUpdate(data);
    }
    
    

}
