package com.setvect.bokslmusic.service.music;

import com.setvect.common.util.SearchListVo;

public class MusicArticleSearch extends SearchListVo {

	/** */
	private static final long serialVersionUID = 7408199949914095804L;

	/**
	 * 정렬 조건
	 */
	public enum Order {
		FILE_NAME, TITLE, RUNNING_TIME;
	}

	/**
	 * 결합 조건
	 */
	public enum UnionCondition {
		AND, OR
	}

	private Order order = Order.FILE_NAME;
	private UnionCondition unionCondition = UnionCondition.AND;

	private String searchArtist;
	private String searchTitle;
	private String searchFileName;
	private String searchLyrics;

	public MusicArticleSearch(int currentPage) {
		super(currentPage);
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public UnionCondition getUnionCondition() {
		return unionCondition;
	}

	public void setUnionCondition(UnionCondition unionCondition) {
		this.unionCondition = unionCondition;
	}

	public String getSearchArtist() {
		return searchArtist;
	}

	public void setSearchArtist(String searchArtist) {
		this.searchArtist = searchArtist;
	}

	public String getSearchTitle() {
		return searchTitle;
	}

	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
	}

	public String getSearchFileName() {
		return searchFileName;
	}

	public void setSearchFileName(String searchFileName) {
		this.searchFileName = searchFileName;
	}

	public String getSearchLyrics() {
		return searchLyrics;
	}

	public void setSearchLyrics(String searchLyrics) {
		this.searchLyrics = searchLyrics;
	}
}
