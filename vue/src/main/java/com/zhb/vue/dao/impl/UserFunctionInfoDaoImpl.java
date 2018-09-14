package com.zhb.vue.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zhb.vue.dao.UserFunctionInfoDao;
import com.zhb.vue.params.UserFunctionInfoParam;
import com.zhb.vue.pojo.UserFunctionInfoData;
import com.zhb.vue.pojo.UserInfoData;

@Repository
public class UserFunctionInfoDaoImpl implements UserFunctionInfoDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public void saveOrUpdate(UserFunctionInfoData data) {
        sessionFactory.getCurrentSession().saveOrUpdate(data);
    }
    
    @Override
    public void delUserFunctionInfoData(UserFunctionInfoData data) {
        sessionFactory.getCurrentSession().delete(data);
    }

    @Override
    public List<UserFunctionInfoData> getDataByUser(UserInfoData data) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<UserFunctionInfoData> criteriaQuery = criteriaBuilder.createQuery(UserFunctionInfoData.class);
        Root<UserFunctionInfoData> root = criteriaQuery.from(UserFunctionInfoData.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("userInfoData"), data));
        //criteriaQuery.orderBy(criteriaBuilder.asc(root.get("functionInfoData")));
        Query<UserFunctionInfoData> query = session.createQuery(criteriaQuery);
        return query.list();
    }

    @Override
    public List<UserFunctionInfoData> getUserFunctionInfoDatas(UserFunctionInfoParam param) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<UserFunctionInfoData> criteriaQuery = criteriaBuilder.createQuery(UserFunctionInfoData.class);
        Root<UserFunctionInfoData> root = criteriaQuery.from(UserFunctionInfoData.class);
        
        List<Predicate> conditions = new ArrayList<>();
        if (null != param.getUserInfoData()) {
            conditions.add(criteriaBuilder.equal(root.get("userInfoData"), param.getUserInfoData()));
        }
        if (null != param.getFunctionInfoData()) {
            conditions.add(criteriaBuilder.equal(root.get("functionInfoData"), param.getFunctionInfoData()));
        }
        if (conditions.size() > 0) {
            criteriaQuery.where(conditions.toArray(new Predicate[conditions.size()]));
        }
        
        Query<UserFunctionInfoData> query = session.createQuery(criteriaQuery);
        return query.list();
    }

}
