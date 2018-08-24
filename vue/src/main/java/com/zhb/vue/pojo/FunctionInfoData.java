package com.zhb.vue.pojo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="function_info")
public class FunctionInfoData {
    
    private String id;
    private String name;
    private Short type;
    private String path;
    private Integer order;
    private IconInfoData iconInfoData;
    private FunctionInfoData parentFunctionInfo;
    private Integer deleteFlag;
    
    private List<FunctionInfoData> childFunctionInfos;
    
    @Id
    @GeneratedValue(generator = "app_seq")
    @GenericGenerator(name = "app_seq", strategy = "com.zhb.vue.pojo.strategy.StringRandomGenerator")
    @Column(name = "ID")
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    @Column(name = "name",nullable = false, length = 20)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name = "type",nullable = false)
    public Short getType() {
        return type;
    }
    public void setType(Short type) {
        this.type = type;
    }
    
    @Column(name = "path",nullable = false, length = 100)
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    
    @Column(name = "order_index",nullable = false)
    public Integer getOrder() {
        return order;
    }
    public void setOrder(Integer order) {
        this.order = order;
    }
    
    @JoinColumn(name = "icon_id",nullable = true)
    @OneToOne(fetch = FetchType.LAZY)
    public IconInfoData getIconInfoData() {
        return iconInfoData;
    }
    public void setIconInfoData(IconInfoData iconInfoData) {
        this.iconInfoData = iconInfoData;
    }
    
    @JoinColumn(name = "parent_id")
    /*@ManyToOne(cascade=CascadeType.REFRESH,optional=true,fetch = FetchType.LAZY)*/
    @ManyToOne(fetch = FetchType.LAZY)
    public FunctionInfoData getParentFunctionInfo() {
        return parentFunctionInfo;
    }
    public void setParentFunctionInfo(FunctionInfoData parentFunctionInfo) {
        this.parentFunctionInfo = parentFunctionInfo;
    }
    
    @Column(name = "delete_flag",nullable = false)
    public Integer getDeleteFlag() {
        return deleteFlag;
    }
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
    
    @OneToMany(fetch = FetchType.EAGER,mappedBy="parentFunctionInfo")
    @OrderBy(value = "order ASC")
    @Fetch(FetchMode.SUBSELECT)
    public List<FunctionInfoData> getChildFunctionInfos() {
        return childFunctionInfos;
    }
    public void setChildFunctionInfos(List<FunctionInfoData> childFunctionInfos) {
        this.childFunctionInfos = childFunctionInfos;
    }
    
}
