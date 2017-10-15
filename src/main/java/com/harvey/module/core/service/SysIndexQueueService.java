package com.harvey.module.core.service;

import com.harvey.common.base.BaseEntityManager;
import com.harvey.common.base.EntityDao;
import com.harvey.module.core.dao.SysIndexQueueDao;
import com.harvey.module.core.domain.SysIndexQueue;
import com.harvey.module.core.domain.code.SysIndexObjectTypeCodeEnum;
import com.harvey.service.IIndexService;
import com.harvey.service.ISysIndexQueueService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Created by Administrator on 2017/8/27 0027.
 */
@Service
public class SysIndexQueueService extends BaseEntityManager<SysIndexQueue,Integer> implements ISysIndexQueueService {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private IIndexService indexService;

    @Autowired
    public void setIndexService(IIndexService indexService) {
        this.indexService = indexService;
    }

    private SysIndexQueueDao sysIndexQueueDao;

    @Autowired
    public void setSysIndexQueueDao(SysIndexQueueDao sysIndexQueueDao) {
        this.sysIndexQueueDao = sysIndexQueueDao;
    }

    @Override
    protected EntityDao<SysIndexQueue, Integer> getEntityDao() {
        return sysIndexQueueDao;
    }

    @Transactional
    public void save(SysIndexObjectTypeCodeEnum sysIndexObjectTypeCodeEnum, Integer objectId) {
        SysIndexQueue indexQueue = new SysIndexQueue();
        indexQueue.setSysIndexObjectTypeCode(sysIndexObjectTypeCodeEnum.toCode());
        indexQueue.setSysObjectId(objectId);
        sysIndexQueueDao.save(indexQueue);
    }

    @Override
    public List<SysIndexQueue> findByObjectType(SysIndexObjectTypeCodeEnum type, Integer limit) {
        return sysIndexQueueDao.findByObjectType(type, limit);
    }

    @Override
    public Long findCountByObjectType(SysIndexObjectTypeCodeEnum type) {
        return sysIndexQueueDao.findCountByObjectType(type);
    }

    @Transactional
    public void deleteList(List<SysIndexQueue> list) {
       for (SysIndexQueue queue : list){
            sysIndexQueueDao.delete(queue);
       }
    }

    @Transactional
    public void sysInitAllIndexs() {
        sysIndexQueueDao.clearQueue();
        indexService.reBuildIndex(new String[]{SysIndexObjectTypeCodeEnum.SYSUSER.toCode()});
    }

    @Transactional
    public int execIndexSQL(String sql) {
        return sysIndexQueueDao.execBySql(sql);
    }
}
