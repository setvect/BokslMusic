package com.setvect.bokslmusic.db;

import com.setvect.bokslmusic.service.music.MusicArticleSearch;
import com.setvect.bokslmusic.vo.music.MusicArticle;
import com.setvect.common.util.GenericPage;

/**
 * ���� ��� ���� DAO
 * 
 * @version $Id$
 */
public interface MusicArticleDao {

	/**
	 * @param musicArticleSeq
	 * @return ��������
	 */
	public MusicArticle getMusicArticle(String musicArticleId);

	/**
	 * @param pageCondition
	 * @return ��������
	 */
	public GenericPage<MusicArticle> getMusicArticlePagingList(MusicArticleSearch pageCondition);

	/**
	 * ���� ���
	 * 
	 * @param item
	 */
	public void createMusicArticle(MusicArticle item);

	/**
	 * ���� ����
	 * 
	 * @param item
	 */
	public void updateMusicArticle(MusicArticle item);

	/**
	 * ���� ����
	 * 
	 * @param musicArticleId
	 */
	public void removeMusicArticle(String musicArticleId);
}
