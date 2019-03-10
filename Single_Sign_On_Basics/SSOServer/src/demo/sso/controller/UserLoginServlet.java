package demo.sso.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import demo.sso.TokenUserData;
import demo.sso.pojo.User;
import demo.sso.service.UserAccountService;
import demo.sso.utils.KeyGenerator;
import demo.sso.utils.StringUtil;

//�����û������¼�ķ���
@WebServlet("/login")
public class UserLoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	private UserAccountService userAccountService=new UserAccountService();
	
	//get������ʽ���������¼ҳ
	//�п������û�������Ҫ���е�¼
	//Ҳ�п������û���������ҳ�� ��ʦ����Ȩ������ ��ת���������¼ҳ��
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//��ȡ����url�еĲ�����ԭʼurl
		String origUrl=req.getParameter("origUrl");
		//��ԭʼurl���õ��������������У�������һ��ҳ���ȡԭʼurl����
		req.setAttribute("origUrl", origUrl);
		//��ȡ��ԭʼ��Url֮������ת��loginҳ��
		//����login���ӣ���ת����Ŀ�е�login.jspsҳ��
		req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);
	}
	
	//post������ʽ������¼���ύʱ����
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//��ȡ���е���ز���
		String account=req.getParameter("account");
		String passwd=req.getParameter("passwd");
		String origUrl=req.getParameter("origUrl");
		
		//��account�����ݿ��в����û�
		User user=null;
		try {
			user=userAccountService.findUserByAccount(account);
			//�ҵ����û��������ж������Ƿ���ȷ
			if(user!=null) {
				if(user.getPasswd().equals(passwd)) {
					//������ȷ������½�ɹ�
					//����token
					//��token�����Ӧ��user �ŵ�ȫ��Ψһ���ݽṹ��
					//����һ��cookie valueΪtoken
					//��ת��ԭ����url����
					
					//1.����token
					String token=KeyGenerator.generate();
					//2.��token�����Ӧ��user �ŵ�ȫ��Ψһ���ݽṹ��
					//��������ݽṹʹ��һ����(������һ��map�ṹ)������ֻ��static�����Ժͷ���
					//���������Ҳ��ȫ�ֵ�
					TokenUserData.addToken(token, user);
					//3.����cookie,vlaueΪtoken
					Cookie tokenCookie=new Cookie("token", token);
					tokenCookie.setPath("/");
					tokenCookie.setHttpOnly(true);
					resp.addCookie(tokenCookie);
					
					//4.��ԭ����Ϊ�գ�����ת��ԭ����url����
					if(StringUtil.isEmpty(origUrl)) {
						//ԭ����Ϊnull,����ת����¼ҳ��
						origUrl="login_success";
					}else {
						//���н������
						origUrl=URLDecoder.decode(origUrl,"utf-8");
					}
					//ҳ����ת
					resp.sendRedirect(origUrl);
				//���벻��ȷ������ԭ����loginҳ����е�¼����
				}else {
					backToLoginPage(req, resp, account, origUrl, "���벻��ȷ");
				}
			//���Ҳ����û�������ԭ����loginҳ����е�¼����
			}else {
				backToLoginPage(req, resp, account, origUrl, "�û�������");
			}
		}catch(SQLException e) {
			e.printStackTrace();
			backToLoginPage(req, resp, account, origUrl, "����ϵͳ����!");
		}
	}
	
	//���ص�¼ҳ��Ĳ���
	//Я����ԭ����url�Լ�������Ϣ
	private void backToLoginPage(HttpServletRequest req,
			HttpServletResponse resp,String account,
			String origUrl,String errInfo)throws ServletException,IOException{
		//����req�����ԣ�����loginҳ���ȡ
		req.setAttribute("account", account);
		req.setAttribute("origUrl", origUrl);
		req.setAttribute("errInfo", errInfo);
		
		//ҳ����ת
		req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);
	}
	
}
