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

/**
 * Async <code>AlbumService<code> interface.
 */
public interface AlbumServiceAsync {

	public void getFolderChildren(AlbumArticleModel model, AsyncCallback<List<AlbumArticleModel>> children);

	void addAlbum(String albumName, AsyncCallback<Void> callback);
}
