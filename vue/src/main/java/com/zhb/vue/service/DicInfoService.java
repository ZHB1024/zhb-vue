package com.zhb.vue.service;

import java.util.List;

import com.zhb.forever.framework.page.Page;
import com.zhb.forever.framework.vo.OrderVO;
import com.zhb.vue.params.DicInfoParam;
import com.zhb.vue.pojo.DicInfoData;

public interface DicInfoService {
    
    void saveOrUpdate(DicInfoData data);
    
    void saveOrUpdate(List<DicInfoData> datas);
    
    List<DicInfoData> getDicInfos(DicInfoParam param,List<OrderVO> orderVos);
    
    Page<DicInfoData> getDicInfosPage(DicInfoParam param,List<OrderVO> orderVos);
    
    List<String> getDicCategory();
    
    List<String> getDicTypeByCategory(DicInfoParam param);

}
