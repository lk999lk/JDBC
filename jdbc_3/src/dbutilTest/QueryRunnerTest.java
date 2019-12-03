package dbutilTest;

import bean.Customer;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;
import org.junit.Test;
import util.JDBCUtils3;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * commons-dbutils 是 Apache 组织提供的一个开源 JDBC工具类库，封装了增删改查操作
 * @author x
 *
 * QueryRunner();无参是手动提交，要配合Connection。
 * QueryRunner(DataSource ds)有参是自动提交，不需要Connection
 *
 */
public class QueryRunnerTest {

	//测试插入
	@Test
	public void testInsert() {
		Connection connection = null;
		try {
			QueryRunner runner = new QueryRunner();
			connection = JDBCUtils3.getConnection();
			String sql = "insert into customers(name,email,birth)values(?,?,?)";
			int insertCount = runner.update(connection, sql, "蔡徐坤","caixukun@126.com","1997-09-08");

			System.out.println("添加了"+ insertCount + "条记录");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils3.closeResource(connection, null);
		}
	}
	@Test
	public void testInsert2() {
		try {
			QueryRunner runner = new QueryRunner(JDBCUtils3.source);
			String sql = "insert into customers(name,email,birth)values(?,?,?)";
			int insertCount = runner.update(sql, "蔡徐坤","caixukun@126.com","1997-09-08");
			System.out.println("添加了"+ insertCount + "条记录");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//测试查询
	@Test
//	BeanHander:是ResultSetHandler接口的实现类，用于封装表中的一条记录。
	public void testQuery1() throws Exception{
		Connection connection = JDBCUtils3.getConnection();
		
		QueryRunner runner = new QueryRunner();
		String sql = "select id,name,email,birth from customers where id = ?";
		BeanHandler<Customer> handler = new BeanHandler<>(Customer.class);
		Customer customer = runner.query(connection, sql, handler, 24);
		
		System.out.println(customer);
		
	}

	@Test
	public void testQuery11() throws Exception{

		QueryRunner runner = new QueryRunner(JDBCUtils3.source);
		String sql = "select id,name,email,birth from customers where id = ?";
		BeanHandler<Customer> handler = new BeanHandler<>(Customer.class);
//		Customer customer = runner.query(sql, handler, 24);
		Object[] objects = runner.query(sql, new ArrayHandler(), 24);//返回一行放入Object[]
		System.out.println(objects[0]+"--"+objects[1]+"--"+objects[2]+"--"+objects[3]);

	}
	
	//BeanListHandler:是ResultSetHandler接口的实现类，用于封装表中的多条记录构成的集合。
	@Test
	public void testQuery2() throws Exception{
		Connection connection = JDBCUtils3.getConnection();
		
		QueryRunner runner = new QueryRunner();
		String sql = "select id,name,email,birth from customers where id > ?";
		BeanListHandler<Customer> handler = new BeanListHandler<>(Customer.class);
		
		List<Customer> list = runner.query(connection, sql, handler, 24);
		list.forEach(System.out::println);
		
	}

	//BeanMapHandler:是ResultSetHandler接口的实现类，用于封装表中的多条记录构成的集合。
	@Test
	public void testQuery22() throws Exception{
		Connection connection = JDBCUtils3.getConnection();

		QueryRunner runner = new QueryRunner();
		String sql = "select id,name,email,birth from customers where id > ?";
		BeanMapHandler<Integer, Customer> handler = new BeanMapHandler<>(Customer.class, "id");

		Map<Integer, Customer> map = runner.query(connection, sql, handler, 24);
		for (int key : map.keySet()) {
			System.out.println("key= "+ key + " and value= " + map.get(key));
		}
		Customer c = map.get(28);
		System.out.println(c);
	}


	//MapHander:是ResultSetHandler接口的实现类，对应表中的一条记录。
	//将字段及相应字段的值作为map中的key和value
	@Test
	public void testQuery3() throws Exception{
		Connection connection = JDBCUtils3.getConnection();
		QueryRunner runner = new QueryRunner();
		
		String sql = "select id,name,email,birth from customers where id = ?";
		MapHandler handler = new MapHandler();
		Map<String, Object> map = runner.query(connection, sql, handler,24);
		System.out.println(map);
	}
	/*
	 * MapListHander:是ResultSetHandler接口的实现类，对应表中的多条记录。
	 * 将字段及相应字段的值作为map中的key和value。将这些map添加到List中
	 */
	@Test
	public void testQuery4() throws Exception{
		Connection connection = JDBCUtils3.getConnection();
		QueryRunner runner = new QueryRunner();
		
		String sql = "select id,name,email,birth from customers where id > ? and name like ?";
		MapListHandler handler = new MapListHandler();
		List<Map<String,Object>> list = runner.query(connection, sql, handler,new Object[]{24,"%嘉欣%"});
		list.forEach(System.out::println);
	}
	/*
	 * ScalarHandler:用于查询特殊值
	 */
	@Test
	public void testQuery5() throws Exception{
		Connection connection = JDBCUtils3.getConnection();
		QueryRunner runner = new QueryRunner();
		
		String sql = "select count(*) from customers";
		ScalarHandler handler = new ScalarHandler();
		Long count = (Long) runner.query(connection, sql, handler);
		System.out.println(count);
	}

	@Test
	public void testQuery6() throws Exception{
		Connection connection = JDBCUtils3.getConnection();
		QueryRunner runner = new QueryRunner();
		
		String sql = "select max(birth) from customers";
		ScalarHandler handler = new ScalarHandler();
		Date maxBirth = (Date) runner.query(connection, sql, handler);
		System.out.println(maxBirth);
	}

	@Test
	public void testQuery66() throws Exception{
		QueryRunner runner = new QueryRunner(JDBCUtils3.source);

		String sql = "select name from customers where id = ?";
		ScalarHandler<String> handler = new ScalarHandler<>();
		String s = runner.query(sql, handler, 24);
		System.out.println(s);
	}

	/*
	 * 自定义ResultSetHandleder 的实现类
	 */
	@Test
	public void testQuery7() throws Exception{
		Connection connection = JDBCUtils3.getConnection();
		QueryRunner runner = new QueryRunner();
		
		String sql = "select id,name,email,birth from customers where id = ?";
		ResultSetHandler<Customer> handler = new ResultSetHandler<Customer>() {

			@Override
			public Customer handle(ResultSet rs) throws SQLException {
				if(rs.next()){
					int id = rs.getInt("id");
					String name = rs.getString("name");
					String email = rs.getString("email");
					Date birth = rs.getDate("birth");
					Customer customer = new Customer(id, name, email, birth);
					return customer;
				}
				return null;
			}
			
		};
		Customer customer = runner.query(connection, sql,handler,24);
		System.out.println(customer);
	}
	
	
	
}
