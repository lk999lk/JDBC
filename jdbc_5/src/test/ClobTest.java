package test;

import org.junit.Test;
import util.JDBCUtils;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClobTest {

	//向数据表customers中插入Clob类型的字段
	@Test
	public void testInsert() throws Exception{

		FileInputStream is = null;
		Connection conn = JDBCUtils.getConnection();
		String sql = "insert into customers(name,email,birth,photo,novel)values(?,?,?,?,?)";

		PreparedStatement ps = conn.prepareStatement(sql);

		ps.setObject(1,"李嘉欣");
		ps.setObject(2, "lijiaxin@qq.com");
		ps.setObject(3,"1970-06-20");

		is = new FileInputStream(new File("resources/李嘉欣.jpg"));
		ps.setBlob(4, is);

		File file = new File("resources/不忘初心.txt");
		is = new FileInputStream(file);
		Reader reader = new InputStreamReader(is,"utf-8");
		ps.setCharacterStream(5,reader,(int)file.length());

		ps.execute();

		reader.close();
		is.close();

		JDBCUtils.closeResource(conn, ps);

	}

	//查询数据表customers中txt类型的字段
	@Test
	public void testQuery(){
		Connection conn = null;
		PreparedStatement ps = null;
		InputStream is = null;
		FileOutputStream fos = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "select novel from customers where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 33);
			rs = ps.executeQuery();
			if(rs.next()){
				//将txt下载下来
				Reader reader = rs.getCharacterStream("novel");
				File file = new File("小说.txt");
				Writer writer = new BufferedWriter(new FileWriter(file));
				char[] buff = new char[1024] ;
				int len = -1;
				while(  (len = reader.read(buff)) != -1 ) {
					writer.write( buff,0, len  );
				}
				writer.close();
				reader.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{

			try {
				if(is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			JDBCUtils.closeResource(conn, ps, rs);
		}


	}

}
