package com.harvey.common.base;

import java.io.Serializable;
import java.util.List;

/**
 * 公共dao接口
 * Created by lhw on 2017/4/14.
 */
public interface EntityDao <E,PK extends Serializable> {

    /* 获取entity */
    E getById(PK id);

    /* 根据id删除entity*/
    void deleteById(PK id);

    /* 删除entity*/
    void delete(E obj);

    /** 插入数据 */
    void save(E entity);

    /** 更新数据 */
    void update(E entity);

    /** 根据id检查是否插入或是更新数据 */
    void saveOrUpdate(E entity);

    /** 用于hibernate.flush() 有些dao实现不需要实现此类  */
    void flush();

    /** 用于hibernate.clear() 有些dao实现不需要实现此类  */
    void clear();

    /* 查找出所有*/
    List<E> findAll();

    /* 根据属性查询*/
    List<E> findByKey(String keyName, Object keyId);

}
