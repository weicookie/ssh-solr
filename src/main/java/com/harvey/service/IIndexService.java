package com.harvey.service;

/**
 * Created by Administrator on 2017/8/27 0027.
 */
public interface IIndexService {
    //重建索引
    void reBuildIndex(String[] types);
    //删除索引
    void deleteIndex(String[] types);
}
