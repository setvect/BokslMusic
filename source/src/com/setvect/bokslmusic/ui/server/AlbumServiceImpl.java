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

	// ----------------------
	public List<AlbumArticleModel> listFolder(AlbumArticleModel model) {
		if (model == null) {
			return listAlbum();
		}
		else {
			int albumSeq = Integer.parseInt(model.getId());
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
			FolderModel m = new FolderModel(a.getName(), String.valueOf(a.getAlbumSeq()));
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
			AlbumArticleModel m = new AlbumArticleModel(musicArticle.getName(), musicArticle.getRunningTime(),
					musicArticle.getMusicId());
			result.add(m);
		}
		if (result.size() == 0) {
			AlbumArticleModel m = new AlbumArticleModel("Empty", 0, "Empty");
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

	// ----------------------

	public List<MusicArticleModel> listMusicArticleAll() {
		Collection<MusicArticle> allList = musicService.getMusicArticleAllList();
		List<MusicArticleModel> result = new ArrayList<MusicArticleModel>();
		for (MusicArticle article : allList) {
			MusicArticleModel m = new MusicArticleModel(article.getName(), article.getRunningTime(), article.getPath());
			result.add(m);
		}
		return result;
	}
}
