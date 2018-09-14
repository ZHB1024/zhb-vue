package com.zhb.vue.dao;

import java.util.List;

import com.zhb.forever.framework.vo.OrderVO;
import com.zhb.vue.params.DicInfoParam;
import com.zhb.vue.pojo.DicInfoData;

public interface DicInfoDao {
    
    
    void saveOrUpdate(DicInfoData data);
    
    List<DicInfoData> getDicInfos(DicInfoParam param,List<OrderVO> orderVos);
    
    Long getDicInfosPageCount(DicInfoParam param);

    List<DicInfoData> getDicInfosPage(DicInfoParam param,List<OrderVO> orderVos);

}
