package dao;

import bean.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/*
 * 此接口用来规范针对于customers表的常用操作
 */
public interface CustomerDAO {

	//将cust对象添加到数据库中
	void insert(Connection connection, Customer cust);
	
	//针对指定的id,删除表的一条记录
	void deleteById(Connection connection, int id);
	
	//针对内存中的cust对象去修改数据表中的记录
	void update(Connection connection, Customer cust);
	
	//针对指定的id查询得到对应的Customer对象
	Customer getCustomerById(Connection connection, int id);
	
	//查询表中的所有记录构成的集合
	List<Customer> getAll(Connection connection);
	
	//返回数据表中数据的条目数
	Long getCount(Connection connection);
	
	//返回数据表中的最大的生日
	Date getMaxBirth(Connection connection);
	
	
}
