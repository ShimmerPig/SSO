package demo.sso.utils;

//�ַ���������
public class StringUtil {

	private StringUtil() {}

	//�ж��ַ����Ƿ�Ϊ��
	public static boolean isEmpty(String str) {
		if (str == null) {
			return true;
		}

		if (str.trim().length() == 0) {
			return true;
		}

		return false;
	}
}
