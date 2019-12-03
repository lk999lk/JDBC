package util;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import bean.Customer;
import dao.CustomerDAOImpl;
import org.junit.Test;


public class CustomerDAOIpmlTest {
	private CustomerDAOImpl dao = new CustomerDAOImpl();
	
	@Test
	public void testInsert() {
		Connection connection = null;
		try {
			connection = JDBCUtils.getConnection();
			Customer customer = new Customer(1,"小飞fei","xiaofei@126.com",new Date(45613464684646L));
			dao.insert(connection, customer);
			System.out.println("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeResource(connection, null);
		}
	}

	@Test
	public void testDeleteById() {
		Connection connection = null;
		try {
			connection = JDBCUtils.getConnection();
			dao.deleteById(connection, 13);
			
			System.out.println("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeResource(connection, null);
		}
	}

	@Test
	public void testUpdateConnectionCustomer() {
		Connection connection = null;
		try {
			connection = JDBCUtils.getConnection();
			Customer customer = new Customer(19,"小飞","xiaofei@126.com",new Date(45613464684646L));
			dao.update(connection, customer);
			
			System.out.println("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeResource(connection, null);
		}
	}

	@Test
	public void testGetCustomerById() {
		Connection connection = null;
		try {
			connection = JDBCUtils.getConnection();
			Customer customer = dao.getCustomerById(connection, 24);
			System.out.println(customer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeResource(connection, null);
		}
	}

	@Test
	public void testGetAll() {
		Connection connection = null;
		try {
			connection = JDBCUtils.getConnection();
			
			List<Customer> list = dao.getAll(connection);
			list.forEach(System.out::println);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeResource(connection, null);
		}
	}

	@Test
	public void testGetCount() {
		Connection connection = null;
		try {
			connection = JDBCUtils.getConnection();
			Long count = dao.getCount(connection);
			System.out.println("表中的记录数为： " + count);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeResource(connection, null);
		}
	}

	@Test
	public void testGetMaxBirth() {
		Connection connection = null;
		try {
			connection = JDBCUtils.getConnection();
			Date maxBirth = dao.getMaxBirth(connection);
			
			System.out.println("最大的生日为：" + maxBirth);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeResource(connection, null);
		}
	}

}
