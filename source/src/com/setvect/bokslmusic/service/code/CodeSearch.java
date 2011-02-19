package com.setvect.bokslmusic.service.code;

import com.setvect.common.util.SearchListVo;

/**
 * �ڵ� ��� ����¡ �� �˻� ����
 * 
 * @version $Id$
 */
public class CodeSearch extends SearchListVo {

	/** */
	private static final long serialVersionUID = -1949906111337073961L;

	/** ���� �ڵ� */
	private String searchMainCode;

	/** �ڵ尪, like �˻� */
	private String searchValue;

	public CodeSearch(int currentPage) {
		super(currentPage);
	}

	/**
	 * @return the searchMainCode
	 */
	public String getSearchMainCode() {
		return searchMainCode;
	}

	/**
	 * @param searchMainCode
	 *            the searchMainCode to set
	 */
	public void setSearchMainCode(String searchName) {
		this.searchMainCode = searchName;
	}

	/**
	 * @return the searchValue
	 */
	public String getSearchValue() {
		return searchValue;
	}

	/**
	 * @param searchValue
	 *            the searchValue to set
	 */
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
}
