package util;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @Description:
 * @author: lk
 * @create: 2019-12-02   20:53
 */
public class JDBCUtil {
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    //获取连接
    public static Connection getConncetion() throws Exception {
        Connection connection = threadLocal.get();
        //获取ThreadLocal中的变量，如果不够，会自动创建副本
        if (connection == null){
            Properties pros = new Properties();
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
            pros.load(is);

            DataSource source = DruidDataSourceFactory.createDataSource(pros);
            connection = source.getConnection();
            threadLocal.set(connection);
        }
        return connection;
    }
    @Test
    public void test()throws Exception {
        Connection conncetion = getConncetion();
        System.out.println(conncetion);
    }

    //开启事务
    public static void beginTransaction() throws Exception {
        Connection conncetion = getConncetion();
        if (conncetion != null)
            conncetion.setAutoCommit(false);//开启事务，手动提交
    }

    //正常，提交事务
    public static void commitTransaction() throws Exception {
        Connection conncetion = getConncetion();
        if (conncetion != null)
            conncetion.commit();
    }

    //失败，回滚事务
    public static void rollbackTransaction() throws Exception {
        Connection conncetion = getConncetion();
        if (conncetion != null)
            conncetion.rollback();
    }

    public static void close(){
        Connection connection = null;
        try {
            connection = getConncetion();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        threadLocal.remove();
        connection = null;
    }
}
