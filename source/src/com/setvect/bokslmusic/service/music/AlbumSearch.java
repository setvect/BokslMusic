package com.setvect.bokslmusic.service.music;

import com.setvect.common.util.SearchListVo;

/**
 * 앨범 검색 정보
 */
public class AlbumSearch extends SearchListVo {
	/** */
	private static final long serialVersionUID = -3367855375111847962L;
	private String searchName;

	public AlbumSearch(int currentPage) {
		super(currentPage);
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

}
