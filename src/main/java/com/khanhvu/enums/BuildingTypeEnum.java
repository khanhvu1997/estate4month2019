package com.khanhvu.enums;

public enum BuildingTypeEnum {
	
	  	TANG_TRET("tang tret"),
	    NGUYEN_CAN("nguyen can"),
	    NOI_THAT("noi that");
	 

	    private String value;

	    BuildingTypeEnum(String value) {
	        this.value = value;
	    }

	    public String getValue() {
	        return value;
	    }
}
