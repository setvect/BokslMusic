package com.setvect.bokslmusic.log;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * ���� ����ȭ �α�
 */
public class SyncLogPrinter {

	/** �α� ��� */
	private static Logger out;

	/** �ʱ�ȭ ���� */
	private static boolean init = false;

	/**
	 * �α� ������ �ʱ�ȭ ��
	 * 
	 * @param logFilePath
	 *            �α� ���� ���� ���
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
	 *            �α� �Ž���
	 */
	public static void log(String message) {
		out.info(message);
	}
}