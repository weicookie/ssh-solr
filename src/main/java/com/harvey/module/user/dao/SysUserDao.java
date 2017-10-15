/*
 * Since 2010 - 2017
 */

package com.harvey.module.user.dao;
import com.harvey.common.base.BaseHibernateDao;
import com.harvey.module.user.domain.SysUser;
import org.springframework.stereotype.Repository;

/**
* 注意：虽然SysUserDao继承了BaseHibernateDao，拥有save、update等方法，
* 但是如果想在dao中将这些方法作为连接点执行切面代码，则需要在相对应的dao中再重新定义
* 因为已经在aop中对save、update、delete进行拦截，所以以下的save、update。delete方法不能删除，所以无法被aop拦截
*/
@Repository
public class SysUserDao extends BaseHibernateDao<SysUser,Integer> {
    @Override
    public void save(SysUser obj) {
        super.save(obj);
    }

    @Override
    public void saveOrUpdate(SysUser obj) {
        super.saveOrUpdate(obj);
    }

    @Override
    public void update(SysUser obj) {
        super.update(obj);
    }

    @Override
    public void delete(SysUser obj) {
        super.delete(obj);
    }

    @Override
    public void deleteById(Integer id) {
        super.deleteById(id);
    }
}
