package com.setvect.bokslmusic.log;

import java.io.File;

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
		// Use a PropertyConfigurator to initialize from a property file.
		DOMConfigurator.configure(logFilePath.getPath());
	}

	/**
	 * @param message
	 *            로그 매시지
	 */
	public static void log(String message) {
		out.info(message);
	}
}