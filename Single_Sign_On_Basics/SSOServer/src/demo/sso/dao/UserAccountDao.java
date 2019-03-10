package demo.sso.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import demo.sso.pojo.User;
import demo.sso.utils.JDBCHelper;

//�û����Ϻ����ݷ��ʶ���
//�û�dao�㿪��
public class UserAccountDao {

	//�ṩ�����˺Ų�ѯ�û��ķ���
	public User findUserByAccount(String account) throws SQLException {
		//��ѯʹ�õ�sql���
		String sql = "select * from user where account=?";
		
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		User retUser=null;
		
		try {
			//��ȡ���ݿ�����
			conn=JDBCHelper.getConnection();
			ps=conn.prepareStatement(sql);
			//����sql����е�һ��������ֵ
			ps.setString(1, account);
			
			//ִ��sql��䲢��������ص�rs��
			rs=ps.executeQuery();
			
			//�����صĽ����װ��user����
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
			throw new SQLException(e);// �����쳣�����׳�
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
