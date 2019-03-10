package demo.sso.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//cookie操作工具
public class CookieUtil{
	//构造方法为private的——禁止实例化
	private CookieUtil() {}
	
	//按名称获取cookie
	public static String getCookie(HttpServletRequest req,String name) {
		//获取req的cookie
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
	
	//清除cookie
	//同一个cookie——name.path,domain要全部相同
	public static void removeCookie(HttpServletResponse resp,String name,
			String path,String domain) {
		//创建同名的cookie
		Cookie cookie=new Cookie(name, null);
		if(null!=path) {
			cookie.setPath(path);
		}
		if(null!=domain) {
			cookie.setDomain(domain);
		}
		//设置有效时间为无效的，等价于将cookie清楚了
		cookie.setMaxAge(-1000);
		//覆盖原来同名的cookie
		resp.addCookie(cookie);
	}
}