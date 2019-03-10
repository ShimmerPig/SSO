package demo.sso.service;

import java.sql.SQLException;

import demo.sso.dao.UserAccountDao;
import demo.sso.pojo.User;
import demo.sso.utils.StringUtil;

//用户与账号服务
//service层开发
public class UserAccountService {
	private UserAccountDao userAccountDao=new UserAccountDao();

	//提供按照账号查询用户的服务
	public User findUserByAccount(String account)throws SQLException{
		if(StringUtil.isEmpty(account)) {
			return null;
		}
		return userAccountDao.findUserByAccount(account);
	}
}
