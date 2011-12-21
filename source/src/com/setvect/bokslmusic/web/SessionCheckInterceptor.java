package com.setvect.bokslmusic.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 로그인 체크<br>
 * 모든 액션에 대해서 로그인 여부를 검사하여 로그인이 되지 않으면 로그인 페이지로 이동
 */
public class SessionCheckInterceptor extends HandlerInterceptorAdapter {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String currentUrl = request.getRequestURI();

		// 호출한 서블릿 주소(~~.do 시작하는)를 저장
		// JSP에서 form action에 주소로 사용
		request.setAttribute(ConstraintWeb.AttributeKey.SERVLET_URL.name(), currentUrl);
		return true;
	}
}