package com.setvect.bokslmusic.service.music;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.setvect.bokslmusic.db.MusicDao;
import com.setvect.bokslmusic.vo.music.Album;
import com.setvect.bokslmusic.vo.music.MusicArticle;
import com.setvect.bokslmusic.vo.music.MusicDirectory;
import com.setvect.bokslmusic.vo.music.PlayItem;
import com.setvect.bokslmusic.vo.music.PlayTime;
import com.setvect.common.util.GenericPage;

@Service("MusicService")
public class MusicService {
	@Autowired
	private MusicDao musicArticleDao;

	// ------ ���� ��� ����
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

	// ------ ���� ��� ����
	public MusicArticle getMusicArticle(String musicArticleId) {
		return musicArticleDao.getMusicArticle(musicArticleId);
	}

	public GenericPage<MusicArticle> getMusicArticlePagingList(MusicArticleSearch pageCondition) {
		return musicArticleDao.getMusicArticlePagingList(pageCondition);
	}

	public List<String> getMusicArticlePath() {
		return musicArticleDao.getMusicArticlePath();
	}

	/**
	 * getMusicArticlePath()�޼ҵ�� ����� ������ ������, �˻��� �������� ��� ������ path�� �����Ͽ� �ߺ�������
	 * ��� ����
	 * 
	 * @TODO ���� ĳ���� �̿��� �ӵ� ����
	 * @param pageCondition
	 * @return
	 */
	public List<String> getMusicArticlePathSearch(MusicArticleSearch pageCondition) {
		GenericPage<MusicArticle> page = musicArticleDao.getMusicArticlePagingList(pageCondition);
		Collection<MusicArticle> allList = page.getList();
		Set<String> pathSet = new TreeSet<String>();
		for (MusicArticle m : allList) {
			pathSet.add(m.getPath());
		}

		List<String> result = new ArrayList<String>();
		result.addAll(pathSet);
		return result;
	}

	/**
	 * @return DB�� �ִ� ��ü ���� ���
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

	// ------ �ٹ� ����

	public Album getAlbum(int albumSeq) {
		return musicArticleDao.getAlbum(albumSeq);
	}

	public GenericPage<Album> getAlbumList(AlbumSearch pageCondition) {
		return musicArticleDao.getAlbumList(pageCondition);
	}

	public void createAlbum(Album item) {
		musicArticleDao.createAlbum(item);
	}

	public void updateAlbum(Album item) {
		musicArticleDao.updateAlbum(item);
	}

	public void removeAlbum(int albumSeq) {
		musicArticleDao.removeAlbum(albumSeq);
	}

	// ------ Play Item ����
	public PlayItem getPlayItem(int playItemSeq) {
		return musicArticleDao.getPlayItem(playItemSeq);
	}

	public GenericPage<PlayItem> getPlayItemList(PlayItemSearch pageCondition) {
		return musicArticleDao.getPlayItemList(pageCondition);
	}

	public void createPlayItem(PlayItem item) {
		musicArticleDao.createPlayItem(item);
	}

	public void updatePlayItem(PlayItem item) {
		musicArticleDao.updatePlayItem(item);
	}

	public void removePlayItem(int playItemSeq) {
		musicArticleDao.removePlayItem(playItemSeq);
	}

	// ------ Play Time ����

	public PlayTime getPlayTime(int playTimeSeq) {
		return musicArticleDao.getPlayTime(playTimeSeq);
	}

	public GenericPage<PlayTime> getPlayTimeList(PlayTimeSearch pageCondition) {
		return musicArticleDao.getPlayTimeList(pageCondition);
	}

	public void createPlayTime(PlayTime item) {
		musicArticleDao.createPlayTime(item);
	}

	public void updatePlayTime(PlayTime item) {
		musicArticleDao.updatePlayTime(item);
	}

	public void removePlayTime(int playTimeSeq) {
		musicArticleDao.removePlayTime(playTimeSeq);
	}

}
