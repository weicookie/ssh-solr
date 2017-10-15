package com.harvey.module.user.aop;

import com.harvey.common.helper.ServiceManager;
import com.harvey.module.core.domain.code.SysIndexObjectTypeCodeEnum;
import com.harvey.module.user.domain.SysUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 主要的功能是用户数据发生改变的时候通知索引队列，
 * 让索引进程能够及时地更新索引。
 */
@Aspect
@Component
public class SysUserDaoAop {

    //匹配任何参数的public的save方法
    @AfterReturning("execution(public * com.harvey.module.user.dao.SysUserDao.save(..))")
    public void afterSave(JoinPoint jp) {
        addIndexingQue(jp);
    }

    @AfterReturning("execution(public * com.harvey.module.user.dao.SysUserDao.update(..))")
    public void afterUpdate(JoinPoint jp) {
        addIndexingQue(jp);
    }

    @AfterReturning("execution(public * com.harvey.module.user.dao.SysUserDao.delete(..))")
    public void afterDelete(JoinPoint jp) {
        addIndexingQue(jp);
    }

    private void addIndexingQue(JoinPoint jp) {
        Object[] args = jp.getArgs();
        SysUser user = (SysUser) args[0];
        ServiceManager.sysIndexQueueService.save(SysIndexObjectTypeCodeEnum.SYSUSER, user.getSysUserId());
    }
}
