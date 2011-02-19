package com.setvect.bokslmusic.db;

import com.setvect.bokslmusic.service.music.MusicArticleSearch;
import com.setvect.bokslmusic.vo.music.MusicArticle;
import com.setvect.common.util.GenericPage;

/**
 * À½¾Ç ¸ñ·Ï °ü¸® DAO
 * 
 * @version $Id$
 */
public interface MusicArticleDao {

	/**
	 * @param musicArticleSeq
	 * @return À½¾ÇÁ¤º¸
	 */
	public MusicArticle getMusicArticle(int musicArticleSeq);

	/**
	 * @param pageCondition
	 * @return À½¾ÇÁ¤º¸
	 */
	public GenericPage<MusicArticle> getMusicArticlePagingList(MusicArticleSearch pageCondition);

	/**
	 * À½¾Ç µî·Ï
	 * 
	 * @param item
	 */
	public void createMusicArticle(MusicArticle item);

	/**
	 * À½¾Ç ¼öÁ¤
	 * 
	 * @param item
	 */
	public void updateMusicArticle(MusicArticle item);

	/**
	 * À½¾Ç »èÁ¦
	 * 
	 * @param musicArticleSeq
	 */
	public void removeMusicArticle(int musicArticleSeq);
}
