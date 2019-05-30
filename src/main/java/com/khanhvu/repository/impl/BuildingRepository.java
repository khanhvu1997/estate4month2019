package com.khanhvu.repository.impl;

import com.khanhvu.entity.BuildingEntity;
import com.khanhvu.repository.IBuildingRepository;

public class BuildingRepository extends AbstractJDBC<BuildingEntity> implements IBuildingRepository  {

	@Override
	public Long insert(BuildingEntity buildingEntity) {

		return null;
	}


	/*
	 * @Override public Long insert(BuildingEntity entity) { StringBuilder sql = new
	 * StringBuilder("INSERT INTO building (name, numberofbasement, buildingarea , district , ward, street, structure, costrent, costdescription, servicecost"
	 * ); sql.
	 * append(", carcost , motorbikecost, overtimecost, electricitycost, deposit, payment, timecontract, timedecorator, managername, managerphone, createddate"
	 * ); sql.append(", modifieddate, createdby, modifiedby) ");
	 * sql.append("VALUE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) "); return
	 * this.insert(sql.toString(), entity.getName(), entity.getNumberOfBasement(),
	 * entity.getBuildungArea(),entity.getDistrict(),entity.getWard(),
	 * entity.getStreet(), entity.getStructure(), entity.getCostRent(),
	 * entity.getCostDescription(), entity.getServiceCost(), entity.getCarCost(),
	 * entity.getMotorbikeCost(), entity.getOvertimeCost(),
	 * entity.getElectricityCost(), entity.getDeposit(), entity.getPayment(),
	 * entity.getTimeContract(), entity.getTimeDecorator(), entity.getManagerName(),
	 * entity.getManagerPhone(), entity.getCreatedDate(), entity.getModifiedDate(),
	 * entity.getCreateBy(), entity.getModifiedBy()); }
	 */
	

}
