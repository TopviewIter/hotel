package com.xqx.www.util;

import java.util.UUID;

/**
 * ×Ö·û´®¹¤¾ßÀà
 * @author xqx
 *
 */
public class StringUtil {

	public static final String DEFAULT_VALUE = "";
	
	public static boolean isNotEmpty(String str) {
		return str != null && !"".equals(str);
	}
	
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
