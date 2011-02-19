package com.setvect.bokslmusic.service.music;

import com.setvect.common.util.SearchListVo;

public class MusicArticleSearch extends SearchListVo {

	/** */
	private static final long serialVersionUID = 7408199949914095804L;

	/**
	 * 게시물 정렬 조건
	 * 
	 * @version $Id$
	 */
	public enum Order {
		NAME;
	}

	/** 게시물 정렬 조건 */
	private Order orderArticle = Order.NAME;

	private String searchName;
	private String searchCode;

	public MusicArticleSearch(int currentPage) {
		super(currentPage);
	}

}
