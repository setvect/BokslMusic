package com.setvect.bokslmusic.ui.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.setvect.bokslmusic.service.music.MusicService;
import com.setvect.bokslmusic.ui.shared.model.AlbumArticleModel;

/**
 * 앨범 관리
 * 
 * @see MusicService
 */
@RemoteServiceRelativePath("service/AlbumService")
public interface AlbumService extends RemoteService {

	public List<AlbumArticleModel> getFolderChildren(AlbumArticleModel model);

	/**
	 * 앨범 등록
	 * 
	 * @param albumName
	 *            앨범 이름
	 * @param asyncCallback
	 */
	public void addAlbum(String albumName);

}
