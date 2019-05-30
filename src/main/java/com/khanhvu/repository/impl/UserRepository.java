package com.khanhvu.repository.impl;

import com.khanhvu.entity.BuildingEntity;
import com.khanhvu.entity.UserEntity;
import com.khanhvu.repository.IUserRepository;

public class UserRepository extends AbstractJDBC<BuildingEntity> implements IUserRepository{

	@Override
	public Long insert(UserEntity userEntity) {
		
		return null;
	}

}
