package com.setvect.bokslmusic.boot;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import com.setvect.bokslmusic.ConstraintProject;
import com.setvect.common.http.CheckAllowUploadFile;
import com.setvect.common.util.FileUtil;
import com.setvect.common.util.StringUtilAd;

/**
 * ������Ʈ �������� ���� �޼ҵ� ����
 * 
 * @version $Id$
 */
public class CommonUtil {
	private static final String GET_SEARCH = "getSearch";

	/**
	 * @param request
	 * @param listName
	 *            displaytag ID �̸�
	 * @return displaytag �Ķ���Ͱ� ������ �ش� �Ķ������ ������ ��, ������ "currentPage" �Ķ���� ��,
	 *         �̰͵� ������ 1
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
	 *            displaytag ID �̸�
	 * @return diplaytag ������ ���� �Ķ���� �̸�
	 */
	public static String getPageParamName(String listName) {
		String pageParam = new ParamEncoder(listName).encodeParameterName(TableTagParameters.PARAMETER_PAGE);
		return pageParam;
	}

	/**
	 * search�� ���۵Ǵ� �޼ҵ带 �м��Ͽ� �ش� �̸��� ���� ������ ����
	 * 
	 * @param pageCondition
	 * @return �˻� ��
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

	/**
	 * ���� ���ε� üũ
	 */
	public static CheckAllowUploadFile checkAllowUploadFile() throws ApplicationException {
		return new CheckAllowUploadFile() {
			public boolean check(String filename) {
				String ext = FileUtil.getExt(filename).toLowerCase().trim();

				int a = StringUtilAd.indexOfAny(ext, ConstraintProject.ALLOW_UPLOAD_FILE);
				if (a == -1) {
					return false;
				}
				return true;
			}
		};
	}

}
