package com.khanhvu.converter;

import org.modelmapper.ModelMapper;

import com.khanhvu.dto.BuildingDTO;
import com.khanhvu.entity.BuildingEntity;

public class BuildingConverter {
	
	public BuildingDTO convertToDTO(BuildingEntity buildingEntity) {
		ModelMapper modelMapper = new ModelMapper();
		BuildingDTO result = modelMapper.map(buildingEntity, BuildingDTO.class);
		return result;
	}
	public BuildingEntity convertToEntity(BuildingDTO buildingDTO) {
		ModelMapper modelMapper = new ModelMapper();
		BuildingEntity result = modelMapper.map(buildingDTO, BuildingEntity.class);
		return result;
	}
}
