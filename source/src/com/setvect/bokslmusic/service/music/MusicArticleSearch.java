package com.setvect.bokslmusic.service.music;

import com.setvect.common.util.SearchListVo;

public class MusicArticleSearch extends SearchListVo {

	/** */
	private static final long serialVersionUID = 7408199949914095804L;

	/**
	 * �Խù� ���� ����
	 * 
	 * @version $Id$
	 */
	public enum Order {
		NAME;
	}

	/** �Խù� ���� ���� */
	private Order orderArticle = Order.NAME;

	private String searchName;
	private String searchCode;

	public MusicArticleSearch(int currentPage) {
		super(currentPage);
	}

}
