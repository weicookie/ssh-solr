package com.harvey.module.user.service;
import com.harvey.common.base.BaseEntityManager;
import com.harvey.common.base.EntityDao;
import com.harvey.module.user.dao.SysUserDao;
import com.harvey.module.user.domain.SysUser;
import com.harvey.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserService extends BaseEntityManager<SysUser,Integer> implements ISysUserService {

	private SysUserDao sysUserDao;

	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
    @Autowired
	public void setSysUserDao(SysUserDao dao) {
		this.sysUserDao = dao;
	}

	public EntityDao<SysUser,Integer> getEntityDao() {
		return this.sysUserDao;
	}

}
