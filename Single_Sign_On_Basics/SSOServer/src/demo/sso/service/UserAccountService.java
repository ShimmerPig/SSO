package demo.sso.service;

import java.sql.SQLException;

import demo.sso.dao.UserAccountDao;
import demo.sso.pojo.User;
import demo.sso.utils.StringUtil;

//�û����˺ŷ���
//service�㿪��
public class UserAccountService {
	private UserAccountDao userAccountDao=new UserAccountDao();

	//�ṩ�����˺Ų�ѯ�û��ķ���
	public User findUserByAccount(String account)throws SQLException{
		if(StringUtil.isEmpty(account)) {
			return null;
		}
		return userAccountDao.findUserByAccount(account);
	}
}
