package com.setvect.bokslmusic.service.code;

import com.setvect.common.util.SearchListVo;

/**
 * 코드 목록 페이징 및 검색 조건
 * 
 * @version $Id$
 */
public class CodeSearch extends SearchListVo {

	/** */
	private static final long serialVersionUID = -1949906111337073961L;

	/** 메인 코드 */
	private String searchMainCode;

	/** 코드값, like 검색 */
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
