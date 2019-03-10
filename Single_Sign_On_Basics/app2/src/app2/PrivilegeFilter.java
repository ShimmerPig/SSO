package app2;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import demo.sso.pojo.User;


//ҵ��ϵͳ�Լ����û�Ȩ�޹�����
//�磺���������ܷ��ʸ�ҳ��QAQ
public class PrivilegeFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		// ����һ��Filter(SSOFilter)����������л�ȡuser����ִ�е��˴���һ�����ѵ�¼
		User user=(User)req.getAttribute("user");
		// ���Ը���user��Ϣ����뱾ϵͳ��ص��û�ҵ����Ϣ
		// Empl empl = EmplService.findEmplByUser(user.getId());
		
		// ʾ��һ������ϸ����Ҫ�ж�
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
