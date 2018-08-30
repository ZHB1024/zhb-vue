package com.zhb.vue.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zhb.vue.dao.FunctionInfoDao;
import com.zhb.vue.pojo.FunctionInfoData;

@Repository
public class FunctionInfoDaoImpl implements FunctionInfoDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    
    @Override
    public void saveOrUpdate(FunctionInfoData data) {
        sessionFactory.getCurrentSession().saveOrUpdate(data);
    }

}
