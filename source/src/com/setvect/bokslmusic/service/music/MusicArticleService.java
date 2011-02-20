package com.setvect.bokslmusic.service.music;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.setvect.bokslmusic.db.MusicArticleDao;
import com.setvect.bokslmusic.vo.music.MusicArticle;
import com.setvect.common.util.GenericPage;

@Service("MusicArticleService")
public class MusicArticleService {
	@Autowired
	private MusicArticleDao musicArticleDao;

	/**
	 * @param musicArticleSeq
	 * @return
	 * @see com.setvect.bokslmusic.db.MusicArticleDao#getMusicArticle(String)
	 */
	public MusicArticle getMusicArticle(String musicArticleId) {
		return musicArticleDao.getMusicArticle(musicArticleId);
	}

	/**
	 * @param pageCondition
	 * @return
	 * @see com.setvect.bokslmusic.db.MusicArticleDao#getMusicArticlePagingList(com.setvect.bokslmusic.service.music.MusicArticleSearch)
	 */
	public GenericPage<MusicArticle> getMusicArticlePagingList(MusicArticleSearch pageCondition) {
		return musicArticleDao.getMusicArticlePagingList(pageCondition);
	}

	/**
	 * @param item
	 * @see com.setvect.bokslmusic.db.MusicArticleDao#createMusicArticle(com.setvect.bokslmusic.vo.music.MusicArticle)
	 */
	public void createMusicArticle(MusicArticle item) {
		musicArticleDao.createMusicArticle(item);
	}

	/**
	 * @param item
	 * @see com.setvect.bokslmusic.db.MusicArticleDao#updateMusicArticle(com.setvect.bokslmusic.vo.music.MusicArticle)
	 */
	public void updateMusicArticle(MusicArticle item) {
		musicArticleDao.updateMusicArticle(item);
	}

	/**
	 * @param musicArticleId
	 * @see com.setvect.bokslmusic.db.MusicArticleDao#removeMusicArticle(String)
	 */
	public void removeMusicArticle(String musicArticleId) {
		musicArticleDao.removeMusicArticle(musicArticleId);
	}
}
