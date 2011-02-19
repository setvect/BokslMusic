package com.setvect.bokslmusic;

import java.util.List;

import com.setvect.bokslmusic.config.EnvirmentProperty;

/**
 * ������Ʈ �������� ��� ����
 * 
 * @version $Id: ConstraintProject.java 128 2010-10-10 11:54:38Z
 *          setvect@naver.com $
 */
public class ConstraintProject {
	/** ���мҳ�: �⺻ Ʋ�� ���� �� �̸� */
	public final static String PMS_LAYOUT = "pms/layout";

	/** ���ε� ���� URL */
	public final static String UPLOAD_URL_BASE = EnvirmentProperty.getString("com.setvect.bokslmusic.file_upload_url");

	public enum AttributeKey {
		// jsp�� ������ ������ ������ ����
		INCLUDE_PAGE,
		// ���� �ּ� ����
		SERVLET_URL,
		// �Խ��� ��ü ����
		BOARD_ITEMS,
		// ���� ��Ʈ�ѷ����� �����ϴ� �� ��
		MODEL_VIEW,
		// ������Ʈ������ ���Ե� URL ����
		RETURN_URL,
	}

	/**
	 * ��з� �ڵ�
	 * 
	 * @version $Id$
	 */
	public enum Code {
		/** */
		ROOT,
		/** �����ηº� �ܰ� */
		MM_COST,
		/** ���� �׸� */
		PRD_COST,
		/** ���� �׸� */
		SALE,
		/** ������Ʈ �з� */
		PRJ_CLASSIFY,
		/** ���� �з� */
		POSITION
	}

	/**
	 * Ư���� �ڵ� ��
	 */
	public enum CodeSpecial {
		// ���� �׸�
		MM, OUT_MM, GOODS, DIRECT, BUSINESS
	}

	/**
	 * ������Ʈ Ʈ�� ǥ�ÿ� ���õ� ��� ��
	 */
	public static class TreeMenu {
		public static final String COOKIE_NAME = "_tree_type";
		public static final String TYPE_YEAR = "year";
		public static final String TYPE_SITE_NAME = "siteName";
	}

	/** ���ε� ���� Ȯ���� */
	public final static String[] ALLOW_UPLOAD_FILE;

	/** �ǵ� ���� ���� */
	public static String FEED_TYPE = "rss_2.0";

	static {
		@SuppressWarnings("unchecked")
		List<String> s = EnvirmentProperty.getList("com.setvect.bokslmusic.allow_upload_file");
		ALLOW_UPLOAD_FILE = new String[s.size()];
		for (int i = 0; i < s.size(); i++) {
			ALLOW_UPLOAD_FILE[i] = s.get(i).toLowerCase().trim();
		}
	}

	/** �н����� ��ȣȭ �˰��� */
	public final static String PASSWD_ALGORITHM = "MD5";

	/** �α��� ��Ű Ű�� */
	public static final String USER_COOKIE_KEY = "_user_cookie_key";

	/** ���̵� ���� ��Ű �̸� */
	public static final String USER_COOKIE_ID = "_user_cookie_id";

	/** �α��� attribute Ű�� */
	public static final String USER_SESSION_KEY = "_user_session_key";

	/** ���� URL �Ķ���� �̸� */
	public static final String RETURN_URL = "returnUrl";

	/** ����ȭ�� �Խ��� �ڵ� */
	public static final String NOTICE_BOARD = "NOTICE";

	/**
	 * ���� ��¥�� ���� Man Day <br>
	 * 21.5day == 1Man Day
	 */
	// 2011-02-09 ������
	// public static final double DAY_OF_MM = 21.5;

}
