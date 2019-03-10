package demo.sso.validate;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import demo.sso.TokenUserData;
import demo.sso.pojo.User;

//客户端token存在的时候，需要向服务器发起token是否有效的验证
//系统间的通信 这里使用http通信

//创建接口  验证token的有效性
@WebServlet("/validate")
public class TokenValidateServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	//处理客户端发起的get请求
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//获取token
		String token=req.getParameter("token");
		//验证这个token是否有效——这个token对应的user是否保存在map结构中
		User user=TokenUserData.validateToken(token);
		//token无效 向客户端写出null
		if(user==null) {
			resp.getWriter().write("");
		//token有效 向客户端写出对应的user信息
		}else {
			// 如果有中文，要考虑乱码问题，此处不处理
			resp.getWriter().write(
					"id=" + user.getId() + ";name=" + user.getName()
					+ ";account=" + user.getAccount());
		}
	}
}
