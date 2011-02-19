package com.setvect.bokslmusic.boot;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.setvect.bokslmusic.config.EnvirmentProperty;
import com.setvect.bokslmusic.db.DBInitializer;
import com.setvect.common.http.MultiFileCommonsMultipartResolver;
import com.setvect.common.log.LogPrinter;

/**
 * WAS�� ����Ǹ� ���ø����̼ǿ� �⺻���� ������, �α׼������� ���ش�. <br>
 * $Id: EnvirmentInit.java 111 2010-09-23 18:37:27Z setvect@naver.com $
 */
@SuppressWarnings("serial")
public class EnvirmentInit extends HttpServlet {
	private static final String CONFIG_FILEPATH_PARAMETER_NAME = "config_file";

	/** �ʱ�ȭ ���� */
	private static boolean initialize = false;
	private static ClassPathXmlApplicationContext springContext;

	public EnvirmentInit() {
	}

	public void init() throws ServletException {
		super.init();

		ServletConfig config = getServletConfig();

		ServletContext sc = config.getServletContext();
		/*
		 * OS���忡�� ����Ʈ ���丮
		 */
		File webBase = new File(sc.getRealPath("/"));
		String conf = config.getInitParameter(CONFIG_FILEPATH_PARAMETER_NAME);
		bootUp(webBase, conf);
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
	 * @param configPath
	 *            ����Ʈ�� ���������� config ���� path ���
	 */
	public static void bootUp(File webBase, String configPath) {

		if (initialize) {
			return;
			// throw new IllegalStateException("aready initialized!");
		}

		File configFile = new File(webBase, configPath);
		EnvirmentProperty.init(configFile);

		File logFilePath = new File(webBase, EnvirmentProperty.getString("com.setvect.bokslmusic.log.config"));
		LogPrinter.init(logFilePath);
		LogPrinter.out.info("Log Manager Initialized");

		springContext = new ClassPathXmlApplicationContext(new String[] { "classpath:spring/applicationContext.xml" },
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