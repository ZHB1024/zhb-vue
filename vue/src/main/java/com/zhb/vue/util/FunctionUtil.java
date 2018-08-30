package com.zhb.vue.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhb.vue.pojo.FunctionInfoData;
import com.zhb.vue.pojo.UserFunctionInfoData;

public class FunctionUtil {
    
    
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

}
