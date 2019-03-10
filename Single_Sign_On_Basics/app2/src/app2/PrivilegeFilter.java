package app2;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import demo.sso.pojo.User;


//业务系统自己的用户权限过滤器
//如：“猪猪”不能访问该页面QAQ
public class PrivilegeFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		// 从上一个Filter(SSOFilter)存入的属性中获取user，能执行到此处，一定是已登录
		User user=(User)req.getAttribute("user");
		// 可以根据user信息获得与本系统相关的用户业务信息
		// Empl empl = EmplService.findEmplByUser(user.getId());
		
		// 示范一个更详细的需要判定
		String acc=user.getAccount();
		if("z".equals(acc)) {
			resp.getWriter().write("zhuzhu is forbidden!");
		}else {
			chain.doFilter(req,resp);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
}
