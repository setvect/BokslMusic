package com.setvect.bokslmusic.service.music;

import com.setvect.common.util.SearchListVo;

/**
 * 재생 대상 음악 검색 
 */
public class PlayItemSearch extends SearchListVo {
	/** */
	private static final long serialVersionUID = -8437684242068170012L;
	private int searchAlbumSeq;

	public PlayItemSearch(int currentPage) {
		super(currentPage);
	}

	public int getSearchAlbumSeq() {
		return searchAlbumSeq;
	}

	public void setSearchAlbumSeq(int searchAlbumSeq) {
		this.searchAlbumSeq = searchAlbumSeq;
	}

}
