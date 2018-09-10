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

import com.zhb.forever.framework.dic.DeleteFlagEnum;
import com.zhb.forever.framework.util.StringUtil;
import com.zhb.vue.dao.IconInfoDao;
import com.zhb.vue.params.IconInfoParam;
import com.zhb.vue.pojo.IconInfoData;

@Repository
public class IconInfoDaoImpl implements IconInfoDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    
    @Override
    public void saveOrUpdate(IconInfoData data) {
        sessionFactory.getCurrentSession().saveOrUpdate(data);
    }


    @Override
    public List<IconInfoData> getIconInfos(IconInfoParam param) {
        if (null == param) {
            return null;
        }
        
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<IconInfoData> criteriaQuery = criteriaBuilder.createQuery(IconInfoData.class);
        Root<IconInfoData> root = criteriaQuery.from(IconInfoData.class);
        
        List<Predicate> conditions = new ArrayList<>();
        if (StringUtil.isNotBlank(param.getId())) {
            conditions.add(criteriaBuilder.equal(root.get("id"), param.getId()));
        }
        if (StringUtil.isNotBlank(param.getName())) {
            conditions.add(criteriaBuilder.equal(root.get("name"), param.getName()));
        }
        if (StringUtil.isNotBlank(param.getValue())) {
            conditions.add(criteriaBuilder.equal(root.get("value"), param.getValue()));
        }
        if (null != param.getDeleteFlag()) {
            conditions.add(criteriaBuilder.equal(root.get("deleteFlag"), param.getDeleteFlag()));
        }else {
            conditions.add(criteriaBuilder.equal(root.get("deleteFlag"), DeleteFlagEnum.UDEL.getIndex()));
        }
        
        if (conditions.size() > 0) {
            criteriaQuery.where(conditions.toArray(new Predicate[conditions.size()]));
        }
        Query<IconInfoData> query = session.createQuery(criteriaQuery);
        
        return query.getResultList();
    }

}
