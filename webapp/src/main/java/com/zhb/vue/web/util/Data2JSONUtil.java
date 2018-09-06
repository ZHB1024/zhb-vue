package com.zhb.vue.web.util;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhb.forever.framework.dic.DeleteFlagEnum;
import com.zhb.forever.framework.util.DateTimeUtil;
import com.zhb.forever.framework.vo.UserInfoVO;
import com.zhb.vue.pojo.UserInfoData;

public class Data2JSONUtil {
    
    public static JSONObject userInfoData2JSONObject(UserInfoData data) {
        if (null == data) {
            return null;
        }
        JSONObject object = new JSONObject();
        object.put("id", data.getId());
        object.put("userName", data.getUserName());
        object.put("realName", data.getRealName());
        object.put("identityCard", data.getIdentityCard());
        object.put("sex", data.getSex());
        object.put("birthday", DateTimeUtil.getDateTime(data.getBirthday(),"yyyy-MM-dd"));
        object.put("lobId", data.getLobId());
        object.put("mobilePhone", data.getMobilePhone());
        object.put("byyx", data.getByyx());
        object.put("country", data.getCountry());
        object.put("nation", data.getNation());
        object.put("email", data.getEmail());
        object.put("createTime", data.getCreateTime());
        object.put("updateTime", data.getUpdateTime());
        object.put("deleteFlag", DeleteFlagEnum.getName(data.getDeleteFlag()));
        return object;
    }
    
    public static JSONArray userInfoDatas2JSONArray(List<UserInfoData> datas) {
        JSONArray jsonArray = null;
        if (null != datas && datas.size() >0) {
            jsonArray = new JSONArray();
            for (UserInfoData data : datas) {
                JSONObject object = new JSONObject();
                object.put("id", data.getId());
                object.put("userName", data.getUserName());
                object.put("realName", data.getRealName());
                object.put("identityCard", data.getIdentityCard());
                object.put("sex", data.getSex());
                object.put("birthday", DateTimeUtil.getDateTime(data.getBirthday(),"yyyy-MM-dd"));
                object.put("lobId", data.getLobId());
                object.put("mobilePhone", data.getMobilePhone());
                object.put("byyx", data.getByyx());
                object.put("country", data.getCountry());
                object.put("nation", data.getNation());
                object.put("email", data.getEmail());
                object.put("createTime", data.getCreateTime());
                object.put("updateTime", data.getUpdateTime());
                object.put("deleteFlag", DeleteFlagEnum.getName(data.getDeleteFlag()));
                jsonArray.add(object);
            }
        }
        return jsonArray;
    }
    
    
    
    public static JSONObject userInfoVO2JSONObject(UserInfoVO data) {
        if (null == data) {
            return null;
        }
        JSONObject object = new JSONObject();
        object.put("id", data.getId());
        object.put("userName", data.getUserName());
        object.put("realName", data.getRealName());
        object.put("identityCard", data.getIdentityCard());
        object.put("sex", data.getSex());
        object.put("birthday", DateTimeUtil.getDateTime(data.getBirthday(),"yyyy-MM-dd"));
        object.put("lobId", data.getLobId());
        object.put("mobilePhone", data.getMobilePhone());
        object.put("byyx", data.getByyx());
        object.put("country", data.getCountry());
        object.put("nation", data.getNation());
        object.put("email", data.getEmail());
        object.put("createTime", data.getCreateTime());
        object.put("updateTime", data.getUpdateTime());
        object.put("deleteFlag", DeleteFlagEnum.getName(data.getDeleteFlag()));
        
        return object;
    }
    
    public static JSONArray userInfoVOs2JSONArray(List<UserInfoVO> datas) {
        JSONArray jsonArray = null;
        if (null != datas && datas.size() >0) {
            jsonArray = new JSONArray();
            for (UserInfoVO data : datas) {
                JSONObject object = new JSONObject();
                object.put("id", data.getId());
                object.put("userName", data.getUserName());
                object.put("realName", data.getRealName());
                object.put("identityCard", data.getIdentityCard());
                object.put("sex", data.getSex());
                object.put("birthday", DateTimeUtil.getDateTime(data.getBirthday(),"yyyy-MM-dd"));
                object.put("lobId", data.getLobId());
                object.put("mobilePhone", data.getMobilePhone());
                object.put("byyx", data.getByyx());
                object.put("country", data.getCountry());
                object.put("nation", data.getNation());
                object.put("email", data.getEmail());
                object.put("createTime", data.getCreateTime());
                object.put("updateTime", data.getUpdateTime());
                object.put("deleteFlag", DeleteFlagEnum.getName(data.getDeleteFlag()));
                jsonArray.add(object);
            }
        }
        
        return jsonArray;
    }

}
