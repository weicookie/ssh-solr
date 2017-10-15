package com.harvey.common.solr.client;


import com.harvey.common.solr.entity.ISearchEntity;

public interface IIndexProvider<E extends ISearchEntity> {
    E getEntity(Integer id);
}
