package demo.sso.utils;

import java.sql.Connection;
import java.sql.DriverManager;

//JDBC������
//�������ݿ�Ĺ�����
public class JDBCHelper{
	//���췽��Ϊprivate�ġ�����ֹʵ����
	private JDBCHelper() {}
	
	//��ȡ���ݿ�����
	public static Connection getConnection() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost:3306/mydb";
		String user="root";
		String passwd="zhu1998";
		return DriverManager.getConnection(url,user,passwd);
	}
}