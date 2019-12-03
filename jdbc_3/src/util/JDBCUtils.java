package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 使用c3p0的数据库连接
 * @author x
 *
 */
public class JDBCUtils {
	
	private static ComboPooledDataSource cpds = new ComboPooledDataSource("hellc3p0");
	public static Connection getConnection() throws Exception{
		Connection connection = cpds.getConnection();
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
