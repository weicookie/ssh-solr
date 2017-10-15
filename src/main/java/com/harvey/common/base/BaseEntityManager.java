package com.harvey.common.base;

import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/** 抽象业务类
 * Created by Administrator on 2017/4/15.
 */
@Transactional(readOnly = true)
public abstract class BaseEntityManager<E, PK extends Serializable> implements IBaseEntityManager<E, PK>  {

    public BaseEntityManager() {

    }

    protected abstract EntityDao<E, PK> getEntityDao();

    @Override
    @Transactional(readOnly = true)
    public E getById(PK id) {
        return getEntityDao().getById(id);
    }

    @Override
    @Transactional
    public void save(E entity) {
        getEntityDao().save(entity);
    }

    @Override
    @Transactional
    public void removeById(PK id) {
        getEntityDao().deleteById(id);
    }

    @Override
    @Transactional
    public void remove(E obj) {
        getEntityDao().delete(obj);
    }

    @Override
    @Transactional
    public void update(E entity) {
        getEntityDao().update(entity);
    }

    @Override
    public List<E> findAll() {
        return getEntityDao().findAll();
    }

    @Override
    @Transactional
    public void flush() {
        getEntityDao().flush();
    }

    @Override
    @Transactional(readOnly = true)
    public List<E> findByKey(String keyName, Object value) {
        return getEntityDao().findByKey(keyName, value);
    }

    @Override
    @Transactional
    public void saveOrUpdate(E entity) {
        getEntityDao().saveOrUpdate(entity);
    }

    @Override
    @Transactional
    public void batchSave(List<E> entitys) {
        //获取迭代器
        Iterator iterator = entitys.iterator();
        while(iterator.hasNext()) {
            E entity = (E)iterator.next();
            this.getEntityDao().save(entity);
        }
    }

    @Override
    @Transactional
    public void batchRemove(List<E> entitys) {
        Iterator iterator = entitys.iterator();
        while(iterator.hasNext()) {
            E entity = (E)iterator.next();
            this.getEntityDao().delete(entity);
        }
    }
}
