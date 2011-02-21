package com.setvect.bokslmusic.db;

import java.util.List;

import com.setvect.bokslmusic.service.music.MusicArticleSearch;
import com.setvect.bokslmusic.vo.music.MusicArticle;
import com.setvect.bokslmusic.vo.music.MusicPath;
import com.setvect.common.util.GenericPage;

/**
 * À½¾Ç °ü¸® DAO
 * 
 * @version $Id$
 */
public interface MusicDao {
	// ------ À½¾Ç °æ·Î °ü¸®
	public MusicPath getMusicPath(String basePath);

	public List<MusicPath> getMusicPathList();

	public void createMusicPath(MusicPath item);

	public void updateMusicPath(MusicPath item);

	public void removeMusicPath(String basePath);

	// ------ À½¾Ç ¸ñ·Ï °ü¸®
	/**
	 * @param musicArticleSeq
	 * @return À½¾ÇÁ¤º¸
	 */
	public MusicArticle getMusicArticle(String musicArticleId);

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
	 * @param musicArticleId
	 */
	public void removeMusicArticle(String musicArticleId);
}
