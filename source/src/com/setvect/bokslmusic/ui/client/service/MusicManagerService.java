package com.setvect.bokslmusic.ui.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.setvect.bokslmusic.service.music.MusicService;
import com.setvect.bokslmusic.ui.shared.model.AlbumArticleModel;
import com.setvect.bokslmusic.ui.shared.model.MusicArticleModel;

/**
 * 앨범 관리
 * 
 * @see MusicService
 */
@RemoteServiceRelativePath("service/MusicManagerService")
public interface MusicManagerService extends RemoteService {

	/**
	 * 앨범 정보 및 앨범 수록 항목 정보
	 * 
	 * @param model
	 * @return
	 */
	public List<AlbumArticleModel> listFolder(AlbumArticleModel model);

	/**
	 * 앨범 등록
	 * 
	 * @param albumName
	 *            앨범 이름
	 * @param asyncCallback
	 */
	public void addAlbum(String albumName);

	/**
	 * @return
	 */
	public List<MusicArticleModel> listMusicArticleAll();

}
