package demo.sso.utils;

//×Ö·û´®¹¤¾ßÀà
public class StringUtil {

	private StringUtil() {}

	//ÅÐ¶Ï×Ö·û´®ÊÇ·ñÎª¿Õ
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
