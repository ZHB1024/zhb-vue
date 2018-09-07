package com.zhb.vue.service;

import java.util.List;

import com.zhb.vue.params.DicInfoParam;
import com.zhb.vue.pojo.DicInfoData;

public interface DicInfoService {
    
    void saveOrUpdate(DicInfoData data);
    
    void saveOrUpdate(List<DicInfoData> datas);
    
    List<DicInfoData> getDicInfos(DicInfoParam param);

}
