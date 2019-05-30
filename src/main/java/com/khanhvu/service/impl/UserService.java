package com.khanhvu.service.impl;

import com.khanhvu.converter.UserConverter;
import com.khanhvu.dto.UserDTO;
import com.khanhvu.entity.UserEntity;
import com.khanhvu.service.IUserService;

public class UserService implements IUserService {

	@Override
	public UserDTO save(UserDTO newUser) {
		UserConverter converter = new UserConverter();
		@SuppressWarnings("unused")
		UserEntity userEntity = converter.convertToEntity(newUser);
		
		return null;
	}
}
