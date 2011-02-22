package com.setvect.bokslmusic.db;

import java.util.List;

import com.setvect.bokslmusic.service.music.MusicArticleSearch;
import com.setvect.bokslmusic.vo.music.MusicArticle;
import com.setvect.bokslmusic.vo.music.MusicDirectory;
import com.setvect.common.util.GenericPage;

/**
 * 음악 관리 DAO
 * 
 * @version $Id$
 */
public interface MusicDao {
	// ------ 음악 경로 관리
	/**
	 * @param basePath
	 *            경로(키)
	 * @return 음원 저장 경로
	 */
	public MusicDirectory getMusicPath(String basePath);

	/**
	 * @return 등록된 모든 음원 저장 경로
	 */
	public List<MusicDirectory> getMusicPathList();

	/**
	 * 등록
	 * @param item
	 *            음원 저장 경로
	 */
	public void createMusicPath(MusicDirectory item);

	/**
	 * 수정
	 * @param item
	 *            음원 저장 경로
	 */
	public void updateMusicPath(MusicDirectory item);

	/**
	 * 삭제
	 * @param basePath
	 *            음원 경로(키)
	 */
	public void removeMusicPath(String basePath);

	// ------ 음악 목록 관리
	/**
	 * @param musicArticleSeq
	 * @return 음악정보
	 */
	public MusicArticle getMusicArticle(String musicArticleId);

	/**
	 * @param pageCondition
	 * @return 음악정보
	 */
	public GenericPage<MusicArticle> getMusicArticlePagingList(MusicArticleSearch pageCondition);

	/**
	 * 음악 등록
	 * 
	 * @param item
	 */
	public void createMusicArticle(MusicArticle item);

	/**
	 * 음악 수정
	 * 
	 * @param item
	 */
	public void updateMusicArticle(MusicArticle item);

	/**
	 * 음악 삭제
	 * 
	 * @param musicArticleId
	 */
	public void removeMusicArticle(String musicArticleId);
}
