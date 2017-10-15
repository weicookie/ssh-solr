package com.harvey.common.solr.entity;

/**
 * Created by Administrator on 2017/8/27 0027.
 */
public class SingleSearchEntity implements ISearchEntity{

    @Override
    public SingleSearchEntity asSingleSearchEntity() {
        return this;
    }

    @Override
    public boolean isSingleSearchEntity() {
        return true;
    }
}
