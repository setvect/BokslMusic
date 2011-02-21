package com.setvect.bokslmusic.service.music;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.setvect.bokslmusic.db.MusicDao;
import com.setvect.bokslmusic.vo.music.MusicArticle;
import com.setvect.common.util.GenericPage;

@Service("MusicService")
public class MusicService {
	@Autowired
	private MusicDao musicArticleDao;

	/**
	 * @param musicArticleSeq
	 * @return
	 * @see com.setvect.bokslmusic.db.MusicDao#getMusicArticle(String)
	 */
	public MusicArticle getMusicArticle(String musicArticleId) {
		return musicArticleDao.getMusicArticle(musicArticleId);
	}

	/**
	 * @param pageCondition
	 * @return
	 * @see com.setvect.bokslmusic.db.MusicDao#getMusicArticlePagingList(com.setvect.bokslmusic.service.music.MusicArticleSearch)
	 */
	public GenericPage<MusicArticle> getMusicArticlePagingList(MusicArticleSearch pageCondition) {
		return musicArticleDao.getMusicArticlePagingList(pageCondition);
	}

	/**
	 * @param item
	 * @see com.setvect.bokslmusic.db.MusicDao#createMusicArticle(com.setvect.bokslmusic.vo.music.MusicArticle)
	 */
	public void createMusicArticle(MusicArticle item) {
		musicArticleDao.createMusicArticle(item);
	}

	/**
	 * @param item
	 * @see com.setvect.bokslmusic.db.MusicDao#updateMusicArticle(com.setvect.bokslmusic.vo.music.MusicArticle)
	 */
	public void updateMusicArticle(MusicArticle item) {
		musicArticleDao.updateMusicArticle(item);
	}

	/**
	 * @param musicArticleId
	 * @see com.setvect.bokslmusic.db.MusicDao#removeMusicArticle(String)
	 */
	public void removeMusicArticle(String musicArticleId) {
		musicArticleDao.removeMusicArticle(musicArticleId);
	}
}
