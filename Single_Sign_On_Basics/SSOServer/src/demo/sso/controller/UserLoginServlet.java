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

//处理用户请求登录的服务
@WebServlet("/login")
public class UserLoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	private UserAccountService userAccountService=new UserAccountService();
	
	//get的请求方式——进入登录页
	//有可能是用户本来就要进行登录
	//也有可能是用户访问其他页面 大师由于权限问题 跳转到了这个登录页面
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//获取请求url中的参数的原始url
		String origUrl=req.getParameter("origUrl");
		//将原始url设置到请求对象的属性中，方便下一个页面获取原始url数据
		req.setAttribute("origUrl", origUrl);
		//获取了原始的Url之后再跳转到login页面
		//访问login链接，跳转到项目中的login.jsps页面
		req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);
	}
	
	//post的请求方式——登录表单提交时处理
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//获取表单中的相关参数
		String account=req.getParameter("account");
		String passwd=req.getParameter("passwd");
		String origUrl=req.getParameter("origUrl");
		
		//按account到数据库中查找用户
		User user=null;
		try {
			user=userAccountService.findUserByAccount(account);
			//找到了用户——》判断密码是否正确
			if(user!=null) {
				if(user.getPasswd().equals(passwd)) {
					//密码正确——登陆成功
					//生成token
					//将token与其对应的user 放到全局唯一数据结构中
					//设置一个cookie value为token
					//跳转到原来的url请求
					
					//1.生成token
					String token=KeyGenerator.generate();
					//2.将token与其对应的user 放到全局唯一数据结构中
					//这里的数据结构使用一个类(其中有一个map结构)，类中只有static的属性和方法
					//所以这个类也是全局的
					TokenUserData.addToken(token, user);
					//3.设置cookie,vlaue为token
					Cookie tokenCookie=new Cookie("token", token);
					tokenCookie.setPath("/");
					tokenCookie.setHttpOnly(true);
					resp.addCookie(tokenCookie);
					
					//4.若原请求不为空，则跳转到原来的url请求
					if(StringUtil.isEmpty(origUrl)) {
						//原请求为null,则跳转到登录页面
						origUrl="login_success";
					}else {
						//进行解码操作
						origUrl=URLDecoder.decode(origUrl,"utf-8");
					}
					//页面跳转
					resp.sendRedirect(origUrl);
				//密码不正确，返回原来的login页面进行登录操作
				}else {
					backToLoginPage(req, resp, account, origUrl, "密码不正确");
				}
			//查找不到用户，返回原来的login页面进行登录操作
			}else {
				backToLoginPage(req, resp, account, origUrl, "用户不存在");
			}
		}catch(SQLException e) {
			e.printStackTrace();
			backToLoginPage(req, resp, account, origUrl, "发生系统错误!");
		}
	}
	
	//返回登录页面的操作
	//携带着原来的url以及错误信息
	private void backToLoginPage(HttpServletRequest req,
			HttpServletResponse resp,String account,
			String origUrl,String errInfo)throws ServletException,IOException{
		//设置req的属性，方便login页面获取
		req.setAttribute("account", account);
		req.setAttribute("origUrl", origUrl);
		req.setAttribute("errInfo", errInfo);
		
		//页面跳转
		req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);
	}
	
}
