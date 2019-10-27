package com.khanhvu.entity;

import java.sql.Timestamp;

import com.khanhvu.annotation.Column;

//import java.sql.Timestamp;

import com.khanhvu.annotation.Entity;
import com.khanhvu.annotation.Table;

@Entity
@Table( name = " user")
public class UserEntity extends BaseEntity {
	@Column(name = "username")
	private String userName;
	
	@Column(name = "password")
	private String passWord;
	
	@Column(name = "fullname")
	private String fullName;
	
	@Column(name = "status")
	private Integer status;
	
	
	@Column(name = "id")
	private Long id;
	
	@Column(name = "createby")
	private String createBy;
	
	@Column(name = "modifiedby")
	private String modifiedBy;
	
	@Column(name = "createddate")
	private Timestamp createdDate;
	
	@Column(name = "modifieddate")
	private Timestamp modifiedDate;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
