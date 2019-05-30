package com.khanhvu.entity;

import java.sql.Timestamp;

import com.khanhvu.annotation.Column;

public class BaseEntity {
	
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
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
