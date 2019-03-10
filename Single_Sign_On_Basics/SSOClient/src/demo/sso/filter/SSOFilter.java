package demo.sso.filter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.xml.sax.InputSource;

import demo.sso.pojo.User;
import demo.sso.utils.CookieUtil;

//过滤器
//SSO客户端模块Filter
public class SSOFilter implements Filter{
	//SSO server登录页面url
	private static final String SSO_LOGIN_URL="/server/login";
	//SSO servertoken验证页面url
	private static final String SSO_VALIDATE_URL="http://localhost:8080/server/validate";
	
	@Override
	public void destroy() {
		
	}

	//客户端向服务端发送请求的时候进行拦截操作
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse resp=(HttpServletResponse)response;
		
		//从客户端的请求中提取token
		//从客户端的cookie中进行提取
		String token=CookieUtil.getCookie(req, "token");
		
		//获取本次请求的完整路径，若后期要进行跳转到login页面的操作，则这个url作为原始url
		String origUrl=req.getRequestURL().toString();
		String queryStr=req.getQueryString();
		if(queryStr!=null) {
			origUrl+="?"+queryStr;
		}
		
		//token不存在，跳转到SSOServer的用户登录页面
		if(token==null) {
			//需要对原始的url进行编码
			resp.sendRedirect(SSO_LOGIN_URL+"?origUrl="
					+URLEncoder.encode(origUrl,"utf-8"));
		}else {
			//token存在，则发送http通信给服务端，进行token有效性的验证
			URL validateUrl=new URL(SSO_VALIDATE_URL+"?token="+token);
			HttpURLConnection conn=(HttpURLConnection)validateUrl.openConnection();
			conn.connect();
			
			//发送请求后 从连接中获取输入流
			//这里是堵塞式的，只有当输入流获取了数据，我们下面的代码才继续执行
			//否则将等待服务端的响应
			InputStream is=conn.getInputStream();
			
			byte[]buffer=new byte[is.available()];
			//将服务端返回的字符串转换成字节数组
			is.read(buffer);
			
			//将字节数组转换成字符串
			String ret=new String(buffer);
			
			//若token无效则服务端返回""
			//否则返回用户相关信息
			if(ret.length()==0) {
				//token无效  返回登录页面  并携带原始url
				resp.sendRedirect(SSO_LOGIN_URL + "?origUrl="
						+ URLEncoder.encode(origUrl, "utf-8"));
			//token有效 ，对user对象进行还原
			//并设置到req的属性中 方便下一个进行获取
			}else {
				String[]tmp=ret.split(";");
				User user=new User();
				for(int i=0;i<tmp.length;i++) {
					String[]attrs=tmp[i].split("=");
					switch(attrs[0]) {
					case "id":
						user.setId(Integer.parseInt(attrs[1]));
						break;
					case "name":
						user.setName(attrs[1]);
						break;
					case "account":
						user.setAccount(attrs[1]);
						break;
					}
				}
				//设置user 方便下一个获取
				req.setAttribute("user", user);
				//进入下一个拦截器
				chain.doFilter(req, resp);
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
