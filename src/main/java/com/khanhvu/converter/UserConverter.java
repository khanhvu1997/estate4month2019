package com.khanhvu.converter;

import org.modelmapper.ModelMapper;

//import com.khanhvu.dto.BuildingDTO;
import com.khanhvu.dto.UserDTO;
//import com.khanhvu.entity.BuildingEntity;
import com.khanhvu.entity.UserEntity;

public class UserConverter {
	
	public UserDTO convertToDTO(UserEntity entity) {
		ModelMapper modelMapper = new ModelMapper();
		UserDTO result = modelMapper.map(entity, UserDTO.class);
		return result;
	}
	public UserEntity convertToEntity(UserDTO dto) {
		ModelMapper modelMapper = new ModelMapper();
		UserEntity result = modelMapper.map(dto, UserEntity.class);
		return result;
	}
}