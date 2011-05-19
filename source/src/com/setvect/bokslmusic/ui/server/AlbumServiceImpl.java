package com.setvect.bokslmusic.ui.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.setvect.bokslmusic.service.music.MusicService;
import com.setvect.bokslmusic.ui.client.service.AlbumService;
import com.setvect.bokslmusic.ui.shared.model.AlbumArticleModel;
import com.setvect.bokslmusic.ui.shared.model.FolderModel;
import com.setvect.bokslmusic.vo.music.Album;

/**
 * 
 */
@Service("AlbumService")
public class AlbumServiceImpl implements AlbumService {
	@Autowired
	private MusicService musicService;

	public List<AlbumArticleModel> getFolderChildren(AlbumArticleModel model) {
		List<AlbumArticleModel> result = new ArrayList<AlbumArticleModel>();
		Collection<Album> album = musicService.getAlbumListAll();

		for (Album a : album) {
			FolderModel m = new FolderModel(a.getName(), a.getName());
			result.add(m);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.setvect.bokslmusic.ui.client.service.AlbumService#addAlbum(java.lang
	 * .String)
	 */
	public void addAlbum(String albumName) {
		Album album = new Album();
		album.setName(albumName);
		musicService.createAlbum(album);
	}

}
