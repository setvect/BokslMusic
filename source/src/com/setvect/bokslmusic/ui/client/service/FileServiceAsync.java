/*
 * Ext GWT 2.2.3 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.setvect.bokslmusic.ui.client.service;

import java.util.List;

import com.extjs.gxt.ui.client.data.RemoteSortTreeLoadConfig;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.setvect.bokslmusic.ui.shared.model.AlbumArticleModel;

/**
 * Async <code>FileService<code> interface.
 */
public interface FileServiceAsync {

	public void getFolderChildren(AlbumArticleModel model, AsyncCallback<List<AlbumArticleModel>> children);

	public void getFolderChildren(RemoteSortTreeLoadConfig loadConfig, AsyncCallback<List<AlbumArticleModel>> children);

}
