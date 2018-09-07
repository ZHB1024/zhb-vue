package com.zhb.vue.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zhb.forever.framework.dic.DeleteFlagEnum;
import com.zhb.forever.framework.util.StringUtil;
import com.zhb.vue.dao.DicInfoDao;
import com.zhb.vue.params.DicInfoParam;
import com.zhb.vue.pojo.DicInfoData;

@Repository
public class DicInfoDaoImpl implements DicInfoDao {
    
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveOrUpdate(DicInfoData data) {
        sessionFactory.getCurrentSession().saveOrUpdate(data);
    }
    
    @Override
    public List<DicInfoData> getDicInfos(DicInfoParam param) {
        if (null == param) {
            return null;
        }
        Session session = sessionFactory.getCurrentSession();
        //Criteria criteria = session.createCriteria(DicInfoData.class);
        
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<DicInfoData> criteriaQuery = criteriaBuilder.createQuery(DicInfoData.class);
        Root<DicInfoData> root = criteriaQuery.from(DicInfoData.class);
        
        if (StringUtil.isNotBlank(param.getId())) {
            criteriaQuery.where(criteriaBuilder.equal(root.get("id"), param.getId()));
        }
        if (StringUtil.isNotBlank(param.getCategory())) {
            criteriaQuery.where(criteriaBuilder.equal(root.get("category"), param.getCategory()));
        }
        
        if (StringUtil.isNotBlank(param.getCode())) {
            criteriaQuery.where(criteriaBuilder.equal(root.get("code"), param.getCode()));
        }
        if (StringUtil.isNotBlank(param.getName())) {
            criteriaQuery.where(criteriaBuilder.equal(root.get("name"), param.getName()));
        }
        if (StringUtil.isNotBlank(param.getName2())) {
            criteriaQuery.where(criteriaBuilder.equal(root.get("name2"), param.getName2()));
        }
        if (StringUtil.isNotBlank(param.getName3())) {
            criteriaQuery.where(criteriaBuilder.equal(root.get("name3"), param.getName3()));
        }
        if (StringUtil.isNotBlank(param.getType())) {
            criteriaQuery.where(criteriaBuilder.equal(root.get("type"), param.getType()));
        }
        if (null != param.getDeleteFlag()) {
            criteriaQuery.where(criteriaBuilder.equal(root.get("deleteFlag"), param.getDeleteFlag()));
        }else {
            criteriaQuery.where(criteriaBuilder.equal(root.get("deleteFlag"), DeleteFlagEnum.UDEL.getIndex()));
        }
        
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("category"))).orderBy(criteriaBuilder.asc(root.get("orderIndex")));
        
        Query<DicInfoData> query = session.createQuery(criteriaQuery);
        return query.list();
    }


}
