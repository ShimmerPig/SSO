package demo.sso.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//cookie��������
public class CookieUtil{
	//���췽��Ϊprivate�ġ�����ֹʵ����
	private CookieUtil() {}
	
	//�����ƻ�ȡcookie
	public static String getCookie(HttpServletRequest req,String name) {
		//��ȡreq��cookie
		Cookie[]cookies=req.getCookies();
		if(null==cookies||StringUtil.isEmpty(name)) {
			return null;
		}
		for(Cookie cookie:cookies) {
			if(name.equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}
	
	//���cookie
	//ͬһ��cookie����name.path,domainҪȫ����ͬ
	public static void removeCookie(HttpServletResponse resp,String name,
			String path,String domain) {
		//����ͬ����cookie
		Cookie cookie=new Cookie(name, null);
		if(null!=path) {
			cookie.setPath(path);
		}
		if(null!=domain) {
			cookie.setDomain(domain);
		}
		//������Чʱ��Ϊ��Ч�ģ��ȼ��ڽ�cookie�����
		cookie.setMaxAge(-1000);
		//����ԭ��ͬ����cookie
		resp.addCookie(cookie);
	}
}