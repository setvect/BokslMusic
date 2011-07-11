/*
 * Ext GWT 2.2.3 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.setvect.bokslmusic.ui.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.setvect.bokslmusic.ui.shared.model.AlbumArticleModel;
import com.setvect.bokslmusic.ui.shared.model.AlbumModel;
import com.setvect.bokslmusic.ui.shared.model.MusicArticleModel;
import com.setvect.bokslmusic.ui.shared.model.MusicDirectoryModel;
import com.setvect.bokslmusic.ui.shared.model.PlayArticleModel;

/**
 * Async <code>MusicManagerService<code> interface.
 */
public interface MusicManagerServiceAsync {

	// ------------------------- 앨범관련
	public void listAlbum(AlbumModel model, AsyncCallback<List<AlbumArticleModel>> children);

	public void addAlbum(String albumName, AsyncCallback<Void> callback);

	public void addMusicForAlbum(int albumSeq, List<String> musicId, AsyncCallback<Void> callback);

	public void removeAlbumArticle(List<AlbumArticleModel> articleList, AsyncCallback<Void> callback);

	// ------------------------- 음악 목록 관련
	public void listMusicArticleAll(AsyncCallback<List<MusicArticleModel>> data);

	public void listMusic(MusicDirectoryModel dir, AsyncCallback<List<MusicArticleModel>> callback);

	public void getPlayArticle(String id, AsyncCallback<PlayArticleModel> callback);

}
