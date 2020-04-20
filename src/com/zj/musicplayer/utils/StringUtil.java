package com.zj.musicplayer.utils;

public class StringUtil {
	/**
	 * 判断字符串是否为空
	 * 
	 * @param strs
	 * @return
	 */
	public static boolean checkNull(String... strs) {
		if (strs == null || strs.length <= 0) {
			return true;
		}
		for (String str : strs) {
			if (str == null || "".equals(str)) {
				return true;
			}
		}
		return false;
	}
}
