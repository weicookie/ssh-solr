package com.harvey.service;


import com.harvey.common.base.IBaseEntityManager;
import com.harvey.module.core.domain.SysIndexQueue;
import com.harvey.module.core.domain.code.SysIndexObjectTypeCodeEnum;

import java.util.List;

/**
 * Created by Administrator on 2017/8/26 0026.
 */
public interface ISysIndexQueueService extends IBaseEntityManager<SysIndexQueue, Integer> {
    void save(SysIndexObjectTypeCodeEnum sysIndexObjectTypeCodeEnum, Integer objectId);

    List<SysIndexQueue> findByObjectType(SysIndexObjectTypeCodeEnum type, Integer limit);

    Long findCountByObjectType(SysIndexObjectTypeCodeEnum type);

    void deleteList(List<SysIndexQueue> list);

    void sysInitAllIndexs();

    int execIndexSQL(String sql);

}
