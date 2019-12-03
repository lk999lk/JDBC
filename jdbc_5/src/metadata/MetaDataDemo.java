package metadata;

import java.sql.*;

/**
 * @Description:
 * @author: lk
 * @create: 2019-12-03   11:35
 */
public class MetaDataDemo {
    private final static String DRIVER = "com.mysql.jdbc.Driver";
    private final static String URL = "jdbc:mysql://localhost:3306/test?generateSimpleParameterMetadata=true";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "123456";

    public static void databaseMetaData() throws Exception {
        //获取连接
        Class.forName(DRIVER);
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        //数据库元信息
        DatabaseMetaData metaData = connection.getMetaData();

        String databaseProductName = metaData.getDatabaseProductName();
        System.out.println("数据库名："+databaseProductName);

        String databaseProductVersion = metaData.getDatabaseProductVersion();
        System.out.println("数据库版本："+databaseProductVersion);

        String driverName = metaData.getDriverName();
        System.out.println("驱动："+driverName);

        String url = metaData.getURL();
        System.out.println("URL:"+url);

        String userName = metaData.getUserName();
        System.out.println("UserName:"+userName);

        System.out.println("------------------");

        //获取指定表中的主键信息
        ResultSet rs = metaData.getPrimaryKeys(null, userName, "CUSTOMERS");
        while (rs.next()){
            Object tableName = rs.getObject(3);
            Object columnName = rs.getObject(4);
            Object pkName = rs.getObject(6);//主键的约束名称
            System.out.println(tableName + "---" + columnName + "---" + pkName);

        }

    }

    public static void parameterMetaData() throws Exception {
        //获取连接
        Class.forName(DRIVER);
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        String sql = "select * from customers where name = ? and novel = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);

        //获取参数元数据
        ParameterMetaData metaData = pstmt.getParameterMetaData();

        int parameterCount = metaData.getParameterCount();
        System.out.println("参数个数："+parameterCount);

        for (int i=1; i<=parameterCount; i++){
            String typeName = metaData.getParameterTypeName(i);
            System.out.println(typeName);
        }
    }

    public static void resultsetMetaData() throws Exception {
        //获取连接
        Class.forName(DRIVER);
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        String sql = "select id,name,email,birth from customers where id > 24";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();

        int columnCount = metaData.getColumnCount();
        System.out.println("列的个数："+columnCount);

        for (int i=1; i<=columnCount; i++){
            String columnName = metaData.getColumnName(i);
            String columnTypeName = metaData.getColumnTypeName(i);
            String columnLabel = metaData.getColumnLabel(i);

            System.out.println(columnName+"\t"+columnTypeName+"\t"+columnLabel);
        }

        while (resultSet.next()){
            for (int i=1; i<=columnCount; i++){
                String columnLabel = metaData.getColumnLabel(i);
                System.out.print(columnLabel+"--"+resultSet.getObject(i)+"\t");
            }
            System.out.println();
        }


    }
    public static void main(String[] args) throws Exception {
//        databaseMetaData();
//        parameterMetaData();
        resultsetMetaData();


    }

}
