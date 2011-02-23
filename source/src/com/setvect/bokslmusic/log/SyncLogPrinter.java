package com.setvect.bokslmusic.log;

import java.io.File;
import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * 파일 동기화 로그
 */
public class SyncLogPrinter {

	/** 로그 기록 */
	private static Logger out;

	/** 초기화 여부 */
	private static boolean init = false;

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
	}

}