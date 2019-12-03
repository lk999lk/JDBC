package connection;

import java.sql.Connection;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;


public class C3P0Test {
	//方式1：
	@Test
	public void testGetConnection() throws Exception{
		//获取c3p0数据库连接池
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass("com.mysql.jdbc.Driver");//loads the jdbc driver
		cpds.setJdbcUrl("jdbc:mysql://localhost:3306/test");
		cpds.setUser("root");                                  
		cpds.setPassword("123456"); 
		
		//通过设置相关的参数对数据库进行管理
		
		//设置初始时数据库连接池中的连接数
		cpds.setInitialPoolSize(10);
		
		Connection connection = cpds.getConnection();
		System.out.println(connection);
		
		//销毁连接池：一般不执行
		//DataSources.destroy（cpds）;
		
	}
	
	//方式2：使用配置文件
	@Test
	public void testGetConnection1() throws Exception{
		ComboPooledDataSource cpds = new ComboPooledDataSource("hellc3p0");
		Connection connection = cpds.getConnection();
		System.out.println(connection);
		
	}
	
	
}
