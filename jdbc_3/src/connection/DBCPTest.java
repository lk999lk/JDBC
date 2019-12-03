package connection;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

public class DBCPTest {

	/**
	 * 测试DBCP的数据库连接技术
	 * @throws SQLException 
	 */
	//方式1
	@Test
	public void testGetConnection() throws SQLException{
		//创建了DBCP的数据库连接池
		BasicDataSource source = new BasicDataSource();
		//设置基本的信息
		source.setDriverClassName("com.mysql.jdbc.Driver");
		source.setUrl("jdbc:mysql://localhost:3306/test");
		source.setUsername("root");
		source.setPassword("123456");
		
		//设置其他涉及数据库连接池管理的属性
		source.setInitialSize(10);
		source.setMaxActive(20);
		
		Connection connection = source.getConnection();
		System.out.println(connection);
	}
	
	//方式2：使用配置文件
	@Test
	public void testGetConnection1() throws Exception{
		Properties pros = new Properties();
		//方式1：
//		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
		//方式2
		InputStream is = new FileInputStream(new File("resources/dbcp.properties"));
		pros.load(is);
		DataSource source = BasicDataSourceFactory.createDataSource(pros);
		
		Connection connection = source.getConnection();
		System.out.println(connection);
	}
	
}
