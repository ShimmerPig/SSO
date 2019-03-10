package demo.sso.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import demo.sso.pojo.User;
import demo.sso.utils.JDBCHelper;

//用户与上号数据访问对象
//用户dao层开发
public class UserAccountDao {

	//提供按照账号查询用户的方法
	public User findUserByAccount(String account) throws SQLException {
		//查询使用的sql语句
		String sql = "select * from user where account=?";
		
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		User retUser=null;
		
		try {
			//获取数据库连接
			conn=JDBCHelper.getConnection();
			ps=conn.prepareStatement(sql);
			//设置sql语句中第一个？处的值
			ps.setString(1, account);
			
			//执行sql语句并将结果返回到rs中
			rs=ps.executeQuery();
			
			//将返回的结果封装成user对象
			if(rs.next()) {
				retUser=new User();
				retUser.setId(rs.getInt("id"));
				retUser.setName(rs.getString("name"));
				retUser.setAccount(rs.getString("account"));
				retUser.setPasswd(rs.getString("passwd"));
			}
			
			return retUser;
		}catch(Exception e) {
			e.printStackTrace();
			throw new SQLException(e);// 发生异常得新抛出
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

	}

}
