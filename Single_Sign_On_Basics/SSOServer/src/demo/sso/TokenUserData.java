package demo.sso;

import java.util.HashMap;
import java.util.Map;

import demo.sso.pojo.User;

//User����ά���ľ�̬������
public class TokenUserData {
	private TokenUserData() {}
	
	//�洢���ݵĶ���
	private static Map<String, User>dataMap=new HashMap<>();
	
	//�������ƴ洢
	public static void addToken(String token,User user) {
		dataMap.put(token, user);
	}
	
	//��֤���ƣ���������Ч�򷵻�user���󣬷��򷵻�null
	public static User validateToken(String token) {
		return dataMap.get(token);
	}
	
	//�Ƴ�token
	public static void removeToken(String token) {
		dataMap.remove(token);
	}
}
