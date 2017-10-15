package com.harvey.processor.user;

import com.harvey.common.solr.client.IIndexProvider;
import com.harvey.module.core.domain.code.SysIndexObjectTypeCodeEnum;
import com.harvey.processor.BaseIndexProcessor;
import com.harvey.service.ISysIndexQueueService;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/8/27 0027.
 */
@Component
public class SysUserIndexProcessor extends BaseIndexProcessor {

    private IIndexProvider sysUserIndexProvider;

    private ISysIndexQueueService sysIndexQueueService;

    public IIndexProvider getSysUserIndexProvider() {
        return sysUserIndexProvider;
    }

    public void setSysUserIndexProvider(IIndexProvider sysUserIndexProvider) {
        this.sysUserIndexProvider = sysUserIndexProvider;
    }

    public ISysIndexQueueService getSysIndexQueueService() {
        return sysIndexQueueService;
    }

    public void setSysIndexQueueService(ISysIndexQueueService sysIndexQueueService) {
        this.sysIndexQueueService = sysIndexQueueService;
    }

    @Override
    public IIndexProvider<?> getProvider() {
        return sysUserIndexProvider;
    }

    @Override
    public SysIndexObjectTypeCodeEnum getObjectTypeCode() {
        return SysIndexObjectTypeCodeEnum.SYSUSER;
    }
}
