package com.khanhvu.service.impl;

import java.sql.Timestamp;

import com.khanhvu.converter.BuildingConverter;
import com.khanhvu.dto.BuildingDTO;
import com.khanhvu.entity.BuildingEntity;
import com.khanhvu.repository.IBuildingRepository;
import com.khanhvu.repository.impl.BuildingRepository;
import com.khanhvu.service.IBuildingService;

public class BuildingService implements IBuildingService{
	private IBuildingRepository buildingRepository;
	public BuildingService() {
		buildingRepository = new BuildingRepository();
	}
	
	@Override
	public BuildingDTO save(BuildingDTO buildingDTO) {
		BuildingConverter buildingConverter = new BuildingConverter();
		BuildingEntity buildingEntity = buildingConverter.convertToEntity(buildingDTO);
		buildingEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		@SuppressWarnings("unused")
		Long id = buildingRepository.insert(buildingEntity);
		return null;
	}
}