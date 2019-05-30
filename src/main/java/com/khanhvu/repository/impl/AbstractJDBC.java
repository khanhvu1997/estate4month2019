package com.khanhvu.repository.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.khanhvu.annotation.Column;
import com.khanhvu.annotation.Table;
import com.khanhvu.mapper.ResultSetMapper;
import com.khanhvu.repository.GenericJDBC;

public class AbstractJDBC<T> implements GenericJDBC<T> {

	private Class<T> zClass;

	@SuppressWarnings("unchecked")
	public AbstractJDBC() {
		Type type = getClass().getGenericSuperclass();
		ParameterizedType parameterizedType = (ParameterizedType) type;
		zClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}

	private Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String databaseURL = "jdbc:mysql://localhost:3306/estate4month2019";
			String user = "root";
			String password = "1997";
			return DriverManager.getConnection(databaseURL, user, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<T> query(String sql, Object... parameters) {
		ResultSetMapper<T> resultSetMapper = new ResultSetMapper<T>();

		try (Connection conn = getConnection();
				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			if (conn != null) {
				return resultSetMapper.mapRow(resultSet, this.zClass);
			}

		} catch (SQLException e) {
			System.out.print(e.getMessage());
		}
		return null;
	}

	@Override
	public void update(String sql, Object... parameters) {
		PreparedStatement statement = null;
		ResultSet resultset = null;
		Connection conn = null;

		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			statement = conn.prepareStatement(sql);
			if (conn != null) {
				// set parameter to statement
				for (int i = 0; i < parameters.length; i++) {
					int index = i + 1;
					statement.setObject(index, parameters[i]);
				}
			}

			statement.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			if (conn != null) {

				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		} finally {

			try {
				if (conn != null) {
					conn.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}

	}

	@Override
	public Long insert(String sql, Object... parameters) {
		PreparedStatement statement = null;
		ResultSet resultset = null;
		Connection conn = null;

		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			statement = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
			if (conn != null) {
				// set parameter to statement
				for (int i = 0; i < parameters.length; i++) {
					int index = i + 1;
					statement.setObject(index, parameters[i]);
				}
			}

			int rowsInserted = statement.executeUpdate();
			resultset = statement.getGeneratedKeys();
			conn.commit();
			if (rowsInserted > 0) {

				while (resultset.next()) {
					Long id = resultset.getLong(1);
					return id;

				}
			}
		} catch (SQLException e) {
			if (conn != null) {

				try {
					conn.rollback();
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}

			}
		} finally {

			try {
				if (conn != null) {
					conn.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Long insert(Object object) {
		PreparedStatement statement = null;
		ResultSet resultset = null;
		Connection conn = null;

		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			/*
			 * String tableName = ""; if(zClass.isAnnotationPresent(Table.class)) { Table
			 * table = zClass.getAnnotation(Table.class); tableName = table.name(); }
			 */
			String sql = createSQLInsert();
			statement = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
			if (conn != null) {
				Class<?> zClass = object.getClass();
				Field[] fields = zClass.getDeclaredFields();
				// set parameter to statement
				
				  for (int i = 0; i < fields.length; i++) { 
					  Field field = fields[i];
					  field.setAccessible(true);
					  int index = i + 1;
					  statement.setObject(index, field.get(object)); 
					  }
				 
				  Class<?> parentClass = zClass.getSuperclass();
				  int indexParent = fields.length + 1;
					while (parentClass != null) {
						  for (int i = 0; i < parentClass.getDeclaredFields().length; i++) { 
							  Field field = parentClass.getDeclaredFields()[i];
							  field.setAccessible(true);
							  statement.setObject(indexParent, field.get(object));
							  indexParent = indexParent + 1;
							  }
						parentClass = parentClass.getSuperclass();
					}

			int rowsInserted = statement.executeUpdate();
			resultset = statement.getGeneratedKeys();
			conn.commit();
			if (rowsInserted > 0) {

				while (resultset.next()) {
					Long id = resultset.getLong(1);
					return id;

				}
			}
		}
		} catch (SQLException | IllegalAccessException e) {
			if (conn != null) {

				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		} finally {

			try {
				if (conn != null) {
					conn.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}

	private String createSQLInsert() {

		String tableName = "";
		if (zClass.isAnnotationPresent(Table.class)) {
			Table table = zClass.getAnnotation(Table.class);
			tableName = table.name();
		}
		StringBuilder fields = new StringBuilder();
		StringBuilder params = new StringBuilder();
		
		for(Field field : zClass.getDeclaredFields()) {
			if(fields.length() > 1) {
				fields.append(",");
				params.append(",");
			}
			if(field.isAnnotationPresent(Column.class)) {
				Column column = field.getAnnotation(Column.class);
				fields.append(column.name());
				params.append("?");
			
			}
		}
		
		//Check parent class
		Class<?> parentClass = zClass.getSuperclass();
		while (parentClass != null) {
			// Field[] fieldsParents = parentClass.getDeclaredFields();
			for(Field field : parentClass.getDeclaredFields()) {
				if(fields.length() > 1) {
					fields.append(",");
					params.append(",");
				}
				if(field.isAnnotationPresent(Column.class)) {
					Column column = field.getAnnotation(Column.class);
					fields.append(column.name());
					params.append("?");
				}
			}
			parentClass = parentClass.getSuperclass();
		}
		
		String sql = "INSERT INTO " + tableName + "(" + fields.toString() + ") VALUES(" + params.toString() + ")";
		return sql;
	}
}
