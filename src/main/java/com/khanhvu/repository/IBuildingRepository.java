package com.khanhvu.repository;

import com.khanhvu.entity.BuildingEntity;

public interface IBuildingRepository extends GenericJDBC<BuildingEntity> {
		Long insert(BuildingEntity buildingEntity);

}
