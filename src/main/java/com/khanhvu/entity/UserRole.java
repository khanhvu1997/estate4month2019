package com.khanhvu.entity;

import com.khanhvu.annotation.Column;
import com.khanhvu.annotation.Entity;
import com.khanhvu.annotation.Table;

@Entity
@Table( name = " user_role")
public class UserRole extends BaseEntity {
	
	@Column(name = "userid")
	private Long userId;
	
	@Column(name = "roleid")
	private Long roleId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}
