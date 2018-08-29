package com.zhb.vue.web.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 作为Ajax返回信息封装对象
 * 
 * @author zhanghuibin
 * 
 */
public class AjaxData {
    
    /**
     * 判断执行结果的标识
     */
    private Boolean flag;

    /**
     * 提示信息。
     */
    private List<String> msg;

    /**
     * 放置任何页面需要的对象
     */
    private Object data;

    public void addMessage(String message) {
        if (null ==  msg) {
            msg = new ArrayList<String>();
        }
        msg.add(message);
    }
    public Boolean getFlag() {
        return flag;
    }
    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
    public List<String> getErrorMessages() {
        return null == msg ? new ArrayList<String>() : msg;
    }
    public void setErrorMessages(List<String> errorMessages) {
        this.msg = errorMessages;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }

}
