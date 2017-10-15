package com.harvey.common.solr.provider;

import com.harvey.common.helper.ServiceManager;
import com.harvey.module.user.domain.SysUser;
import com.harvey.common.solr.client.IIndexProvider;
import com.harvey.common.solr.entity.SysUserIndexEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/8/27 0027.
 */
@Component
public class SysUserIndexProvider implements IIndexProvider<SysUserIndexEntity>{
    private static Logger logger = Logger.getLogger(SysUserIndexProvider.class.getName());
    @Override
    public SysUserIndexEntity getEntity(Integer id) {
        SysUser sysUser = ServiceManager.sysUserService.getById(id);
        SysUserIndexEntity entity = new SysUserIndexEntity();
        try {
            BeanUtils.copyProperties(sysUser, entity);
        } catch (Exception e) {
            logger.error("SysUserIndexProvider.getEntity copyProperties error", e);
            return null;
        }

        return entity;
    }
}
