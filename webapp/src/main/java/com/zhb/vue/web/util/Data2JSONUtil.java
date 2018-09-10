package com.zhb.vue.web.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhb.forever.framework.dic.DeleteFlagEnum;
import com.zhb.forever.framework.util.DateTimeUtil;
import com.zhb.forever.framework.vo.UserInfoVO;
import com.zhb.vue.pojo.DicInfoData;
import com.zhb.vue.pojo.FunctionInfoData;
import com.zhb.vue.pojo.IconInfoData;
import com.zhb.vue.pojo.UserFunctionInfoData;
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
    
    public static JSONArray dicInfoData2JSONArray(DicInfoData data) {
        if (null == data ) {
            return null;
        }
        JSONArray jsonArray = new JSONArray();
        JSONObject object = new JSONObject();
        object.put("id", data.getId());
        object.put("category", data.getCategory());
        object.put("code", data.getCode());
        object.put("name", data.getName());
        object.put("name2", data.getName2());
        object.put("name3", data.getName3());
        object.put("type", data.getType());
        object.put("orderIndex", data.getOrderIndex());
        object.put("deleteFlag", data.getDeleteFlag());
        object.put("remark", data.getRemark());
        jsonArray.add(object);
        return jsonArray;
    }
    
    public static JSONArray dicInfoDatas2JSONArray(List<DicInfoData> datas) {
        if (null == datas || datas.size() == 0) {
            return null;
        }
        JSONArray jsonArray = new JSONArray();
        for (DicInfoData data : datas) {
            JSONObject object = new JSONObject();
            object.put("id", data.getId());
            object.put("category", data.getCategory());
            object.put("code", data.getCode());
            object.put("name", data.getName());
            object.put("name2", data.getName2());
            object.put("name3", data.getName3());
            object.put("type", data.getType());
            object.put("orderIndex", data.getOrderIndex());
            object.put("deleteFlag", data.getDeleteFlag());
            object.put("remark", data.getRemark());
            jsonArray.add(object);
        }
        
        return jsonArray;
    }
    public static JSONArray iconInfoData2JSONArray(IconInfoData data) {
        if (null == data ) {
            return null;
        }
        JSONArray jsonArray = new JSONArray();
        JSONObject object = new JSONObject();
        object.put("id", data.getId());
        object.put("name", data.getName());
        object.put("value", data.getValue());
        object.put("deleteFlag", data.getDeleteFlag());
        jsonArray.add(object);
        return jsonArray;
    }
    
    public static JSONArray iconInfoDatas2JSONArray(List<IconInfoData> datas) {
        if (null == datas || datas.size() == 0) {
            return null;
        }
        JSONArray jsonArray = new JSONArray();
        for (IconInfoData data : datas) {
            JSONObject object = new JSONObject();
            object.put("id", data.getId());
            object.put("name", data.getName());
            object.put("value", data.getValue());
            object.put("deleteFlag", data.getDeleteFlag());
            jsonArray.add(object);
        }
        
        return jsonArray;
    }
    
    //功能
    public static JSONArray functionInfo2JSONArray(List<FunctionInfoData> datas) {
        JSONArray jsonArray = new JSONArray();
        Map<FunctionInfoData, List<FunctionInfoData>> map = null;
        if (null != datas && datas.size() >0) {
            map = new HashMap<FunctionInfoData, List<FunctionInfoData>>();
            for (FunctionInfoData data : datas) {
                if (!map.containsKey(data.getParentFunctionInfo())) {
                    List<FunctionInfoData> functionsList = new ArrayList<>();
                    functionsList.add(data);
                    map.put(data.getParentFunctionInfo(), functionsList);
                }else {
                    List<FunctionInfoData> functionsList = map.get(data.getParentFunctionInfo());
                    functionsList.add(data);
                    map.put(data.getParentFunctionInfo(), functionsList);
                }
            }
            
            for (Map.Entry<FunctionInfoData,List<FunctionInfoData>> object : map.entrySet()) {
                FunctionInfoData parent = object.getKey();
                List<FunctionInfoData> childrens = object.getValue();
                
                JSONArray jbjlChildrenMenu = new JSONArray();
                for(FunctionInfoData funData : childrens){
                    JSONObject json = new JSONObject();
                    json.put("id", funData.getId());
                    json.put("name", funData.getName());
                    json.put("path", funData.getPath());
                    json.put("icon", "");
                    json.put("orderIndex", funData.getOrder());
                    jbjlChildrenMenu.add(json);
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", parent.getId());
                jsonObject.put("name", parent.getName());
                jsonObject.put("path", parent.getPath());
                jsonObject.put("orderIndex", parent.getOrder());
                jsonObject.put("icon", parent.getIconInfoData().getValue());
                jsonObject.put("children", jbjlChildrenMenu);
                
                jsonArray.add(jsonObject);
            }
        }
        
        return jsonArray;
    }
    
    //左侧菜单
    public static JSONArray generateJSonArray(List<UserFunctionInfoData> datas) {
        JSONArray jsonArray = new JSONArray();
        Map<FunctionInfoData, List<FunctionInfoData>> map = null;
        if (null != datas && datas.size() >0) {
            map = new HashMap<FunctionInfoData, List<FunctionInfoData>>();
            for (UserFunctionInfoData data : datas) {
                FunctionInfoData functionInfo = data.getFunctionInfoData();
                if (!map.containsKey(functionInfo.getParentFunctionInfo())) {
                    List<FunctionInfoData> functionsList = new ArrayList<>();
                    functionsList.add(functionInfo);
                    map.put(functionInfo.getParentFunctionInfo(), functionsList);
                }else {
                    List<FunctionInfoData> functionsList = map.get(functionInfo.getParentFunctionInfo());
                    functionsList.add(functionInfo);
                    map.put(functionInfo.getParentFunctionInfo(), functionsList);
                }
            }
            
            for (Map.Entry<FunctionInfoData,List<FunctionInfoData>> object : map.entrySet()) {
                FunctionInfoData parent = object.getKey();
                List<FunctionInfoData> childrens = object.getValue();
                
                JSONArray jbjlChildrenMenu = new JSONArray();
                for(FunctionInfoData funData : childrens){
                    JSONObject json = new JSONObject();
                    json.put("id", funData.getId());
                    json.put("url", funData.getPath());
                    json.put("name", funData.getName());
                    jbjlChildrenMenu.add(json);
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", parent.getId());
                jsonObject.put("url", parent.getPath());
                jsonObject.put("name", parent.getName());
                jsonObject.put("icon", parent.getIconInfoData().getValue());
                jsonObject.put("children", jbjlChildrenMenu);
                
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }
    
    public static JSONObject userFunctionInfoData2JSONObject(UserFunctionInfoData data) {
        if (null == data) {
            return null;
        }
        JSONObject object = new JSONObject();
        object.put("id", data.getId());
        object.put("userName", data.getUserInfoData().getUserName());
        object.put("realName", data.getUserInfoData().getRealName());
        object.put("functionName", data.getFunctionInfoData().getName());
        return object;
    }
    
    public static JSONArray userFunctionInfoDatas2JSONObject(List<UserFunctionInfoData> datas) {
        JSONArray jsonArray = null;
        if (null != datas && datas.size() >0) {
            jsonArray = new JSONArray();
            for (UserFunctionInfoData data : datas) {
                JSONObject object = new JSONObject();
                object.put("id", data.getId());
                object.put("userName", data.getUserInfoData().getUserName());
                object.put("realName", data.getUserInfoData().getRealName());
                object.put("functionName", data.getFunctionInfoData().getName());
                jsonArray.add(object);
            }
        }
        return jsonArray;
    }

}
