package com.harvey.module.core.dao;

import com.harvey.common.base.BaseHibernateDao;
import com.harvey.module.core.domain.SysIndexQueue;
import com.harvey.module.core.domain.code.SysIndexObjectTypeCodeEnum;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/8/27 0027.
 */
@Repository
public class SysIndexQueueDao extends BaseHibernateDao<SysIndexQueue, Integer> {

    //获取某个类型的前limit条记录
    public List<SysIndexQueue> findByObjectType(SysIndexObjectTypeCodeEnum type, Integer limit){
        return  this.getSession()
                .createCriteria(getEntityClass())
                .add(Restrictions.eq(SysIndexQueue.SYS_INDEX_OBJECT_TYPE_CODE,type.toCode()))
                .setMaxResults(limit)
                .list();
    }

    //计算某个类型的总数
    public Long findCountByObjectType(SysIndexObjectTypeCodeEnum type){
        return  (Long)this.getSession()
                .createCriteria(getEntityClass())
                .setProjection(Projections.rowCount())
                .add(Restrictions.eq(SysIndexQueue.SYS_INDEX_OBJECT_TYPE_CODE,type.toCode()))
                .uniqueResult();
    }

    //清空所有数据
    public int clearQueue() {
        return getSession().createQuery("delete from " + getEntityClass().getSimpleName()+" siq ").executeUpdate();
    }

    //清空某个类型的数据
    public int emptyQueue(SysIndexObjectTypeCodeEnum type) {
        return getSession().createQuery("delete from " + getEntityClass().getSimpleName()+" siq where siq.sysIndexObjectTypeCode!="+type.toCode()).executeUpdate();
    }

    public int execBySql(String sql){
        return getSession().createSQLQuery(sql).executeUpdate();
    }

    //删除给定id的数据
    public int deleteByIds(List<Integer> ids) {
        if (ids == null || ids.size() == 0) {
            return 0;
        }
        return getSession().createQuery(
                "delete from "
                        + getEntityClass().getSimpleName()
                        + " siq where siq.sysIndexQueueId in ("
                        + StringUtils.join(
                        ids.toArray(), ",") + ")").executeUpdate();
    }
}
