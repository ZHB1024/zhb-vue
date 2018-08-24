package com.zhb.vue.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zhb.forever.framework.util.StringUtil;
import com.zhb.vue.dao.UserInfoDao;
import com.zhb.vue.params.UserInfoParam;
import com.zhb.vue.pojo.UserInfoData;

@Repository
public class UserInfoDaoImpl implements UserInfoDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public void saveOrUpdate(UserInfoData data) {
        sessionFactory.getCurrentSession().saveOrUpdate(data);
    }
    
    @Override
    public List<UserInfoData> getUserInfos(UserInfoParam param){
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserInfoData> cq = cb.createQuery(UserInfoData.class);
        Root<UserInfoData> root = cq.from(UserInfoData.class);
        if (StringUtil.isNotBlank(param.getId())) {
            cq.where(cb.equal(root.get("id"), param.getId()));
        }
        
        Query<UserInfoData> query = session.createQuery(cq);
        return query.list();
    }

}
