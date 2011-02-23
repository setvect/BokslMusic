package com.setvect.bokslmusic.log;

import java.io.File;
import java.net.URL;

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
		DOMConfigurator.configure(logFilePath.getPath());
	}

	/**
	 * �α� ������ �ʱ�ȭ ��
	 * 
	 * @param logFilePath
	 *            �α� ���� ���� ���
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
	 *            �α� �Ž���
	 */
	public static void log(String message) {
		out.info(message);
	}

}