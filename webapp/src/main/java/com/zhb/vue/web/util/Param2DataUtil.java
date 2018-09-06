package com.zhb.vue.web.util;

import com.zhb.vue.params.UserInfoParam;
import com.zhb.vue.pojo.UserInfoData;

public class Param2DataUtil {
    
    
    public static void userInfoParam2Data(UserInfoParam param,UserInfoData data) {
        if (null == param || null == data) {
            return ;
        }
        data.setUserName(param.getUserName());
        data.setRealName(param.getRealName());
        data.setSex(param.getSex());
        data.setBirthday(param.getBirthday());
        data.setIdentityCard(param.getIdentityCard());
        data.setCountry(param.getCountry());
        data.setNation(param.getNation());
        data.setByyx(param.getByyx());
        data.setMobilePhone(param.getMobilePhone());
        data.setEmail(param.getEmail());
    }

}
