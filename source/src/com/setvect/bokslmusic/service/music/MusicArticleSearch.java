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

	/** 아티스트(Like 검색) */
	private String searchArtist;
	/** 제목(Like 검색) */
	private String searchTitle;
	/** 파일 이름(Like 검색) */
	private String searchFileName;
	/** 가사(Like 검색) */
	private String searchLyrics;
	/** 디렉토리 경로(equals 검색) */
	private String searchPath;

	/** 디렉토리 경로(우측 like 검색 'abcde%') */
	private String searchPathParent;

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

	public String getSearchPath() {
		return searchPath;
	}

	public void setSearchPath(String searchPath) {
		this.searchPath = searchPath;
	}

	/**
	 * @return the searchPathParent
	 */
	public String getSearchPathParent() {
		return searchPathParent;
	}

	/**
	 * @param searchPathParent
	 *            the searchPathParent to set
	 */
	public void setSearchPathParent(String searchPathParent) {
		this.searchPathParent = searchPathParent;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		result = prime * result + ((searchArtist == null) ? 0 : searchArtist.hashCode());
		result = prime * result + ((searchFileName == null) ? 0 : searchFileName.hashCode());
		result = prime * result + ((searchLyrics == null) ? 0 : searchLyrics.hashCode());
		result = prime * result + ((searchPath == null) ? 0 : searchPath.hashCode());
		result = prime * result + ((searchPathParent == null) ? 0 : searchPathParent.hashCode());
		result = prime * result + ((searchTitle == null) ? 0 : searchTitle.hashCode());
		result = prime * result + ((unionCondition == null) ? 0 : unionCondition.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MusicArticleSearch)) {
			return false;
		}
		MusicArticleSearch other = (MusicArticleSearch) obj;
		if (order != other.order) {
			return false;
		}
		if (searchArtist == null) {
			if (other.searchArtist != null) {
				return false;
			}
		}
		else if (!searchArtist.equals(other.searchArtist)) {
			return false;
		}
		if (searchFileName == null) {
			if (other.searchFileName != null) {
				return false;
			}
		}
		else if (!searchFileName.equals(other.searchFileName)) {
			return false;
		}
		if (searchLyrics == null) {
			if (other.searchLyrics != null) {
				return false;
			}
		}
		else if (!searchLyrics.equals(other.searchLyrics)) {
			return false;
		}
		if (searchPath == null) {
			if (other.searchPath != null) {
				return false;
			}
		}
		else if (!searchPath.equals(other.searchPath)) {
			return false;
		}
		if (searchPathParent == null) {
			if (other.searchPathParent != null) {
				return false;
			}
		}
		else if (!searchPathParent.equals(other.searchPathParent)) {
			return false;
		}
		if (searchTitle == null) {
			if (other.searchTitle != null) {
				return false;
			}
		}
		else if (!searchTitle.equals(other.searchTitle)) {
			return false;
		}
		if (unionCondition != other.unionCondition) {
			return false;
		}
		return true;
	}

}
