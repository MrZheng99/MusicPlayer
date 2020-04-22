package com.zj.musicplayer.utils;

public class DateUtil {
	public static String millisecondToMinuteSecond(String millisecond) {
		int minute = (int) Integer.parseInt(millisecond) / 60000;
		int second = (Integer.parseInt(millisecond) - minute * 60000) / 1000;
		String minuteSecond = "";
		if (minute < 10) {
			minuteSecond = minuteSecond + "0" + minute + ":";
		} else {
			minuteSecond = minuteSecond + minute + ":";

		}
		if (second < 10) {
			minuteSecond = minuteSecond + "0" + second;
		} else {
			minuteSecond += second;

		}
		return minuteSecond;
	}
}
