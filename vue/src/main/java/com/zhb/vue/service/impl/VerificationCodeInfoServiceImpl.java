package com.zhb.vue.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhb.forever.framework.vo.OrderVO;
import com.zhb.vue.dao.VerificationCodeInfoDao;
import com.zhb.vue.params.VerificationCodeInfoParam;
import com.zhb.vue.pojo.VerificationCodeInfoData;
import com.zhb.vue.service.VerificationCodeInfoService;

@Service
public class VerificationCodeInfoServiceImpl implements VerificationCodeInfoService {

    @Autowired
    private VerificationCodeInfoDao verificationCodeInfoDao;
    
    @Override
    public void saveOrUpdate(VerificationCodeInfoData data) {
        verificationCodeInfoDao.saveOrUpdate(data);
    }

    @Override
    public List<VerificationCodeInfoData> getVerificationCodes(VerificationCodeInfoParam param,
            List<OrderVO> orderVos) {
        return verificationCodeInfoDao.getVerificationCodes(param, orderVos);
    }
    
    
}
