package com.zhb.vue.params;

import java.util.Calendar;

public class UserInfoParam {
    
    private String id;
    private String userName;
    private String realName;
    private String password;
    private String salt;
    private String sex;
    private String identityCard;
    private String country;
    private String nation;
    private String byyx;//毕业院校
    private String mobilePhone;
    private String email;
    private String lobId;
    private Calendar createTime;
    private Calendar updateTime;
    private Integer deleteFlag;
    
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getIdentityCard() {
        return identityCard;
    }
    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getNation() {
        return nation;
    }
    public void setNation(String nation) {
        this.nation = nation;
    }
    public String getByyx() {
        return byyx;
    }
    public void setByyx(String byyx) {
        this.byyx = byyx;
    }
    public String getMobilePhone() {
        return mobilePhone;
    }
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getLobId() {
        return lobId;
    }
    public void setLobId(String lobId) {
        this.lobId = lobId;
    }
    public Calendar getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }
    public Calendar getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Calendar updateTime) {
        this.updateTime = updateTime;
    }
    public Integer getDeleteFlag() {
        return deleteFlag;
    }
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
    
}
