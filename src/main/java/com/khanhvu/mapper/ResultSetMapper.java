package com.khanhvu.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.khanhvu.annotation.Column;
import com.khanhvu.annotation.Entity;

public class ResultSetMapper<T> {

	public List<T> mapRow(ResultSet rs,Class<T> zClass ){
		List<T> results = new ArrayList<>();
		try {
			if(zClass.isAnnotationPresent(Entity.class)) {
				ResultSetMetaData resultSetMetaData = rs.getMetaData();
				Field[] fields = zClass.getDeclaredFields();
				while(rs.next()) {
					T object = (T) zClass.newInstance();
					// get giá trị của 1 row trong resultSet và set vào trong entity
					for(int i=0; i< resultSetMetaData.getColumnCount(); i++) {
						String columnName = resultSetMetaData.getColumnClassName(i+1);
						Object columnValue = rs.getObject( i+1);
						//current class 
						convertResultSetToEntity(fields, columnName, columnValue, object);
					
						//parent class
						Class<?> parentClass = zClass.getSuperclass();
						while (parentClass != null) {
							Field[] fieldsParents = parentClass.getDeclaredFields();
							//logic convert data
							convertResultSetToEntity(fieldsParents, columnName, columnValue, object);
							parentClass = parentClass.getSuperclass();
						}
					}
					//
					results.add(object);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		return results;
	}

	private void convertResultSetToEntity(Field[] fieldsParents, String columnName, Object columnValue, T object) throws IllegalAccessException, InvocationTargetException {
		for(Field field: fieldsParents) {
			if(field.isAnnotationPresent(Column.class)) {
				Column column = field.getAnnotation(Column.class);
				if(column.name().equals(columnName) && columnValue != null) {
					BeanUtils.setProperty(object, field.getName(), columnValue);
					break;
				}
			}
		}
		
	}
	
}
