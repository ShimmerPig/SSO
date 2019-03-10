package demo.sso;

import java.util.HashMap;
import java.util.Map;

import demo.sso.pojo.User;

//User数据维护的静态工具类
public class TokenUserData {
	private TokenUserData() {}
	
	//存储数据的对象
	private static Map<String, User>dataMap=new HashMap<>();
	
	//新增令牌存储
	public static void addToken(String token,User user) {
		dataMap.put(token, user);
	}
	
	//验证令牌，若令牌有效则返回user对象，否则返回null
	public static User validateToken(String token) {
		return dataMap.get(token);
	}
	
	//移除token
	public static void removeToken(String token) {
		dataMap.remove(token);
	}
}
