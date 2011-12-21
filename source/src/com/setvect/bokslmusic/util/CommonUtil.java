package com.setvect.bokslmusic.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import com.setvect.common.util.StringUtilAd;

/**
 * 프로젝트 의존적인 공통 메소드 모음
 * 
 * @version $Id$
 */
public class CommonUtil {
	private static final String GET_SEARCH = "getSearch";

	/**
	 * @param request
	 * @return 웹사이트 도메인, 포트, Context Path 포함한 URL 기준
	 */
	public static String getContextUrl(HttpServletRequest request) {
		String basePath = request.getRequestURL().toString().replaceAll(request.getRequestURI(), "");
		basePath += request.getContextPath();
		return basePath;
	}

	/**
	 * @param request
	 * @param listName
	 *            displaytag ID 이름
	 * @return displaytag 파라미터가 있으면 해당 파라미터의 페이지 값, 없으면 "currentPage" 파라미터 값,
	 *         이것도 없으면 1
	 */
	public static int getCurrentPage(HttpServletRequest request, String listName) {
		String pageParam = getPageParamName(listName);
		String pageParamValue = request.getParameter(pageParam);

		if (StringUtilAd.isEmpty(pageParamValue)) {
			pageParamValue = request.getParameter("currentPage");
		}
		int currentPage = Integer.parseInt(StringUtilAd.null2str(pageParamValue, "1"));
		return currentPage;
	}

	/**
	 * @param listName
	 *            displaytag ID 이름
	 * @return diplaytag 페이지 정보 파라미터 이름
	 */
	public static String getPageParamName(String listName) {
		String pageParam = new ParamEncoder(listName).encodeParameterName(TableTagParameters.PARAMETER_PAGE);
		return pageParam;
	}

	/**
	 * search로 시작되는 메소드를 분석하여 해당 이름과 값을 맵으로 만듬
	 * 
	 * @param pageCondition
	 * @return 검색 맵
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static Map<String, Object> getSearchMap(Object pageCondition) throws Exception {
		Method[] methods = pageCondition.getClass().getMethods();
		Map<String, Object> param = new HashMap<String, Object>();
		for (Method m : methods) {
			String name = m.getName();
			if (!name.startsWith(GET_SEARCH)) {
				continue;
			}
			Object v = m.invoke(pageCondition, new Object[0]);
			String key = "search" + name.substring(GET_SEARCH.length());
			param.put(key, v);
		}
		return param;
	}
}
