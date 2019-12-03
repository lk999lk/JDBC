package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;

public class JDBCUtils3 {
	/*
	 * 使用Druid数据库连接池技术获取连接
	 */
//	private static DataSource source;
	public static DataSource source;
	static {
		Properties pros = new Properties();
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
		try {
			pros.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			source = DruidDataSourceFactory.createDataSource(pros);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnection() throws Exception{
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
//		try {
//			DbUtils.close(connection);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			DbUtils.close(rs);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			DbUtils.close(ps);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		DbUtils.closeQuietly(connection);
		DbUtils.closeQuietly(ps);
		DbUtils.closeQuietly(rs);
	}
//	public static void closeResource(Connection connection,Statement ps,ResultSet rs){
//		try {
//			if (ps != null) {
//				ps.close();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			if (connection != null) {
//				connection.close();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			if (rs != null) {
//				rs.close();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	
}

