package com.zhb.vue.service.base.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhb.vue.dao.base.CommonDao;
import com.zhb.vue.service.base.CommonService;

/**

*@author   zhanghb

*date 2018年10月21日下午1:33:23

*/

@Service
public class CommonServiceImpl implements CommonService{

    private Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);
    
    @Autowired
    public CommonDao commonDao;
    
    /**
     * *新增实体
     * @param <T>
     * @param entitie
     */
    public  <T> Serializable save(T entity) {
        return commonDao.save(entity);
    }
    
    /**
     * *新增或修改实体
     * @param <T>
     * @param entitie
     */
    public  <T> T saveOrUpdate(T entity) {
        return commonDao.saveOrUpdate(entity);
    };

    /**
     * *批量新增实体
     * @param <T>
     * @param entitie
     */
    public  <T> void batchSave(List<T> entitys) {
        commonDao.batchSave(entitys);
    };
    
    /**
     * *删除实体
     * @param <T>
     * @param entitie
     */
    public  <T> void delete(T entity) {
        commonDao.delete(entity);
    };
    
    /**
     * *根据主键删除指定的实体
     * @param <T>
     * @param entitie
     */
    public  <T> void deleteEntityById(Class entityName, Serializable id) {
        commonDao.deleteEntityById(entityName,id);
    };
    
    /**
     * *删除全部的实体
     * 
     * @param <T>
     * 
     * @param entitys
     */
    public  <T> void deleteAllEntitie(Collection<T> entities) {
        commonDao.deleteAllEntitie(entities);
    };
    
    /**
     * *根据实体名称和主键获取实体
     * @param <T>
     * @param entityName
     * @param id
     * @return
     */
    public  <T> T get(Class<T> entityClass, Serializable id) {
        return commonDao.get(entityClass, id);
    };

    /**
     * *根据实体名称和主键获取实体
     * @param <T>
     * @param entityName
     * @param id
     * @return
     */
    public  <T> T getEntity(Class entityName, Serializable id) {
        return commonDao.getEntity(entityName, id);
    };

}


