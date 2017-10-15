package com.harvey.common.base;


import cn.org.rapid_framework.page.PageRequest;

/**
 * Created by Administrator on 2017/8/29 0029.
 */
public class BaseQuery extends PageRequest implements java.io.Serializable {
    private static final long serialVersionUID = -360860474471966681L;
    public static final int DEFAULT_PAGE_SIZE = 10;


    public BaseQuery() {//NOSONAR
        setPageSize(DEFAULT_PAGE_SIZE);
    }

}