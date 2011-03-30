package com.setvect.bokslmusic.log;

import java.io.File;
import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * 파일 동기화 로그<br>
 * 로그 정보가 Clear 되지 않으면, 메모리를 다량으로 차지 하여 에러가 날 수 있음. <br>
 * 그러나 용량은 미미하고, 단일 사용자를 위해 만들어 지기 때문에 해당 문제점은 무시
 */
public class SyncLogPrinter {

	/** 로그 기록 */
	private static Logger out;

	/** 초기화 여부 */
	private static boolean init = false;

	private static StringBuffer log = new StringBuffer();

	/**
	 * 로그 설정을 초기화 함
	 * 
	 * @param logFilePath
	 *            로그 파일 설정 경로
	 */
	public synchronized static void init(File logFilePath) {
		if (init) {
			throw new RuntimeException("configuration already setting");
		}
		out = Logger.getLogger(SyncLogPrinter.class);
		DOMConfigurator.configure(logFilePath.getPath());
	}

	/**
	 * 로그 설정을 초기화 함
	 * 
	 * @param logFilePath
	 *            로그 파일 설정 경로
	 */
	public static void init(URL logFilePath) {
		if (init) {
			throw new RuntimeException("configuration already setting");
		}
		out = Logger.getLogger(SyncLogPrinter.class);

		DOMConfigurator.configure(logFilePath);
	}

	/**
	 * @param message
	 *            로그 매시지
	 */
	public static void log(String message) {
		out.info(message);
		log.append("\n" + message);
	}

	/**
	 * @return 현재까지 저장된 로그 정보
	 */
	public static String getLog() {
		return log.toString();
	}

	/**
	 * 저장된 로그 값을 Clear 시킴
	 * 
	 * @return 현재까지 저장된 로그 정보
	 */
	public static String getLogAndClear() {
		String s = log.toString();
		clearForLog();
		return s;
	}

	/**
	 * 저장된 로그 값을 Clear 시킴
	 */
	private static void clearForLog() {
		log.setLength(0);
	}
}
