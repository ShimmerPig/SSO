package demo.sso.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import demo.sso.TokenUserData;
import demo.sso.utils.CookieUtil;

//用户注销——处理退出请求
//完成单点退出操作
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//从cookie中获取该用户对应的token的值
		String token=CookieUtil.getCookie(req, "token");
		
		//将这个token从map结构中移除
		if(token!=null) {
			TokenUserData.removeToken(token);
		}
		//将对应user的ecookie设置为无效的
		CookieUtil.removeCookie(resp, "token", "/", null);
		//向客户端输出信息
		resp.getWriter().write("logout success");
	}
}
