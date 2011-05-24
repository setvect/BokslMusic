package com.setvect.bokslmusic.ui.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.setvect.bokslmusic.service.music.MusicService;
import com.setvect.bokslmusic.ui.client.service.MusicManagerService;
import com.setvect.bokslmusic.ui.shared.model.AlbumArticleModel;
import com.setvect.bokslmusic.ui.shared.model.FolderModel;
import com.setvect.bokslmusic.ui.shared.model.MusicArticleModel;
import com.setvect.bokslmusic.vo.music.Album;
import com.setvect.bokslmusic.vo.music.MusicArticle;
import com.setvect.bokslmusic.vo.music.PlayItem;
import com.setvect.common.util.GenericPage;

/**
 * 
 */
@Service("MusicManagerService")
public class AlbumServiceImpl implements MusicManagerService {
	@Autowired
	private MusicService musicService;

	// ------------------------- 앨범관련
	public List<AlbumArticleModel> listFolder(AlbumArticleModel model) {
		if (model == null) {
			return listAlbum();
		}
		else {
			int albumSeq = model.getAlbumNo();
			return listAlbumArticle(albumSeq);
		}

	}

	/**
	 * @return 앨범 수록 곡 리스트
	 */
	private List<AlbumArticleModel> listAlbum() {
		List<AlbumArticleModel> result = new ArrayList<AlbumArticleModel>();
		Collection<Album> album = musicService.getAlbumListAll();
		for (Album a : album) {
			FolderModel m = new FolderModel(a.getName(), a.getAlbumSeq());
			result.add(m);
		}
		return result;
	}

	/**
	 * @param albumSeq
	 * @return 앨범 수록 곡 리스트
	 */
	private List<AlbumArticleModel> listAlbumArticle(int albumSeq) {
		List<AlbumArticleModel> result = new ArrayList<AlbumArticleModel>();
		GenericPage<PlayItem> page = musicService.getPlayItemList(albumSeq);
		Collection<PlayItem> list = page.getList();
		for (PlayItem a : list) {
			MusicArticle musicArticle = a.getMusicArticle();
			String name = musicArticle.getName();
			int runningTime = musicArticle.getRunningTime();
			String musicId = musicArticle.getMusicId();
			int orderNo = a.getOrderNo();
			int albumNo = a.getAlbumSeq();
			AlbumArticleModel m = new AlbumArticleModel(musicId, name, runningTime, musicArticle.getPath(), orderNo,
					albumNo);
			result.add(m);
		}
		if (result.size() == 0) {
			AlbumArticleModel m = new AlbumArticleModel("Empty", "Empty", 0, "", 0, 0);
			result.add(m);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.setvect.bokslmusic.ui.client.service.MusicManagerService#addAlbum
	 * (java.lang .String)
	 */
	public void addAlbum(String albumName) {
		Album album = new Album();
		album.setName(albumName);
		musicService.createAlbum(album);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.setvect.bokslmusic.ui.client.service.MusicManagerService#addMusicForAlbum
	 * (int, java.util.List)
	 */
	public void addMusicForAlbum(int albumSeq, List<String> musicId) {
		for (String s : musicId) {
			PlayItem p = new PlayItem();
			p.setAlbumSeq(albumSeq);
			p.setMusicId(s);
			musicService.createPlayItem(p);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.setvect.bokslmusic.ui.client.service.MusicManagerService#
	 * removeAlbumArticle(java.util.List)
	 */
	public void removeAlbumArticle(List<AlbumArticleModel> articleList) {
		for (AlbumArticleModel article : articleList) {
			int albumSeq = article.getAlbumNo();
			if (article instanceof FolderModel) {
				musicService.removeAlbum(albumSeq);
			}
			else {
				String musicId = article.getId();
				musicService.removePlayItem(albumSeq, musicId);
			}
		}
	}

	// ----------------------

	public List<MusicArticleModel> listMusicArticleAll() {
		Collection<MusicArticle> allList = musicService.getMusicArticleAllList();
		List<MusicArticleModel> result = new ArrayList<MusicArticleModel>();
		for (MusicArticle article : allList) {
			MusicArticleModel m = new MusicArticleModel(article.getMusicId(), article.getName(),
					article.getRunningTime(), article.getPath());
			result.add(m);
		}
		return result;
	}

}
