package demo.sso.utils;

import java.sql.Connection;
import java.sql.DriverManager;

//JDBC辅助类
//操作数据库的工具类
public class JDBCHelper{
	//构造方法为private的——禁止实例化
	private JDBCHelper() {}
	
	//获取数据库连接
	public static Connection getConnection() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost:3306/mydb";
		String user="root";
		String passwd="zhu1998";
		return DriverManager.getConnection(url,user,passwd);
	}
}