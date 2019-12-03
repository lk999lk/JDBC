import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @Description:
 * @author: lk
 * @create: 2019-12-02   22:18
 */
public class test {
    public static void main(String[] args) throws Exception {

        ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
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


    }

}
