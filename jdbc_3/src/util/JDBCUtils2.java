package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public class JDBCUtils2 {
	/*
	 * 使用DBCP数据库连接池技术获取连接
	 */
	private static DataSource source;
	static{
		try {
			Properties pros = new Properties();
			InputStream is = new FileInputStream(new File("resources/dbcp.properties"));
			pros.load(is);
			//创建一个DBCP数据库连接池
			source = BasicDataSourceFactory.createDataSource(pros);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static Connection testGetConnection() throws Exception {

		Connection connection = source.getConnection();
		
		return connection;
	}
	
	/**
	 * 关闭资源的操作
	 * 
	 * @param connection
	 * @param ps
	 */
	public static void closeResource(Connection connection,Statement ps){
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭资源的操作
	 * @param connection
	 * @param ps
	 * @param rs
	 */
	public static void closeResource(Connection connection,Statement ps,ResultSet rs){
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
