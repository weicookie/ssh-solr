package com.harvey.common.helper;

import com.harvey.service.ISysIndexQueueService;
import com.harvey.service.ISysUserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 业务服务类管理器，所有的service以接口的形式注入
 */
@Component
public class ServiceManager implements InitializingBean {
    public static boolean inited;

    public static ISysUserService sysUserService;
    public static ISysIndexQueueService sysIndexQueueService;

    @Override
    public void afterPropertiesSet() throws Exception {
        inited = true;
    }

    public static void setInited(boolean inited) {
        ServiceManager.inited = inited;
    }

    public static boolean isInited() {
        return inited;
    }

    public ISysUserService getSysUserService() {
        return sysUserService;
    }

    public void setSysUserService(ISysUserService sysUserService) {
        ServiceManager.sysUserService = sysUserService;
    }

    public ISysIndexQueueService getSysIndexQueueService() {
        return sysIndexQueueService;
    }

    public void setSysIndexQueueService(ISysIndexQueueService sysIndexQueueService) {
        ServiceManager.sysIndexQueueService = sysIndexQueueService;
    }
}
