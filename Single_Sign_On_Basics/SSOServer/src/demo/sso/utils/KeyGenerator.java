package demo.sso.utils;

import java.util.UUID;

//����Ψһ��ʶ
public class KeyGenerator {

	private KeyGenerator() {
	}


	//���ɱ�ʶ,��32λ16�����ַ���ɣ���ĸСд
	public static String generate() {
		return UUID.randomUUID().toString().replace("-", "").toLowerCase();
	}
}
