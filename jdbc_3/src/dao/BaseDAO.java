package dao;

import util.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/*
 * DAO:data(base) access object
 * 封装了针对于数据表的通用的操作
 */

/*
 * 封装了对于数据表的操作
 */
public abstract class BaseDAO {
	
	//通用的增删改操作 --- version2.0
	public void update(Connection connection,String sql, Object... args) {//sql当中占位符的个数和可变形参的长度一致
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);

			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);// 小心参数声明错误
			}
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeResource(null, ps);
		}
	}
	
	//通用的查询操作，用于返回数据表中的一条记录(version 2.0)
	public  <T> T getInstance(Connection connection,Class<T> clazz,String sql,Object...args){
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i+1, args[i]);
			}
			
			//执行获取结果集
			rs = ps.executeQuery();
			//获取结果集的元数据
			ResultSetMetaData rsmd = rs.getMetaData();
			//获取列数
			int columnCount = rsmd.getColumnCount();
			
			if (rs.next()) {
				T t = clazz.newInstance();
				for(int i = 0; i<columnCount;i++){
					Object columnValue = rs.getObject(i+1);
					String columnLabel = rsmd.getColumnLabel(i+1);
					
					//通过反射，将对象指定名的属性赋值给指定的值
					Field field = clazz.getDeclaredField(columnLabel);
					field.setAccessible(true);
					field.set(t, columnValue);
				}
				return t;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeResource(null, ps, rs);
		}
		return null;
	}
	
	//通用的查询操作，用于返回数据表中的多条记录的集合(version 2.0)
	public <T> List<T> getForList(Connection connection,Class<T> clazz,String sql,Object...args){
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i+1, args[i]);
			}
			
			//执行获取结果集
			rs = ps.executeQuery();
			//获取结果集的元数据
			ResultSetMetaData rsmd = rs.getMetaData();
			//获取列数
			int columnCount = rsmd.getColumnCount();
			//创建集合对象
			ArrayList<T> list = new ArrayList<T>();
			
			while (rs.next()) {
				T t = clazz.newInstance();
				for(int i = 0; i<columnCount;i++){
					Object columnValue = rs.getObject(i+1);
					String columnLabel = rsmd.getColumnLabel(i+1);
					
					Field field = clazz.getDeclaredField(columnLabel);
					field.setAccessible(true);
					field.set(t, columnValue);
				}
				list.add(t);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeResource(null, ps, rs);
		}
		return null;
	}
	
	//用于查询一些特殊值的通用方法
	public <E> E getValue(Connection connection,String sql,Object...args) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(sql);
			
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i+1, args[i]);
			}
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				return (E)rs.getObject(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeResource(null, ps, rs);
		}
		return null;
	}

	
	
}
