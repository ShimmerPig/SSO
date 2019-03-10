package demo.sso.validate;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import demo.sso.TokenUserData;
import demo.sso.pojo.User;

//�ͻ���token���ڵ�ʱ����Ҫ�����������token�Ƿ���Ч����֤
//ϵͳ���ͨ�� ����ʹ��httpͨ��

//�����ӿ�  ��֤token����Ч��
@WebServlet("/validate")
public class TokenValidateServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	//����ͻ��˷����get����
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//��ȡtoken
		String token=req.getParameter("token");
		//��֤���token�Ƿ���Ч�������token��Ӧ��user�Ƿ񱣴���map�ṹ��
		User user=TokenUserData.validateToken(token);
		//token��Ч ��ͻ���д��null
		if(user==null) {
			resp.getWriter().write("");
		//token��Ч ��ͻ���д����Ӧ��user��Ϣ
		}else {
			// ��������ģ�Ҫ�����������⣬�˴�������
			resp.getWriter().write(
					"id=" + user.getId() + ";name=" + user.getName()
					+ ";account=" + user.getAccount());
		}
	}
}
