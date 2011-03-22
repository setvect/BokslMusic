package com.setvect.bokslmusic.ui.client.util;

import com.google.gwt.i18n.client.NumberFormat;

public class ClientUtil {

	// not instance
	private ClientUtil() {
	}

	/**
	 * 리턴 형식 예<br/>
	 * 01:02:32 <br>
	 * 00:00:21 <br>
	 * 
	 * @param sec
	 *            초
	 * @return 초를 분과 초로 변환된 문자열
	 */
	public static String getHourMinuteSec(int sec) {
		int h = sec / (60 * 60);
		sec = sec % (60 * 60);
		int m = sec / 60;
		int s = sec % 60;
		NumberFormat numbrFormat = NumberFormat.getFormat("00");
		return numbrFormat.format(h) + ":" + numbrFormat.format(m) + ":" + numbrFormat.format(s);
	}

	public static String getMinuteSec(int sec) {
		int m = sec / 60;
		int s = sec % 60;
		NumberFormat numbrFormat = NumberFormat.getFormat("00");
		return numbrFormat.format(m) + ":" + numbrFormat.format(s);
	}
}
