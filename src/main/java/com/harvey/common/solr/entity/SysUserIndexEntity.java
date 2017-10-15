package com.harvey.common.solr.entity;

import org.apache.solr.client.solrj.beans.Field;

/**
 * 索引类,属性类型与数据库中的类型最好对应
 */
public class SysUserIndexEntity extends SingleSearchEntity{

    private static final String SYS_USER_ID = "sysUserId";
    private static final String USER_NAME = "userName";
    private static final String AGE = "age";
    private static final String SCHOOL = "school";

    @Field(SYS_USER_ID)
    private Integer sysUserId;

    @Field(USER_NAME)
    private String userName;

    @Field(AGE)
    private Integer age;

    @Field(SCHOOL)
    private String school;


    public Integer getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
