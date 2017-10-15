package com.harvey.common.base;

import java.io.Serializable;
import java.util.List;

/**
 * 公共服务接口
 * Created by lhw on 2017/4/15.
 */
public interface IBaseEntityManager<E, PK extends Serializable> {
    E getById(PK id);

    void save(E entity);

    void removeById(PK id);

    void remove(E obj);

    void update(E entity);

    List<E> findAll();

    void flush();

    List<E> findByKey(String keyName, Object value);

    void saveOrUpdate(E entity);

    void batchSave(List<E> entitys);

    void batchRemove(List<E> entitys);
}
