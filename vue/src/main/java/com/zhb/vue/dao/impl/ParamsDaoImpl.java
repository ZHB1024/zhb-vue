package com.zhb.vue.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zhb.vue.dao.ParamsDao;
import com.zhb.vue.pojo.ParamsData;

@Repository
public class ParamsDaoImpl implements ParamsDao{
    
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<ParamsData> getParams() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<ParamsData> cq = cb.createQuery(ParamsData.class);
        cq.from(ParamsData.class);
        Query query = session.createQuery(cq);
        return query.list();
    }

}
