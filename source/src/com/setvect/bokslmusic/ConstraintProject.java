package com.setvect.bokslmusic;

import java.util.List;

import com.setvect.bokslmusic.config.EnvirmentProperty;

/**
 * 프로젝트 의존적인 상수 모음
 * 
 */
public class ConstraintProject {
	/** 문학소년: 기본 틀을 가진 뷰 이름 */
	public final static String PMS_LAYOUT = "pms/layout";

	/** 업로드 기준 URL */
	public final static String UPLOAD_URL_BASE = EnvirmentProperty.getString("com.setvect.bokslmusic.file_upload_url");

	public enum AttributeKey {
		// jsp에 전달할 인쿨루드 페이지 정보
		INCLUDE_PAGE,
		// 서비스 주소 저장
		SERVLET_URL,
		// 게시판 전체 정보
		BOARD_ITEMS,
		// 상위 컨트롤러에서 전달하는 뷰 모델
		MODEL_VIEW,
		// 쿼리스트링까지 포함된 URL 정보
		RETURN_URL,
	}

	/**
	 * 대분류 코드
	 * 
	 * @version $Id$
	 */
	public enum Code {
		/** */
		ROOT,
		/** 투입인력별 단가 */
		MM_COST,
		/** 원가 항목 */
		PRD_COST,
		/** 매출 항목 */
		SALE,
		/** 프로젝트 분류 */
		PRJ_CLASSIFY,
		/** 직급 분류 */
		POSITION
	}

	/**
	 * 특별한 코드 값
	 */
	public enum CodeSpecial {
		// 원가 항목
		MM, OUT_MM, GOODS, DIRECT, BUSINESS
	}

	/**
	 * 프로젝트 트리 표시에 관련된 상수 값
	 */
	public static class TreeMenu {
		public static final String COOKIE_NAME = "_tree_type";
		public static final String TYPE_YEAR = "year";
		public static final String TYPE_SITE_NAME = "siteName";
	}

	/** 업로드 파일 확장자 */
	public final static String[] ALLOW_UPLOAD_FILE;

	/** 피드 생성 버전 */
	public static String FEED_TYPE = "rss_2.0";

	static {
		@SuppressWarnings("unchecked")
		List<String> s = EnvirmentProperty.getList("com.setvect.bokslmusic.allow_upload_file");
		ALLOW_UPLOAD_FILE = new String[s.size()];
		for (int i = 0; i < s.size(); i++) {
			ALLOW_UPLOAD_FILE[i] = s.get(i).toLowerCase().trim();
		}
	}

	/** 패스워드 암호화 알고리즘 */
	public final static String PASSWD_ALGORITHM = "MD5";

	/** 로그인 쿠키 키값 */
	public static final String USER_COOKIE_KEY = "_user_cookie_key";

	/** 아이디 저장 쿠키 이름 */
	public static final String USER_COOKIE_ID = "_user_cookie_id";

	/** 로그인 attribute 키값 */
	public static final String USER_SESSION_KEY = "_user_session_key";

	/** 리턴 URL 파라미터 이름 */
	public static final String RETURN_URL = "returnUrl";

	/** 메인화면 게시판 코드 */
	public static final String NOTICE_BOARD = "NOTICE";

	/**
	 * 투입 날짜에 따른 Man Day <br>
	 * 21.5day == 1Man Day
	 */
	// 2011-02-09 사용안함
	// public static final double DAY_OF_MM = 21.5;

}
