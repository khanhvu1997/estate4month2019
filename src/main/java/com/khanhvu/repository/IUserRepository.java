package com.khanhvu.repository;

import com.khanhvu.entity.UserEntity;

public interface IUserRepository {
	Long insert(UserEntity userEntity);
}
