package demo.sso.pojo;

import java.io.Serializable;

//�û�ʵ����
@SuppressWarnings("serial")
public class User implements Serializable{
	private Integer id;
	private String name;
	private String account;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}
