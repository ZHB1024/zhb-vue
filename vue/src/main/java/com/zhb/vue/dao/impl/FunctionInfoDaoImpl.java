package com.zhb.vue.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zhb.forever.framework.dic.DeleteFlagEnum;
import com.zhb.forever.framework.util.StringUtil;
import com.zhb.vue.dao.FunctionInfoDao;
import com.zhb.vue.params.FunctionInfoParam;
import com.zhb.vue.pojo.FunctionInfoData;

@Repository
public class FunctionInfoDaoImpl implements FunctionInfoDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    
    @Override
    public void saveOrUpdate(FunctionInfoData data) {
        sessionFactory.getCurrentSession().saveOrUpdate(data);
    }
    
    @Override
    public void delFunctionInfoData(FunctionInfoData data) {
        sessionFactory.getCurrentSession().delete(data);
    }

    @Override
    public List<FunctionInfoData> getFunctions(FunctionInfoParam param) {
        if (null == param) {
            return null;
        }
        Session session = sessionFactory.getCurrentSession();
        
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<FunctionInfoData> criteriaQuery = criteriaBuilder.createQuery(FunctionInfoData.class);
        Root<FunctionInfoData> root = criteriaQuery.from(FunctionInfoData.class);
        
        List<Predicate> conditions = new ArrayList<>();
        
        if (StringUtil.isNotBlank(param.getId())) {
            conditions.add(criteriaBuilder.equal(root.get("id"), param.getId()));
        }
        if (StringUtil.isNotBlank(param.getName())) {
            conditions.add(criteriaBuilder.equal(root.get("name"), param.getName()));
        }
        
        if (null != param.getType()) {
            conditions.add(criteriaBuilder.equal(root.get("type"), param.getType()));
        }
        
        if (StringUtil.isNotBlank(param.getPath())) {
            conditions.add(criteriaBuilder.equal(root.get("path"), param.getPath()));
        }
        
        if (null != param.getIconInfoData()) {
            conditions.add(criteriaBuilder.equal(root.get("iconInfoData"), param.getIconInfoData()));
        }
        
        if (null != param.getParentFunctionInfo()) {
            conditions.add(criteriaBuilder.equal(root.get("parentFunctionInfo"), param.getParentFunctionInfo()));
        }
        
        if (null != param.getDeleteFlag()) {
            conditions.add(criteriaBuilder.equal(root.get("deleteFlag"), param.getDeleteFlag()));
        }else {
            conditions.add(criteriaBuilder.equal(root.get("deleteFlag"), DeleteFlagEnum.UDEL.getIndex()));
        }
        
        if (conditions.size() > 0 ) {
            criteriaQuery.where(criteriaBuilder.and(conditions.toArray(new Predicate[conditions.size()])));
        }
        
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("order")));
        
        Query<FunctionInfoData> query = session.createQuery(criteriaQuery);
        return query.list();
    }


    @Override
    public int getMaxOrder() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class);
        
        Root<FunctionInfoData> root = criteriaQuery.from(FunctionInfoData.class);
        //criteriaBuilder.max(root.get("order"));
        
        criteriaQuery.select(criteriaBuilder.max(root.get("order")));
        
        return session.createQuery(criteriaQuery).getSingleResult();
    }

}
