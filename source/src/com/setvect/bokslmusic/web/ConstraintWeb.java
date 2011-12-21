package com.setvect.bokslmusic.web;


/**
 * Web UI에서 사용되는 상수 모음
 * 
 * @version $Id: ConstraintWeb.java 128 2010-10-10 11:54:38Z setvect@naver.com $
 */
public class ConstraintWeb {
	/** 문학소년: 기본 틀을 가진 뷰 이름 */
	public final static String SEARCH_LAYOUT = "main/layout";

	public enum AttributeKey {
		// jsp에 전달할 인쿨루드 페이지 정보
		INCLUDE_PAGE,
		// 서비스 주소 저장
		SERVLET_URL,
		// 상위 컨트롤러에서 전달하는 뷰 모델
		MODEL_VIEW,

	}

	/** 패스워드 암호화 알고리즘 */
	public final static String PASSWD_ALGORITHM = "MD5";

	/** 로그인 쿠키 키값 */
	public static final String USER_COOKIE_KEY = "_user_cookie_key";

	/** 로그인 attribute 키값 */
	public static final String USER_SESSION_KEY = "_user_session_key";

	/** 리턴 URL 파라미터 이름 */
	public static final String RETURN_URL = "returnUrl";


}
