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

//������
//SSO�ͻ���ģ��Filter
public class SSOFilter implements Filter{
	//SSO server��¼ҳ��url
	private static final String SSO_LOGIN_URL="/server/login";
	//SSO servertoken��֤ҳ��url
	private static final String SSO_VALIDATE_URL="http://localhost:8080/server/validate";
	
	@Override
	public void destroy() {
		
	}

	//�ͻ��������˷��������ʱ��������ز���
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse resp=(HttpServletResponse)response;
		
		//�ӿͻ��˵���������ȡtoken
		//�ӿͻ��˵�cookie�н�����ȡ
		String token=CookieUtil.getCookie(req, "token");
		
		//��ȡ�������������·����������Ҫ������ת��loginҳ��Ĳ����������url��Ϊԭʼurl
		String origUrl=req.getRequestURL().toString();
		String queryStr=req.getQueryString();
		if(queryStr!=null) {
			origUrl+="?"+queryStr;
		}
		
		//token�����ڣ���ת��SSOServer���û���¼ҳ��
		if(token==null) {
			//��Ҫ��ԭʼ��url���б���
			resp.sendRedirect(SSO_LOGIN_URL+"?origUrl="
					+URLEncoder.encode(origUrl,"utf-8"));
		}else {
			//token���ڣ�����httpͨ�Ÿ�����ˣ�����token��Ч�Ե���֤
			URL validateUrl=new URL(SSO_VALIDATE_URL+"?token="+token);
			HttpURLConnection conn=(HttpURLConnection)validateUrl.openConnection();
			conn.connect();
			
			//��������� �������л�ȡ������
			//�����Ƕ���ʽ�ģ�ֻ�е���������ȡ�����ݣ���������Ĵ���ż���ִ��
			//���򽫵ȴ�����˵���Ӧ
			InputStream is=conn.getInputStream();
			
			byte[]buffer=new byte[is.available()];
			//������˷��ص��ַ���ת�����ֽ�����
			is.read(buffer);
			
			//���ֽ�����ת�����ַ���
			String ret=new String(buffer);
			
			//��token��Ч�����˷���""
			//���򷵻��û������Ϣ
			if(ret.length()==0) {
				//token��Ч  ���ص�¼ҳ��  ��Я��ԭʼurl
				resp.sendRedirect(SSO_LOGIN_URL + "?origUrl="
						+ URLEncoder.encode(origUrl, "utf-8"));
			//token��Ч ����user������л�ԭ
			//�����õ�req�������� ������һ�����л�ȡ
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
				//����user ������һ����ȡ
				req.setAttribute("user", user);
				//������һ��������
				chain.doFilter(req, resp);
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
