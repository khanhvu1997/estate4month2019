package com.khanhvu.entity;

import com.khanhvu.annotation.Column;
import com.khanhvu.annotation.Entity;
import com.khanhvu.annotation.Table;

@Entity
@Table( name = " rentarea")
public class RentArea extends BaseEntity {
	
	@Column(name = "buildingid")
	private Long buildingid;
	
	@Column(name = "value")
	private String value;

	public Long getBuildingid() {
		return buildingid;
	}

	public void setBuildingid(Long buildingid) {
		this.buildingid = buildingid;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
