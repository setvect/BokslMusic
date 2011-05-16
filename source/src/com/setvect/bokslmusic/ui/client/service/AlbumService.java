package com.setvect.bokslmusic.ui.client.service;

import java.util.List;

import com.extjs.gxt.ui.client.data.RemoteSortTreeLoadConfig;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.setvect.bokslmusic.service.music.MusicService;
import com.setvect.bokslmusic.ui.shared.model.AlbumArticleModel;

/**
 * 음악 파일 경로 동기화
 * 
 * @see MusicService
 */
@RemoteServiceRelativePath("service/AlbumService")
public interface AlbumService extends RemoteService {

	public List<AlbumArticleModel> getFolderChildren(AlbumArticleModel model);

	public List<AlbumArticleModel> getFolderChildren(RemoteSortTreeLoadConfig loadConfig);

}
