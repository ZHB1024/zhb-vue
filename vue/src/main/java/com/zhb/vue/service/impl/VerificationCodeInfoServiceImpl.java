package com.zhb.vue.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(readOnly=true)
    public List<VerificationCodeInfoData> getVerificationCodes(VerificationCodeInfoParam param,
            List<OrderVO> orderVos) {
        return verificationCodeInfoDao.getVerificationCodes(param, orderVos);
    }

    @Override
    @Transactional
    public void delete(List<VerificationCodeInfoData> datas) {
        if (null != datas && datas.size() > 0) {
            for (VerificationCodeInfoData verificationCodeInfoData : datas) {
                verificationCodeInfoDao.delete(verificationCodeInfoData);
            }
        }
    }
    
    
}
