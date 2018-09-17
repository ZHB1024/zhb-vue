package com.zhb.vue.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhb.forever.framework.page.Page;
import com.zhb.forever.framework.page.PageUtil;
import com.zhb.forever.framework.vo.OrderVO;
import com.zhb.vue.dao.FunctionInfoDao;
import com.zhb.vue.dao.UserFunctionInfoDao;
import com.zhb.vue.params.FunctionInfoParam;
import com.zhb.vue.params.UserFunctionInfoParam;
import com.zhb.vue.pojo.DicInfoData;
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
    public void delFunctionInfoData(FunctionInfoData data) {
        functionInfoDao.delFunctionInfoData(data);
    }

    @Override
    public void delUserFunctionInfoData(UserFunctionInfoData data) {
        userFunctionInfoDao.delUserFunctionInfoData(data);
    }

    @Override
    public List<FunctionInfoData> getFunctions(FunctionInfoParam param,List<OrderVO> orderVos) {
        return functionInfoDao.getFunctions(param,orderVos);
    }
    
    @Override
    public FunctionInfoData getFunctionById(String id) {
        return functionInfoDao.getFunctionById(id);
    }
    
    @Override
    public int getMaxOrder(FunctionInfoParam param) {
        return functionInfoDao.getMaxOrder(param);
    }

    @Override
    public List<UserFunctionInfoData> getDataByUser(UserInfoData data) {
        return userFunctionInfoDao.getDataByUser(data);
    }
    
    @Override
    public List<UserFunctionInfoData> getUserFunctionInfoDatas(UserFunctionInfoParam param) {
        return userFunctionInfoDao.getUserFunctionInfoDatas(param);
    }

    @Override
    public Page<UserFunctionInfoData> getUserFunctionInfoDatasPage(UserFunctionInfoParam param) {
        long total = userFunctionInfoDao.countUserFunctionInfos(param);
        if (total > 0 ) {
            List<UserFunctionInfoData> userFunctionInfoDatas = userFunctionInfoDao.getUserFunctionInfoDatasPage(param);
            //上一页可能有数据
            if ((null == userFunctionInfoDatas || userFunctionInfoDatas.size() == 0) && param.getCurrentPage() > 1) {
                param.setStart(param.getPageSize() * (param.getCurrentPage()-2));
                userFunctionInfoDatas = userFunctionInfoDao.getUserFunctionInfoDatasPage(param);
            }
            Page<UserFunctionInfoData> page = PageUtil.getPage(userFunctionInfoDatas.iterator(), param.getStart(), param.getPageSize(), total);
            return page;
        }
        return null;
    }

}
