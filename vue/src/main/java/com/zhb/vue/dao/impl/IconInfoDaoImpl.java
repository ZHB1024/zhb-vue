package com.zhb.vue.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zhb.vue.dao.IconInfoDao;
import com.zhb.vue.pojo.IconInfoData;

@Repository
public class IconInfoDaoImpl implements IconInfoDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    
    @Override
    public void saveOrUpdate(IconInfoData data) {
        sessionFactory.getCurrentSession().saveOrUpdate(data);
    }

}
