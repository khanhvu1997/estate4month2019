package com.khanhvu.repository.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import com.khanhvu.annotation.Column;
import com.khanhvu.annotation.Table;
import com.khanhvu.mapper.ResultSetMapper;
import com.khanhvu.paging.Pageble;
import com.khanhvu.paging.Sorter;
import com.khanhvu.repository.GenericJDBC;

public class AbstractJDBC<T> implements GenericJDBC<T> {
	
	//zClass để scan các field
	private Class<T> zClass;

	@SuppressWarnings("unchecked")
	public AbstractJDBC() {
		//lấy zClass
		Type type = getClass().getGenericSuperclass();
		ParameterizedType parameterizedType = (ParameterizedType) type;
		//kiểu mảng mà class chỉ có <T> nên lấy [0]
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

	/*@Override
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
	}*/

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
				//zClass có giá trị
				Class<?> zClass = object.getClass();
				//tạo mảng có giá trị là các fileds
				Field[] fields = zClass.getDeclaredFields();
				// set parameter to statement
				
				  for (int i = 0; i < fields.length; i++) { 
					  Field field = fields[i];
					  //xét quyền để truy cập field
					  field.setAccessible(true);
					  int index = i + 1;
					  statement.setObject(index, field.get(object)); 
					  }
				 
				  //get giá trị field lớp cha
				  Class<?> parentClass = zClass.getSuperclass();
				  //giá trị sau khi chạy vòng for phía trên + 1
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
		//lấy tên bảng
		if (zClass.isAnnotationPresent(Table.class)) {
			Table table = zClass.getAnnotation(Table.class);
			tableName = table.name();
		}
		//Tên fields trong bảng
		StringBuilder fields = new StringBuilder(" ");
		//Giá trị parameters 
		StringBuilder params = new StringBuilder(" ");
		//duyệt vòng for để hiện thị các column name và params ? 
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
		
		//Check parent class cha để get fields lớp cha
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

	@Override
	public void update(Object object) {
		PreparedStatement statement = null;
		Connection conn = null;

		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			/*
			 * String tableName = ""; if(zClass.isAnnotationPresent(Table.class)) { Table
			 * table = zClass.getAnnotation(Table.class); tableName = table.name(); }
			 */
			String sql = createSQLUpdate();
			statement = conn.prepareStatement(sql);
			if (conn != null) {
				//zClass có giá trị
				Class<?> zClass = object.getClass();
				//tạo mảng có giá trị là các fileds
				Field[] fields = zClass.getDeclaredFields();
				// set parameter to statement
				
				  for (int i = 0; i < fields.length; i++) { 
					  Field field = fields[i];
					  //xét quyền để truy cập field
					  field.setAccessible(true);
					  int index = i + 1;
					  statement.setObject(index, field.get(object)); 
					  }
				 
				  //get giá trị field lớp cha
				  Class<?> parentClass = zClass.getSuperclass();
				  //giá trị sau khi chạy vòng for phía trên + 1
				  int indexParent = fields.length + 1;
				  Object id = null;
					while (parentClass != null) {
						  for (int i = 0; i < parentClass.getDeclaredFields().length; i++) { 
							  Field field = parentClass.getDeclaredFields()[i];
							  field.setAccessible(true);
							  String name = field.getName();
							  if(!name.equals("id")) {
								  statement.setObject(indexParent, field.get(object));
								  indexParent = indexParent + 1;
							  }else {
								  id = field.get(object);
							  }
								  
								  
						}
						parentClass = parentClass.getSuperclass();
					}
			statement.setObject(indexParent, id);
			statement.executeUpdate();
			conn.commit();
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
	}
	private String createSQLUpdate() {

		String tableName = "";
		//lấy tên bảng
		if (zClass.isAnnotationPresent(Table.class)) {
			Table table = zClass.getAnnotation(Table.class);
			tableName = table.name();
		}
		StringBuilder sets = new StringBuilder("");
		String where = null;
		//duyệt vòng for để hiện thị các column name và params ? 
		for(Field field : zClass.getDeclaredFields()) {
			if(field.isAnnotationPresent(Column.class)) {
				Column column = field.getAnnotation(Column.class);
				String columnName = column.name();
				String value = columnName + " = ? ";
				if(columnName.equals("id")) {
					if(sets.length()>1) {
						sets.append(", ");
					}
					sets.append(value);
				}	
			}
		}
		
		//Check parent class cha để get fields lớp cha
		Class<?> parentClass = zClass.getSuperclass();
		while (parentClass != null) {
			// Field[] fieldsParents = parentClass.getDeclaredFields();
			for(Field field : parentClass.getDeclaredFields()) {
				if(field.isAnnotationPresent(Column.class)) {
					Column column = field.getAnnotation(Column.class);
					String columnName = column.name();
					String value = columnName + " = ? ";
					if(columnName.equals("id")) {
						if(sets.length()>1) {
							sets.append(", ");
						}
						sets.append(value);
					}else {
						where = " WHERE " + value;
					}	
				}
			}
			parentClass = parentClass.getSuperclass();
		}
		
		String sql = "UPDATE " + tableName + " SET " + sets.toString() + where;
		return sql;
	}

	@Override
	public void delete(long id) {
		PreparedStatement statement = null;
		Connection conn = null;

		try {
			conn = getConnection();
			conn.setAutoCommit(false);
		
			String tableName = "";
			//lấy tên bảng
			if (zClass.isAnnotationPresent(Table.class)) {
				Table table = zClass.getAnnotation(Table.class);
				tableName = table.name();
			}
			String sql = " DELETE FROM "+ tableName + "WHERE id = ? ";
			statement = conn.prepareStatement(sql);
			if (conn != null) {
			statement.setObject(1, id);
			statement.executeUpdate();
			conn.commit();
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
	}

	/*private String createSQLDelete() {

		String tableName = "";
		//lấy tên bảng
		if (zClass.isAnnotationPresent(Table.class)) {
			Table table = zClass.getAnnotation(Table.class);
			tableName = table.name();
		}
		String where = " WHERE id = ? ";
		
		String sql = "UPDATE " + tableName +	 where;
		return sql;
	}*/

	@Override
	public <T> T findById(long id) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection conn = null;
		ResultSetMapper<T> resultSetMapper = new ResultSetMapper<T>();
		
		String tableName = "";
		//lấy tên bảng
		if (zClass.isAnnotationPresent(Table.class)) {
			Table table = zClass.getAnnotation(Table.class);
			tableName = table.name();
		}
		String sql = "SELECT * FROM "+ tableName + "WHERE id = ?";

		try{
			conn = getConnection();
			statement = conn.prepareStatement(sql);
			statement.setObject(1, id);
			resultSet = statement.executeQuery();
			if (conn != null) {
				//return  resultSetMapper.mapRow(resultSet, this.zClass).get(0);
			}

		} catch (SQLException e) {
			System.out.print(e.getMessage());
		}finally {

			try {
				if (conn != null) {
					conn.close();
				}
				if (statement != null) {
					statement.close();
				}
				if(resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public List<T> findAll(Map<String, Object> properties,Pageble pageble, Object...where) {
		Statement statement = null;
		ResultSet resultSet = null;
		Connection conn = null;
		ResultSetMapper<T> resultSetMapper = new ResultSetMapper<T>();
		
		StringBuilder sql = createSQLfindAll(properties);
		if(where != null && where.length >0) {
			sql.append(where[0]);
		}
		if(pageble != null) {
			if(pageble.getOffset() != null && pageble.getLimit() != null) {
				sql.append(" LIMIT "+pageble.getOffset()+" , "+ pageble.getLimit()+" ");
			}
			if(pageble.getSorter() != null) {
				Sorter sorter = pageble.getSorter();
				sql.append(" ORDER BY "+sorter.getSortName()+" "+sorter.getSortBy()+" ");
			}
		}

		try{
			conn = getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery(sql.toString());
			if (conn != null) {
				return  resultSetMapper.mapRow(resultSet, this.zClass);
			}

		} catch (SQLException e) {
			System.out.print(e.getMessage());
		}finally {

			try {
				if (conn != null) {
					conn.close();
				}
				if (statement != null) {
					statement.close();
				}
				if(resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}

	private StringBuilder createSQLfindAll(Map<String, Object> properties) {
		
		String tableName = "";
		//lấy tên bảng
		if (zClass.isAnnotationPresent(Table.class)) {
			Table table = zClass.getAnnotation(Table.class);
			tableName = table.name();
		}
		
		StringBuilder result = new StringBuilder("SELECT * FROM "+ tableName+ " WHERE 1=1");
		if(properties != null && properties.size() > 0) {
			String[] params = new String[properties.size()];
			Object[] values = new Object[properties.size()];
			int i = 0;
			for(Map.Entry<?, ?> item: properties.entrySet() ) {
				params[i] = (String) item.getKey();
				values[i] = item.getValue();
				i++;
			}
			for(int i1=0; i1 < params.length; i1++ ) {
				if(values[i1] instanceof String) {
					result.append("and LOWER("+ params[i1]+") LIKE '% "+values[i1]+" %' ");
				}if(values[i1] instanceof Integer) {
					result.append("and "+params[i1]+" = "+values[i1]+" ");
				}
			}
		}
		return result;
	}
}
