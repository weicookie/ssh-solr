package com.harvey.common.base;

import java.io.Serializable;

/**
 * 抽象的实体类，所有的类都继承该类，实现序列化.
 */
public abstract class BaseEntity implements Serializable {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";
}
