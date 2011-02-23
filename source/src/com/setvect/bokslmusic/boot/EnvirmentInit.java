package com.setvect.bokslmusic.boot;

import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.setvect.bokslmusic.config.EnvirmentProperty;
import com.setvect.bokslmusic.db.DBInitializer;
import com.setvect.bokslmusic.log.SyncLogPrinter;
import com.setvect.common.http.MultiFileCommonsMultipartResolver;
import com.setvect.common.log.LogPrinter;

/**
 * WAS�� ����Ǹ� ���ø����̼ǿ� �⺻���� ������, �α׼������� ���ش�. <br>
 * $Id: EnvirmentInit.java 111 2010-09-23 18:37:27Z setvect@naver.com $
 */
@SuppressWarnings("serial")
public class EnvirmentInit extends HttpServlet {
	/** �ʱ�ȭ ���� */
	private static boolean initialize = false;
	private static ClassPathXmlApplicationContext springContext;

	public EnvirmentInit() {
	}

	public void init() throws ServletException {
		super.init();
		bootUp();
	}

	/**
	 * @return the springContext
	 */
	public static ClassPathXmlApplicationContext getSpringContext() {
		return springContext;
	}

	/**
	 * config propertity, log4j, spring, hibernate ���� �ʱ�ȭ
	 * 
	 * @param webBase
	 *            ����Ʈ ���
	 */
	public static void bootUp() {
		if (initialize) {
			return;
			// throw new IllegalStateException("aready initialized!");
		}
		URL configUrl = EnvirmentInit.class.getClassLoader().getResource("/config/config.properties");
		EnvirmentProperty.init(configUrl);

		URL log4j = EnvirmentInit.class.getResource("/config/log4j.xml");
		LogPrinter.init(log4j);
		SyncLogPrinter.init(log4j);
		LogPrinter.out.info("Log Manager Initialized");

		springContext = new ClassPathXmlApplicationContext(new String[] { "classpath:config/applicationContext.xml" },
				false);
		springContext.refresh();

		LogPrinter.out.info("Spring Initialized");

		// ���� ���ε� Ȯ���� ����
		// TODO ���� �ȵ�.. ���߿� �ذ�
		MultiFileCommonsMultipartResolver mfmr = (MultiFileCommonsMultipartResolver) springContext
				.getBean("multipartResolver");
		mfmr.setCkFile(CommonUtil.checkAllowUploadFile());

		// DB init
		// H2 ������ ���̽� ���� ���� ��� ����. Spring Initialized ���� �ؾߵ�
		if (System.getProperty("h2.baseDir") == null) {
			System.setProperty("h2.baseDir", EnvirmentProperty.getString("com.setvect.bokslmusic.db.path"));
		}

		DBInitializer conn = (DBInitializer) springContext.getBean("db.initializer");
		conn.init();

		conn.makeTable();
		LogPrinter.out.info("DB Initialized");

		initialize = true;
	}

	public void destroy() {
		super.destroy();
	}
}