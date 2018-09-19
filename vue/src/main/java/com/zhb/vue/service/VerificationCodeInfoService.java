package com.zhb.vue.service;

import java.util.List;

import com.zhb.forever.framework.page.Page;
import com.zhb.forever.framework.vo.OrderVO;
import com.zhb.vue.params.VerificationCodeInfoParam;
import com.zhb.vue.pojo.VerificationCodeInfoData;

public interface VerificationCodeInfoService {
    
    void saveOrUpdate(VerificationCodeInfoData data);
    
    List<VerificationCodeInfoData> getVerificationCodes(VerificationCodeInfoParam param,List<OrderVO> orderVos);
    
    Page<VerificationCodeInfoData> getVerificationCodeInfosPage(VerificationCodeInfoParam param,List<OrderVO> orderVos);
    
    void delete(List<VerificationCodeInfoData> datas);

}
