package com.harvey.module.user.domain;


import com.harvey.common.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "sys_user")
public class SysUser extends BaseEntity {
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "SysUser";
	public static final String USER_ID = "userId";
	public static final String AGE = "age";
	public static final String SCHOOL = "school";
	public static final String USER_NAME = "userName";
	
	//columns START
	/**
	 * userId	   db_column: user_id 
	 */	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SYS_USER_ID", unique = true, nullable = false, insertable = true, updatable = true, length = 11)
	private Integer sysUserId;
	/**
	 * age	   db_column: age 
	 */	
	@Column(name = "AGE", unique = false, nullable = true, insertable = true, updatable = true, length = 11)
	private Integer age;
	/**
	 * school	   db_column: school 
	 */	
	@Column(name = "SCHOOL", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	private String school;
	/**
	 * userName	   db_column: user_name 
	 */	
	@Column(name = "USER_NAME", unique = false, nullable = false, insertable = true, updatable = true, length = 255)
	private String userName;
	//columns END

	public SysUser(){
	}

	public Integer getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Integer sysUserId) {
		this.sysUserId = sysUserId;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}

