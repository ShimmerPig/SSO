package demo.sso.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import demo.sso.TokenUserData;
import demo.sso.utils.CookieUtil;

//�û�ע�����������˳�����
//��ɵ����˳�����
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//��cookie�л�ȡ���û���Ӧ��token��ֵ
		String token=CookieUtil.getCookie(req, "token");
		
		//�����token��map�ṹ���Ƴ�
		if(token!=null) {
			TokenUserData.removeToken(token);
		}
		//����Ӧuser��ecookie����Ϊ��Ч��
		CookieUtil.removeCookie(resp, "token", "/", null);
		//��ͻ��������Ϣ
		resp.getWriter().write("logout success");
	}
}
