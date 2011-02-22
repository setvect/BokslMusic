package com.setvect.bokslmusic.service.music;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.setvect.bokslmusic.db.MusicDao;
import com.setvect.bokslmusic.vo.music.MusicArticle;
import com.setvect.bokslmusic.vo.music.MusicDirectory;
import com.setvect.common.util.GenericPage;

@Service("MusicService")
public class MusicService {
	@Autowired
	private MusicDao musicArticleDao;

	// ------ 음악 경로 관리
	public MusicDirectory getMusicPath(String basePath) {
		return musicArticleDao.getMusicPath(basePath);
	}

	public List<MusicDirectory> getMusicPathList() {
		return musicArticleDao.getMusicPathList();
	}

	public void createMusicPath(MusicDirectory item) {
		musicArticleDao.createMusicPath(item);
	}

	public void updateMusicPath(MusicDirectory item) {
		musicArticleDao.updateMusicPath(item);
	}

	public void removeMusicPath(String basePath) {
		musicArticleDao.removeMusicPath(basePath);
	}

	// ------ 음악 목록 관리
	public MusicArticle getMusicArticle(String musicArticleId) {
		return musicArticleDao.getMusicArticle(musicArticleId);
	}

	public GenericPage<MusicArticle> getMusicArticlePagingList(MusicArticleSearch pageCondition) {
		return musicArticleDao.getMusicArticlePagingList(pageCondition);
	}

	/**
	 * @return DB에 있는 전체 음원 목록
	 */
	public Collection<MusicArticle> getMusicArticleAllList() {
		MusicArticleSearch pageCondition = new MusicArticleSearch(1);
		pageCondition.setPagePerItemCount(Integer.MAX_VALUE);
		GenericPage<MusicArticle> musicArticlePagingList = musicArticleDao.getMusicArticlePagingList(pageCondition);
		return musicArticlePagingList.getList();
	}

	public void createMusicArticle(MusicArticle item) {
		musicArticleDao.createMusicArticle(item);
	}

	public void updateMusicArticle(MusicArticle item) {
		musicArticleDao.updateMusicArticle(item);
	}

	public void removeMusicArticle(String musicArticleId) {
		musicArticleDao.removeMusicArticle(musicArticleId);
	}
}
